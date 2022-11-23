package domainapp.modules.simple.dom.articulo;



import domainapp.modules.simple.dom.EstadoHabDes;
import domainapp.modules.simple.dom.reportes.RepoArticulo;
import domainapp.modules.simple.dom.proveedor.Proveedor;
import domainapp.modules.simple.dom.ubicacion.Ubicacion;
import domainapp.modules.simple.types.articulo.CodigoArticulo;
import domainapp.modules.simple.types.articulo.Descripcion;
import domainapp.modules.simple.types.articulo.Stock;
import lombok.*;
import org.apache.isis.applib.annotation.*;
import org.apache.isis.applib.jaxb.PersistentEntityAdapter;
import org.apache.isis.applib.query.Query;
import org.apache.isis.applib.services.message.MessageService;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.apache.isis.applib.services.title.TitleService;
import org.datanucleus.store.rdbms.query.PersistentClassROF;

import javax.inject.Inject;
import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.VersionStrategy;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Comparator;
import java.util.List;

import static org.apache.isis.applib.annotation.SemanticsOf.IDEMPOTENT;
import static org.apache.isis.applib.annotation.SemanticsOf.NON_IDEMPOTENT_ARE_YOU_SURE;

@javax.jdo.annotations.PersistenceCapable(
        schema = "depotapp",
        identityType= IdentityType.DATASTORE)
@javax.jdo.annotations.Unique(
        name = "Articulo_codigo_UNQ", members = {"codigo"}
)
@javax.jdo.annotations.Queries({
        @javax.jdo.annotations.Query(
                name = Articulo.NAMED_QUERY__FIND_BY_CODIGO_LIKE,
                value = "SELECT " +
                        "FROM domainapp.modules.simple.dom.articulo.Articulo " +
                        "WHERE codigo.indexOf(:codigo) >= 0"
        ),
        @javax.jdo.annotations.Query(
                name = Articulo.NAMED_QUERY__FIND_BY_CODIGO_EXACT,
                value = "SELECT " +
                        "FROM domainapp.modules.simple.dom.articulo.Articulo " +
                        "WHERE codigo == :codigo"
        ),
        @javax.jdo.annotations.Query(
                name = Articulo.NAMED_QUERY__FIND_BY_HABILITADO,
                value = "SELECT " +
                        "FROM domainapp.modules.simple.dom.articulo.Articulo " +
                        "WHERE estado == 'HABILITADO'"
        ),
        @javax.jdo.annotations.Query(
                name = Articulo.NAMED_QUERY__FIND_BY_DESHABILITADO,
                value = "SELECT " +
                        "FROM domainapp.modules.simple.dom.articulo.Articulo " +
                        "WHERE estado == 'DESHABILITADO'"
        ),
        @javax.jdo.annotations.Query(
                name = Articulo.NAMED_QUERY__FIND_BY_KIT,
                value = "SELECT " +
                        "FROM domainapp.modules.simple.dom.articulo.Articulo " +
                        "WHERE estado == 'DESHABILITADO'"
        )
})
@javax.jdo.annotations.DatastoreIdentity(strategy= IdGeneratorStrategy.IDENTITY, column="id")
@javax.jdo.annotations.Version(strategy= VersionStrategy.DATE_TIME, column="version")
@DomainObject(logicalTypeName = "depotapp.Articulo", entityChangePublishing = Publishing.ENABLED)
@DomainObjectLayout()
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@XmlJavaTypeAdapter(PersistentEntityAdapter.class)
@ToString(onlyExplicitlyIncluded = true)
@javax.persistence.Table(schema = "SIMPLE")
public  class Articulo implements Comparable<Articulo> {

    static final String NAMED_QUERY__FIND_BY_CODIGO_LIKE = "Articulo.findByCodigoLike";
    public static final String NAMED_QUERY__FIND_BY_CODIGO_EXACT = "Articulo.findByCodigo";
    public static final String NAMED_QUERY__FIND_BY_HABILITADO = "Articulo.findByHabilitado";
    static final String NAMED_QUERY__FIND_BY_DESHABILITADO = "Articulo.findByDeshabilitado";
    static final String NAMED_QUERY__FIND_BY_KIT = "Articulo.findByKit";
    @Inject TitleService titleService;
    @Inject MessageService messageService;
    @Inject RepositoryService repositoryService;

    public static Articulo withName(String descripcion, Proveedor proveedor, Ubicacion ubicacion) {
        val articulo = new Articulo();
        articulo.setStock(0);
        articulo.setDescripcion(descripcion);
        articulo.setStock(0);
        articulo.setEstado(EstadoHabDes.HABILITADO);
        articulo.setProveedor(proveedor);
        articulo.setUbicacion(ubicacion);
        return articulo;
    }

    @Action(semantics = NON_IDEMPOTENT_ARE_YOU_SURE)
    @ActionLayout(
            position = ActionLayout.Position.PANEL,
            describedAs = "Habilita el artículo")
    public Articulo habilitar() {
        final String title = titleService.titleOf(this);
        messageService.informUser(String.format("'%s' habilitado", title));
        this.setEstado(EstadoHabDes.HABILITADO);
        return this;
    }

    @Action(semantics = NON_IDEMPOTENT_ARE_YOU_SURE)
    @ActionLayout(
            position = ActionLayout.Position.PANEL,
            describedAs = "Deshabilita el artículo.")
    public Articulo deshabilitar() {
        final String title = titleService.titleOf(this);
        messageService.informUser(String.format("'%s' deshabilitado", title));
        this.setEstado(EstadoHabDes.DESHABILITADO);
        return this;
    }

    public boolean hideHabilitar() {
        return this.getEstado()== EstadoHabDes.HABILITADO;
    }

    public boolean hideDeshabilitar() {
        return this.getEstado()== EstadoHabDes.DESHABILITADO;
    }

    @Programmatic
    public void restarStock(int cantidad) {
        this.setStock(this.getStock() - cantidad);
    }

    @Programmatic
    public void sumarStock(int cantidad) {
        this.setStock(this.getStock() + cantidad);
    }

    public String title() {
        return "Artículo " + getCodigo();
    }

    @CodigoArticulo
    @Getter
    @Setter
    @ToString.Include
    @PropertyLayout(fieldSetId = "articulo", sequence = "1")
    private int codigo;

    @Descripcion
    @Getter
    @Setter
    @ToString.Include @Property(editing = Editing.ENABLED)
    @PropertyLayout(fieldSetId = "articulo", sequence = "2")
    private String descripcion;

    @Stock
    @Getter
    @Setter
    @ToString.Include
    @PropertyLayout(fieldSetId = "articulo", sequence = "3")
    private Integer stock;

    @Getter
    @Setter
    @ToString.Include
    @Column(allowsNull = "false")
    @PropertyLayout(fieldSetId = "articulo", sequence = "4")
    private EstadoHabDes estado;

    @Getter
    @Setter
    @ToString.Include
    @Column(allowsNull = "false") 
    @PropertyLayout(fieldSetId = "articulo", sequence = "5")
    private Proveedor proveedor;

    @Getter
    @Setter
    @ToString.Include
    @Column(allowsNull = "false")
    @PropertyLayout(fieldSetId = "articulo", sequence = "6")
    private Ubicacion ubicacion;

    @Action(semantics = IDEMPOTENT, commandPublishing = Publishing.ENABLED, executionPublishing = Publishing.ENABLED)
    @ActionLayout(associateWith = "proveedor", promptStyle = PromptStyle.INLINE)
    public Articulo modificarProveedor(final Proveedor proveedor){
        this.setProveedor(proveedor);
        return this;
    };

    public Proveedor default0ModificarProveedor(){
        return this.getProveedor();
    }

    public List<Proveedor> choices0ModificarProveedor() {
        return repositoryService.allMatches(Query.named(Proveedor.class, Proveedor.NAMED_QUERY__FIND_BY_HABILITADO));
    }

    @Action(semantics = IDEMPOTENT, commandPublishing = Publishing.ENABLED, executionPublishing = Publishing.ENABLED)
    @ActionLayout(associateWith = "ubicacion", promptStyle = PromptStyle.INLINE)
    public Articulo modificarUbicacion(final Ubicacion ubicacion){
        this.setUbicacion(ubicacion);
        return this;
    };

    public Ubicacion default0ModificarUbicacion(){
        return this.getUbicacion();
    }

    public List<Ubicacion> choices0ModificarUbicacion() {
        return repositoryService.allMatches(Query.named(Ubicacion.class, Ubicacion.NAMED_QUERY__BUSCAR_HABILITADOS));
    }

    private final static Comparator<Articulo> comparator =
            Comparator.comparing(Articulo::getCodigo);
    @Override
    public int compareTo(final Articulo other) {
        return comparator.compare(this, other);
    }
}
