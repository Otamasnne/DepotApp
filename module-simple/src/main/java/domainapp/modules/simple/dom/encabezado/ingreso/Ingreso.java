package domainapp.modules.simple.dom.encabezado.ingreso;

import domainapp.modules.simple.dom.EstadoOperativo;
import domainapp.modules.simple.dom.item.itemIngreso.ItemIngreso;
import domainapp.modules.simple.dom.proveedor.Proveedor;
import domainapp.modules.simple.types.articulo.Descripcion;
import domainapp.modules.simple.types.comprobante.CodigoCo;
import lombok.*;
import org.apache.isis.applib.annotation.*;
import org.apache.isis.applib.jaxb.PersistentEntityAdapter;
import org.apache.isis.applib.services.message.MessageService;
import org.apache.isis.applib.services.title.TitleService;
import org.apache.isis.persistence.jdo.applib.services.JdoSupportService;

import javax.inject.Inject;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.VersionStrategy;
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
                name = Ingreso.NAMED_QUERY__BUSCAR_POR_PROVEEDOR,
                value = "SELECT " +
                        "FROM domainapp.modules.simple.dom.encabezado.ingreso.Ingreso " +
                        "WHERE proveedor == :proveedor"
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

    @Title
    @CodigoCo
    @Getter
    @Setter
    @ToString.Include
    @PropertyLayout(fieldSetId = "ingreso", sequence = "1")
    private String codigo;

    @Descripcion
    @Getter @Setter @ToString.Include
    @PropertyLayout(fieldSetId = "ingreso", sequence = "2")
    private String descripcion;

    @Getter @Setter
    @PropertyLayout(fieldSetId = "ingreso", sequence = "3")
    private EstadoOperativo estadoOperativo;

    //TODO: Pasar esto a la creación
    @Getter @Setter
    @PropertyLayout(fieldSetId = "ingreso", sequence = "4")
    private Proveedor proveedor;

    @Getter @Setter
    @Persistent(mappedBy="ingreso")
    @PropertyLayout(hidden = Where.EVERYWHERE)
    List<ItemIngreso> items;

    //Manda el ingreso a procesar, lo cual lo envía a la app de Android
    //TODO: ENVIAR EL INGRESO A LA APLICACIÓN CUANDO SE PASE A ESTE ESTADO.
    //TODO: MODIFICAR RETORNO PARA COMPORTAMIENTO DESEADO.
    @Action(semantics = NON_IDEMPOTENT_ARE_YOU_SURE)
    @ActionLayout(
            position = ActionLayout.Position.PANEL,
            describedAs = "Envía el ingreso a ser procesado.")
    public String procesar() {
        String nombre = this.getCodigo();
        final String title = titleService.titleOf(this);
        messageService.informUser(String.format("'%s' siendo procesado.", title));
        this.setEstadoOperativo(EstadoOperativo.PROCESANDO);
        return "Se envió el Ingreso " + nombre + " a procesamiento.";
    }

    public boolean hideProcesar() {
        return this.getEstadoOperativo()==EstadoOperativo.PROCESANDO;
    }

    private final static Comparator<Ingreso> comparator =
            Comparator.comparing(Ingreso::getCodigo);
    @Override
    public int compareTo(final Ingreso other) {
        return comparator.compare(this, other);
    }

}
