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
import org.apache.isis.applib.services.message.MessageService;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.apache.isis.applib.services.title.TitleService;

import javax.inject.Inject;
import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.VersionStrategy;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Comparator;

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
    public static final String NAMED_QUERY__FIND_BY_CODIGO_EXACT = "Articulo.findByCodigoExact";
    public static final String NAMED_QUERY__FIND_BY_HABILITADO = "Articulo.findByHabilitado";
    static final String NAMED_QUERY__FIND_BY_DESHABILITADO = "Articulo.findByDeshabilitado";
    static final String NAMED_QUERY__FIND_BY_KIT = "Articulo.findByKit";
    @Inject TitleService titleService;
    @Inject MessageService messageService;

    public static Articulo withName(String descripcion, Proveedor proveedor) {
        val articulo = new Articulo();
        articulo.setStock(0);
        articulo.setDescripcion(descripcion);
        articulo.setStock(0);
        articulo.setEstado(EstadoHabDes.HABILITADO);
        articulo.setProveedor(proveedor);
        return articulo;
    }

    @Action(semantics = NON_IDEMPOTENT_ARE_YOU_SURE)
    @ActionLayout(
            position = ActionLayout.Position.PANEL,
            describedAs = "Habilita el artículo")
    public String habilitar() {
        String nombre = this.getCodigo();
        final String title = titleService.titleOf(this);
        messageService.informUser(String.format("'%s' habilitado", title));
        this.setEstado(EstadoHabDes.HABILITADO);
        return "Se habilitó el artículo " + nombre;
    }

    @Action(semantics = NON_IDEMPOTENT_ARE_YOU_SURE)
    @ActionLayout(
            position = ActionLayout.Position.PANEL,
            describedAs = "Deshabilita el artículo.")
    public String deshabilitar() {
        String nombre = this.getCodigo();
        final String title = titleService.titleOf(this);
        messageService.informUser(String.format("'%s' deshabilitado", title));
        this.setEstado(EstadoHabDes.DESHABILITADO);
        return "Se deshabilitó el artículo " + nombre;
    }

    public boolean hideHabilitar() {
        return this.getEstado()== EstadoHabDes.HABILITADO;
    }

    public boolean hideDeshabilitar() {
        return this.getEstado()== EstadoHabDes.DESHABILITADO;
    }

    public String title() {
        return "Artículo " + getCodigo();
    }

    @CodigoArticulo
    @Getter
    @Setter
    @ToString.Include
    @PropertyLayout(fieldSetId = "articulo", sequence = "1")
    private String codigo;

    @Descripcion
    @Getter
    @Setter
    @ToString.Include
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

    private final static Comparator<Articulo> comparator =
            Comparator.comparing(Articulo::getCodigo);
    @Override
    public int compareTo(final Articulo other) {
        return comparator.compare(this, other);
    }
}
