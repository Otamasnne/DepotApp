package domainapp.modules.simple.dom.comprobante.ajuste;

import domainapp.modules.simple.dom.articulo.Articulo;
import domainapp.modules.simple.dom.articulo.Articulos;
import domainapp.modules.simple.dom.comprobante.TipoComprobante;
import domainapp.modules.simple.types.comprobante.CantidadMueve;
import domainapp.modules.simple.types.comprobante.CodigoCo;
import domainapp.modules.simple.types.comprobante.FechaAlta;
import lombok.*;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.annotation.PropertyLayout;
import org.apache.isis.applib.annotation.Publishing;
import org.apache.isis.applib.jaxb.PersistentEntityAdapter;
import org.apache.isis.applib.services.message.MessageService;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.apache.isis.applib.services.title.TitleService;

import javax.inject.Inject;
import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.VersionStrategy;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;

@javax.jdo.annotations.PersistenceCapable(
        schema = "depotapp",
        identityType= IdentityType.DATASTORE)
@javax.jdo.annotations.Unique(
        name = "AjustePositivo_codigoCo_UNQ", members = {"codigoCo"}
)
@javax.jdo.annotations.Queries({
        @javax.jdo.annotations.Query(
                name = AjustePositivo.NAMED_QUERY__FIND_BY_CODIGO_LIKE,
                value = "SELECT " +
                        "FROM domainapp.modules.simple.dom.comprobante.ajuste.AjustePositivo " +
                        "WHERE codigoCo.indexOf(:codigoCo) >= 0"
        ),
        @javax.jdo.annotations.Query(
                name = AjustePositivo.NAMED_QUERY__FIND_BY_CODIGO_EXACT,
                value = "SELECT " +
                        "FROM domainapp.modules.simple.dom.comprobante.ajuste.AjustePositivo " +
                        "WHERE codigoCo == :codigoCo"
        )
})
@javax.jdo.annotations.DatastoreIdentity(strategy= IdGeneratorStrategy.IDENTITY, column="id")
@javax.jdo.annotations.Version(strategy= VersionStrategy.DATE_TIME, column="version")
@DomainObject(logicalTypeName = "depotapp.AjustePositivo", entityChangePublishing = Publishing.ENABLED)
@DomainObjectLayout()
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@XmlJavaTypeAdapter(PersistentEntityAdapter.class)
@ToString(onlyExplicitlyIncluded = true)
public class AjustePositivo implements Comparable<AjustePositivo>{

    static final String NAMED_QUERY__FIND_BY_CODIGO_LIKE = "AjustePositivo.findByCodigoLike";
    static final String NAMED_QUERY__FIND_BY_CODIGO_EXACT = "AjustePositivo.findByCodigoExact";

    @Inject
    RepositoryService repositoryService;
    @Inject
    TitleService titleService;
    @Inject
    MessageService messageService;

    public String title() {
        return getTipoComprobante() + "-" + getCodigoCo();
    }

    public static AjustePositivo creacion(Articulo articulo, int cantidad) {
        val ajustePositivo = new AjustePositivo();
        ajustePositivo.setArticulo(articulo);
        ajustePositivo.setCantidadMueve(cantidad);
        ajustePositivo.setFechaAlta(LocalDateTime.now());
        return ajustePositivo;
    }

    @Getter
    @Setter
    @ToString.Include
    @PropertyLayout(fieldSetId = "encabezado", sequence = "1")
    private TipoComprobante tipoComprobante = TipoComprobante.AJP;

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
    @PropertyLayout(fieldSetId = "articulos", sequence = "1")
    @Column(name="articulo_id", allowsNull = "false")
    private Articulo articulo;

    @CantidadMueve
    @Getter
    @Setter
    @ToString.Include
    @PropertyLayout(fieldSetId = "articulos", sequence = "2")
    private int cantidadMueve;

    private final static Comparator<AjustePositivo> comparator =
            Comparator.comparing(AjustePositivo::getCodigoCo);

    @Override
    public int compareTo(final AjustePositivo other) {
        return comparator.compare(this, other);
    }

}