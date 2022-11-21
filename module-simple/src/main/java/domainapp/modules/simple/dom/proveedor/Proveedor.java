package domainapp.modules.simple.dom.proveedor;

import domainapp.modules.simple.dom.EstadoHabDes;
import domainapp.modules.simple.types.articulo.CodigoArticulo;
import domainapp.modules.simple.types.comprobante.CodigoCo;
import lombok.*;
import org.apache.isis.applib.annotation.*;
import org.apache.isis.applib.jaxb.PersistentEntityAdapter;
import org.apache.isis.applib.services.message.MessageService;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.apache.isis.applib.services.title.TitleService;
import org.apache.isis.persistence.jdo.applib.types.PhoneNumber;

import javax.inject.Inject;
import javax.jdo.annotations.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Comparator;

import static org.apache.isis.applib.annotation.SemanticsOf.NON_IDEMPOTENT_ARE_YOU_SURE;

@PersistenceCapable(
        schema = "depotapp",
        identityType = IdentityType.DATASTORE
)
@Unique(
        name= "Proveedor_codigo_UNQ", members = {"codigo"}
)
@javax.jdo.annotations.Queries({
        @javax.jdo.annotations.Query(
                name = Proveedor.NAMED_QUERY__FIND_BY_CODIGO_LIKE,
                value = "SELECT " +
                        "FROM domainapp.modules.simple.dom.proveedor.Proveedor " +
                        "WHERE codigo.indexOf(:codigo) >= 0"
        ),
        @javax.jdo.annotations.Query(
                name = Proveedor.NAMED_QUERY__FIND_BY_CODIGO_EXACT,
                value = "SELECT " +
                        "FROM domainapp.modules.simple.dom.proveedor.Proveedor " +
                        "WHERE codigo == :codigo"
        ),
        @javax.jdo.annotations.Query(
                name = Proveedor.NAMED_QUERY__FIND_BY_HABILITADO,
                value = "SELECT " +
                        "FROM domainapp.modules.simple.dom.proveedor.Proveedor " +
                        "WHERE estado == 'HABILITADO'"
        ),
        @javax.jdo.annotations.Query(
                name = Proveedor.NAMED_QUERY__FIND_BY_DESHABILITADO,
                value = "SELECT " +
                        "FROM domainapp.modules.simple.dom.proveedor.Proveedor " +
                        "WHERE estado == 'DESHABILITADO'"
        )
})
@DatastoreIdentity(strategy = IdGeneratorStrategy.IDENTITY, column="id")
@Version(strategy = VersionStrategy.DATE_TIME, column="version")
@DomainObject(logicalTypeName = "depotapp.Proveedor", entityChangePublishing = Publishing.ENABLED)
@DomainObjectLayout
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@XmlJavaTypeAdapter(PersistentEntityAdapter.class)
@ToString(onlyExplicitlyIncluded = true)
public class Proveedor implements Comparable<Proveedor>{

    static final String NAMED_QUERY__FIND_BY_CODIGO_LIKE = "Proveedor.findByCodigoLike";
    static final String NAMED_QUERY__FIND_BY_CODIGO_EXACT = "Proveedor.findByCodigoExact";
    static final String NAMED_QUERY__FIND_BY_HABILITADO = "Proveedor.findByHabilitado";
    static final String NAMED_QUERY__FIND_BY_DESHABILITADO = "Proveedor.findByDeshabilitado";

    @Inject
    TitleService titleService;
    @Inject
    MessageService messageService;

   public static Proveedor withName(String razonSocial){
       val proveedor = new Proveedor();
       proveedor.setRazonSocial(razonSocial);
       proveedor.setEstado(EstadoHabDes.HABILITADO);
       return proveedor;
   }

    @Action(semantics = NON_IDEMPOTENT_ARE_YOU_SURE)
    @ActionLayout(
            position = ActionLayout.Position.PANEL,
            describedAs = "Habilita el proveedor")
    public Proveedor habilitar() {
        String nombre = this.getCodigo();
        final String title = titleService.titleOf(this);
        messageService.informUser(String.format("'%s' habilitado", title));
        this.setEstado(EstadoHabDes.HABILITADO);
        return this;
    }

    @Action(semantics = NON_IDEMPOTENT_ARE_YOU_SURE)
    @ActionLayout(
            position = ActionLayout.Position.PANEL,
            describedAs = "Deshabilita el proveedor.")
    public Proveedor deshabilitar() {
        String nombre = this.getCodigo();
        final String title = titleService.titleOf(this);
        messageService.informUser(String.format("'%s' deshabilitado", title));
        this.setEstado(EstadoHabDes.DESHABILITADO);
        return this;
    }

    public String disableHabilitar() {
        return this.getEstado()== EstadoHabDes.HABILITADO ? "Ya se encuentra habilitado" : null;
    }

    public String disableDeshabilitar() {
        return this.getEstado()== EstadoHabDes.DESHABILITADO ? "Ya se encuentra deshabilitado" : null;
    }

    public String title() {
        return getCodigo() + " - " + getRazonSocial();
    }

    // Por que no usar anotacion column?
    @CodigoCo
    @Getter @Setter @ToString.Include
    @PropertyLayout(fieldSetId = "proveedor", sequence = "1")
    private String codigo;

    @Getter @Setter @ToString.Include
    private String direccion;

    @Title
    @Getter @Setter @ToString.Include
    private String razonSocial;

    @Getter @Setter @ToString.Include
    @PhoneNumber //Investigar
    private String telefono;

    @Getter @Setter @ToString.Include
    private String localidad;

    //@EmailAddress no funciona
    @Getter @Setter @ToString.Include
    private String email;

    @Getter
    @Setter
    @ToString.Include
    private EstadoHabDes estado;

    /**
     * PENDIENTE: agregar metodos para actualizar campos
     * Agregar Querys?
     * Definir nombre de la clase repo
     */

    public static final Comparator<Proveedor> comparator = Comparator.comparing(Proveedor::getCodigo);

    @Override
    public int compareTo(final Proveedor other) {
        return comparator.compare(this, other);
    }


}
