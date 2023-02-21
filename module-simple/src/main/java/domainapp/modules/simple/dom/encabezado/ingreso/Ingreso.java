package domainapp.modules.simple.dom.encabezado.ingreso;

import domainapp.modules.simple.dom.EstadoOperativo;
import domainapp.modules.simple.dom.encabezado.ajuste.Ajuste;
import domainapp.modules.simple.dom.encabezado.ajuste.TipoAjuste;
import domainapp.modules.simple.dom.encabezado.pedido.Pedido;
import domainapp.modules.simple.dom.item.itemIngreso.ItemIngreso;
import domainapp.modules.simple.dom.proveedor.Proveedor;
import domainapp.modules.simple.types.articulo.Descripcion;
import domainapp.modules.simple.types.comprobante.CodigoCo;
import lombok.*;
import org.apache.isis.applib.annotation.*;
import org.apache.isis.applib.jaxb.PersistentEntityAdapter;
import org.apache.isis.applib.services.message.MessageService;
import org.apache.isis.applib.services.title.TitleService;
import org.apache.isis.applib.services.wrapper.HiddenException;
import org.apache.isis.persistence.jdo.applib.services.JdoSupportService;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcProperties;

import javax.inject.Inject;
import javax.jdo.annotations.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Comparator;
import java.util.List;

import static org.apache.isis.applib.annotation.SemanticsOf.NON_IDEMPOTENT_ARE_YOU_SURE;

@javax.jdo.annotations.PersistenceCapable(
        schema = "depotapp",
        identityType= IdentityType.DATASTORE)
@javax.jdo.annotations.Unique(
        name = "Ingreso_codigo_UNQ", members = {"codigo"}
)
@javax.jdo.annotations.DatastoreIdentity(strategy= IdGeneratorStrategy.IDENTITY, column="id")
@javax.jdo.annotations.Version(strategy= VersionStrategy.DATE_TIME, column="version")
@DomainObject(logicalTypeName = "depotapp.Ingreso", entityChangePublishing = Publishing.ENABLED)
@DomainObjectLayout()
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@XmlJavaTypeAdapter(PersistentEntityAdapter.class)
@ToString(onlyExplicitlyIncluded = true)
@javax.persistence.Table(schema = "SIMPLE")
@javax.jdo.annotations.Queries({
        @javax.jdo.annotations.Query(
                name = Ingreso.NAMED_QUERY__FIND_BY_CODIGO_LIKE,
                value = "SELECT " +
                        "FROM domainapp.modules.simple.dom.encabezado.ingreso.Ingreso " +
                        "WHERE codigo.indexOf(:codigo) >= 0"
        ),
        @javax.jdo.annotations.Query(
                name = Ingreso.NAMED_QUERY__FIND_BY_CODIGO_EXACT,
                value = "SELECT " +
                        "FROM domainapp.modules.simple.dom.encabezado.ingreso.Ingreso " +
                        "WHERE codigo == :codigo"
        ),
        @javax.jdo.annotations.Query(
                name = Ingreso.NAMED_QUERY_FIND_BY_PROCESANDO,
                value = "SELECT " +
                        "FROM domainapp.modules.simple.dom.encabezado.ingreso.Ingreso " +
                        "WHERE estadoOperativo == 'PROCESANDO'"
        ),
        @javax.jdo.annotations.Query(
                name = Ingreso.NAMED_QUERY__FIND_BY_MODIFICABLE,
                value = "SELECT " +
                        "FROM domainapp.modules.simple.dom.encabezado.ingreso.Ingreso " +
                        "WHERE estadoOperativo == 'MODIFICABLE'"
        ),
        @javax.jdo.annotations.Query(
                name = Ingreso.NAMED_QUERY__FIND_BY_COMPLETADO,
                value = "SELECT " +
                        "FROM domainapp.modules.simple.dom.encabezado.ingreso.Ingreso " +
                        "WHERE estadoOperativo == 'COMPLETADO'"
        )
})
public class Ingreso implements Comparable<Ingreso>{

    @Inject
    TitleService titleService;
    @Inject
    MessageService messageService;
    @Inject
    JdoSupportService jdoSupportService;


    static final String NAMED_QUERY__FIND_BY_CODIGO_EXACT = "Ingreso.findByCodigoExact";
    static final String NAMED_QUERY__FIND_BY_CODIGO_LIKE = "Ingreso.findByCodigoLike";
    static final String NAMED_QUERY__BUSCAR_POR_PROVEEDOR = "Ingreso.buscarPorProveedor";

    static final String NAMED_QUERY_FIND_BY_PROCESANDO = "Ingreso.findByProcesando";
    static final String NAMED_QUERY__FIND_BY_MODIFICABLE = "Ingreso.findByModificable";
    static final String NAMED_QUERY__FIND_BY_COMPLETADO = "Ingreso.findByCompletado";

    public static Ingreso crear(String descripcion) {
        val ingreso = new Ingreso();
        ingreso.setEstadoOperativo(EstadoOperativo.MODIFICABLE);
        ingreso.setDescripcion(descripcion);
        return ingreso;
    }

    @Programmatic
    public void agregarItem(ItemIngreso item) {
        this.items.add(item);
        jdoSupportService.refresh(this);
    }

    public String title() {
        return "Ingreso " + getCodigo();
    }

    @CodigoCo
    @Getter
    @Setter
    @ToString.Include
    @PropertyLayout(fieldSetId = "ingreso", sequence = "1")
    private int codigo;

    @Descripcion
    @Getter @Setter @ToString.Include
    @Property(editing = Editing.ENABLED)
    @Column(allowsNull = "false")
    @PropertyLayout(fieldSetId = "ingreso", sequence = "2")
    private String descripcion;

    @Getter @Setter
    @PropertyLayout(fieldSetId = "ingreso", sequence = "3")
    private EstadoOperativo estadoOperativo;

    @Getter @Setter
    @Persistent(mappedBy="ingreso")
    @PropertyLayout(hidden = Where.EVERYWHERE)
    List<ItemIngreso> items;

    //Manda el ingreso a procesar, lo cual lo envía a la app de Android
    @Action(semantics = NON_IDEMPOTENT_ARE_YOU_SURE)
    @ActionLayout(
            position = ActionLayout.Position.PANEL,
            describedAs = "Envía el ingreso a ser procesado.")
    public Ingreso procesar() {
        final String title = titleService.titleOf(this);
        messageService.informUser(String.format("'%s' siendo procesado.", title));
        this.setEstadoOperativo(EstadoOperativo.PROCESANDO);
        return this;
    }

    public boolean hideProcesar() {
        return this.getEstadoOperativo()==EstadoOperativo.PROCESANDO || this.getEstadoOperativo()==EstadoOperativo.ANULADO || this.getEstadoOperativo()==EstadoOperativo.COMPLETADO || this.getItems().size() == 0;
    }

    /*
     * @Santi
     * Este metodo es llamado cuando se presiona el boton de completar un ingreso en la app movil
     * */
    @Action(semantics = NON_IDEMPOTENT_ARE_YOU_SURE)
    @ActionLayout(
        named = "Completar Ingreso",
            describedAs = "NO RECOMENDADO. Se completará el ingreso de manera manual, realizando los cambios apropiados en el stock. Se recomienda realizar el procesamiento entero del ingreso desde la aplicación movil."
    )
    public void completar() { // TODO: Pasar de tipo void a Ingreso?
        for (int i = 0; i < getItems().size(); i++) {
            int cantidad = this.getItems().get(i).getCantidad();
            getItems().get(i).getArticulo().sumarStock(cantidad);
        }
        this.setEstadoOperativo(EstadoOperativo.COMPLETADO);
    }

    public boolean hideCompletar() {
        return  this.estadoOperativo == EstadoOperativo.COMPLETADO || this.estadoOperativo == EstadoOperativo.ANULADO || this.estadoOperativo == EstadoOperativo.MODIFICABLE ;
    }

    @Action(semantics = NON_IDEMPOTENT_ARE_YOU_SURE)
    @ActionLayout(
            position = ActionLayout.Position.PANEL,
            describedAs = "Anula el ingreso.")
    public Ingreso anular(){
        this.setEstadoOperativo(EstadoOperativo.ANULADO);
        messageService.informUser(String.format("Se anuló el '%s'", title()));
        return this;
    }

    public boolean hideAnular() {
        return this.estadoOperativo == EstadoOperativo.ANULADO || this.estadoOperativo == EstadoOperativo.COMPLETADO;
    }

    private final static Comparator<Ingreso> comparator =
            Comparator.comparing(Ingreso::getCodigo);
    @Override
    public int compareTo(final Ingreso other) {
        return comparator.compare(this, other);
    }

}
