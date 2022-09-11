package domainapp.modules.simple.dom.ajuste;

import domainapp.modules.simple.dom.EstadoOperativo;
import domainapp.modules.simple.dom.articulo.Articulo;
import domainapp.modules.simple.dom.item.itemAjuste.ItemAjuste;
import domainapp.modules.simple.dom.item.itemAjuste.ItemAjusteRepository;
import domainapp.modules.simple.types.comprobante.CodigoCo;
import domainapp.modules.simple.types.comprobante.FechaAlta;
import lombok.*;
import org.apache.isis.applib.annotation.*;
import org.apache.isis.applib.jaxb.PersistentEntityAdapter;
import org.apache.isis.applib.services.message.MessageService;
import org.apache.isis.applib.services.title.TitleService;

import javax.inject.Inject;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.VersionStrategy;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

import static org.apache.isis.applib.annotation.SemanticsOf.NON_IDEMPOTENT_ARE_YOU_SURE;

@javax.jdo.annotations.PersistenceCapable(
        schema = "depotapp",
        identityType= IdentityType.DATASTORE)
@javax.jdo.annotations.Unique(
        name = "Ajuste_codigoCo_UNQ", members = {"codigoCo"}
)
@javax.jdo.annotations.Queries({
        @javax.jdo.annotations.Query(
                name = Ajuste.NAMED_QUERY__FIND_BY_CODIGO_LIKE,
                value = "SELECT " +
                        "FROM domainapp.modules.simple.dom.ajuste.Ajuste " +
                        "WHERE codigoCo.indexOf(:codigoCo) >= 0"
        ),
        @javax.jdo.annotations.Query(
                name = Ajuste.NAMED_QUERY__FIND_BY_CODIGO_EXACT,
                value = "SELECT " +
                        "FROM domainapp.modules.simple.dom.ajuste.Ajuste " +
                        "WHERE codigoCo == :codigoCo"
        )
})
@javax.jdo.annotations.DatastoreIdentity(strategy= IdGeneratorStrategy.IDENTITY, column="id")
@javax.jdo.annotations.Version(strategy= VersionStrategy.DATE_TIME, column="version")
@DomainObject(logicalTypeName = "depotapp.Ajuste", entityChangePublishing = Publishing.ENABLED)
@DomainObjectLayout()
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@XmlJavaTypeAdapter(PersistentEntityAdapter.class)
@ToString(onlyExplicitlyIncluded = true)
public class Ajuste implements Comparable<Ajuste>{

    static final String NAMED_QUERY__FIND_BY_CODIGO_LIKE = "Ajuste.findByCodigoLike";
    static final String NAMED_QUERY__FIND_BY_CODIGO_EXACT = "Ajuste.findByCodigoExact";

    @Inject
    TitleService titleService;
    @Inject
    MessageService messageService;

    @Inject ItemAjusteRepository itemAjusteRepository;

    public String title() {
        return getTipoAjuste() + "-" + getCodigoCo();
    }

    public static Ajuste creacion(TipoAjuste tipoAjuste, String descripcion) {
        val ajuste = new Ajuste();
        ajuste.setTipoAjuste(tipoAjuste);
        ajuste.setFechaAlta(LocalDateTime.now());
        ajuste.setEstadoOperativo(EstadoOperativo.MODIFICABLE);
        ajuste.setDescripcion(descripcion); 
        return ajuste;
    }

    //TODO: AGREGAR UN RETORNO AL FINAL, VERIFICAR VALIDACION
    @Action(semantics = NON_IDEMPOTENT_ARE_YOU_SURE)
    @ActionLayout(
            position = ActionLayout.Position.PANEL,
            describedAs = "Procesa el ajuste.")
    public Ajuste procesar() {
        List<ItemAjuste> items = itemAjusteRepository.buscarItemPorAjuste(this);
        for (int i = 0; i< items.size(); i++ ) {
            ItemAjuste item = items.get(i);
            Articulo articulo = item.getArticulo();
            if (this.getTipoAjuste() == TipoAjuste.AJP) {
                articulo.setStock(articulo.getStock() + item.getCantidad());
            } else {
                articulo.setStock(articulo.getStock() - item.getCantidad());
            }
        }
        final String title = titleService.titleOf(this);
        this.setEstadoOperativo(EstadoOperativo.COMPLETADO);
        messageService.informUser(String.format("Se realizaron los ajustes de stock indicados bajo el '%s'", title));
        return this;
    }
    public boolean hideProcesar() {
        return this.getEstadoOperativo()==EstadoOperativo.COMPLETADO;
    }

    @Getter
    @Setter
    @ToString.Include
    @PropertyLayout(fieldSetId = "encabezado", sequence = "1")
    private TipoAjuste tipoAjuste;

    @CodigoCo
    @Getter
    @Setter
    @ToString.Include
    @PropertyLayout(fieldSetId = "encabezado", sequence = "2")
    private int codigoCo;

    @FechaAlta
    @Getter
    @Setter
    @ToString.Include
    @PropertyLayout(fieldSetId = "encabezado", sequence = "3")
    private LocalDateTime fechaAlta;

    @Getter
    @Setter
    @ToString.Include
    @PropertyLayout(fieldSetId = "encabezado", sequence = "5")
    private EstadoOperativo estadoOperativo;

    @Getter
    @Setter
    @ToString.Include
    @PropertyLayout(fieldSetId = "encabezado", sequence = "6")
    private String descripcion;

    private final static Comparator<Ajuste> comparator =
            Comparator.comparing(Ajuste::getCodigoCo);

    @Override
    public int compareTo(final Ajuste other) {
        return comparator.compare(this, other);
    }

}