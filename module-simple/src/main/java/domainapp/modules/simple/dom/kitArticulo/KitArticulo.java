package domainapp.modules.simple.dom.kitArticulo;

import domainapp.modules.simple.dom.EstadoHabDes;
import domainapp.modules.simple.dom.EstadoOperativo;
import domainapp.modules.simple.types.articulo.Descripcion;
import domainapp.modules.simple.types.comprobante.CodigoCo;
import lombok.*;
import org.apache.isis.applib.annotation.*;
import org.apache.isis.applib.jaxb.PersistentEntityAdapter;
import org.apache.isis.applib.services.message.MessageService;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.apache.isis.applib.services.title.TitleService;
import org.apache.isis.persistence.jdo.applib.services.JdoSupportService;

import javax.inject.Inject;
import javax.jdo.JDOQLTypedQuery;
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
        name = "KitArticulo_codigo_UNQ", members = {"codigo"}
)
@javax.jdo.annotations.DatastoreIdentity(strategy= IdGeneratorStrategy.IDENTITY, column="id")
@javax.jdo.annotations.Version(strategy= VersionStrategy.DATE_TIME, column="version")
@DomainObject(logicalTypeName = "depotapp.KitArticulo", entityChangePublishing = Publishing.ENABLED)
@DomainObjectLayout()
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@XmlJavaTypeAdapter(PersistentEntityAdapter.class)
@ToString(onlyExplicitlyIncluded = true)
@javax.persistence.Table(schema = "SIMPLE")
@javax.jdo.annotations.Queries({
        @javax.jdo.annotations.Query(
                name = KitArticulo.NAMED_QUERY__FIND_BY_CODIGO_LIKE,
                value = "SELECT " +
                        "FROM domainapp.modules.simple.dom.kitArticulo.KitArticulo " +
                        "WHERE codigo.indexOf(:codigo) >= 0"
        ),
        @javax.jdo.annotations.Query(
                name = KitArticulo.NAMED_QUERY__FIND_BY_CODIGO_EXACT,
                value = "SELECT " +
                        "FROM domainapp.modules.simple.dom.kitArticulo.KitArticulo " +
                        "WHERE codigo == :codigo"
        ),
        @javax.jdo.annotations.Query(
                name = KitArticulo.NAMED_QUERY__BUSCAR_POR_DESCRIPCION,
                value = "SELECT " +
                        "FROM domainapp.modules.simple.dom.kitArticulo.KitArticulo " +
                        "WHERE descripcion.toLowerCase().startsWith(:descripcion.toLowerCase()) "
        )
})
public class KitArticulo implements Comparable<KitArticulo>{

    @Inject
    RepositoryService repositoryService;
    @Inject
    TitleService titleService;
    @Inject
    MessageService messageService;

    @Inject
    JdoSupportService jdoSupportService;

    static final String NAMED_QUERY__FIND_BY_CODIGO_EXACT = "KitArticulo.findByCodigoExact";
    static final String NAMED_QUERY__FIND_BY_CODIGO_LIKE = "KitArticulo.findByCodigoLike";
    static final String NAMED_QUERY__BUSCAR_POR_DESCRIPCION = "KitArticulo.buscarPorDescripcion";


    public static KitArticulo withName(String descripcion) {
        val kitArticulo = new KitArticulo();
        kitArticulo.setEstadoOperativo(EstadoOperativo.MODIFICABLE);
        kitArticulo.setDescripcion(descripcion);
        kitArticulo.setEstadoHabDes(EstadoHabDes.HABILITADO);
        return kitArticulo;
    }

    @Title
    @CodigoCo
    @Getter
    @Setter
    @ToString.Include
    @PropertyLayout(fieldSetId = "kitArticulo", sequence = "1")
    private String codigo;

    @Descripcion
    @Getter @Setter @ToString.Include
    @PropertyLayout(fieldSetId = "kitArticulo", sequence = "2")
    private String descripcion;

    @Getter @Setter
    @PropertyLayout(fieldSetId = "kitArticulo", sequence = "3")
    private EstadoOperativo estadoOperativo;

    @Getter @Setter
    @PropertyLayout(fieldSetId = "kitArticulo", sequence = "4")
    private EstadoHabDes estadoHabDes;


    //Pasa el Kit a estado PREPARADO, para que este pueda ser utilizado por las distintas operaciones y que no se le puedan agregar mas items.
    @Action(semantics = NON_IDEMPOTENT_ARE_YOU_SURE)
    @ActionLayout(
            position = ActionLayout.Position.PANEL,
            describedAs = "Permite que el Kit sea utilizado para operaciones.")
    public KitArticulo preparado() {
        String nombre = this.getCodigo();
        final String title = titleService.titleOf(this);
        messageService.informUser(String.format("Kit '%s' se pasó a preparado.", title));
        this.setEstadoOperativo(EstadoOperativo.PREPARADO);
        return this;
    }

    //Devuelve el Kit al estado MODIFICABLE (estado por defecto) para que se le puedan agregar mas items. El kit no se va a poder utilizar en operaciones mientras este
    //en este estado.
    @Action(semantics = NON_IDEMPOTENT_ARE_YOU_SURE)
    @ActionLayout(
            position = ActionLayout.Position.PANEL,
            describedAs = "Permite que el Kit sea modificado.")
    public KitArticulo modificable() {
        String nombre = this.getCodigo();
        final String title = titleService.titleOf(this);
        messageService.informUser(String.format("Kit '%s' se pasó a modificable.", title));
        this.setEstadoOperativo(EstadoOperativo.MODIFICABLE);
        return this;
    }

    //La acción correspondiente a cada estado no va a ser visible si el Kit ya se encuentra en dicho estado.
    public boolean hidePreparado() {
        return this.getEstadoOperativo()==EstadoOperativo.PREPARADO;
    }

    public boolean hideModificable() {
        return this.getEstadoOperativo()==EstadoOperativo.MODIFICABLE;
    }


    //ESTADO GENERAL
    //Además de los estados MODIFICABLE Y PREPARADO, el Kit también podrá encontrarse habilitado o deshabilitado. Los deshabilitados no podrán utilizarse para operaciones tal como los
    //que se encuentren en estado de kit MODIFICABLE pero si se podran realizar acciones sobre ellos mismos (es decir agregar mas items, pasar a PREPARADO para que este inmediatamente
    //utilizable al pasarse al estado general HABILITADO)

    @Action(semantics = NON_IDEMPOTENT_ARE_YOU_SURE)
    @ActionLayout(
            position = ActionLayout.Position.PANEL,
            describedAs = "Habilita el kit")
    public KitArticulo habilitar() {
        String nombre = this.getCodigo();
        final String title = titleService.titleOf(this);
        messageService.informUser(String.format("Kit '%s' habilitado", title));
        this.setEstadoHabDes(EstadoHabDes.HABILITADO);
        return this;
    }

    @Action(semantics = NON_IDEMPOTENT_ARE_YOU_SURE)
    @ActionLayout(
            position = ActionLayout.Position.PANEL,
            describedAs = "Deshabilita el kit.")
    public KitArticulo deshabilitar() {
        String nombre = this.getCodigo();
        final String title = titleService.titleOf(this);
        messageService.informUser(String.format("Kit '%s' deshabilitado", title));
        this.setEstadoHabDes(EstadoHabDes.DESHABILITADO);
        return this;
    }

    public boolean hideHabilitar() {
        return this.getEstadoHabDes()== EstadoHabDes.HABILITADO;
    }

    public boolean hideDeshabilitar() {
        return this.getEstadoHabDes()== EstadoHabDes.DESHABILITADO;
    }


    private final static Comparator<KitArticulo> comparator =
            Comparator.comparing(KitArticulo::getCodigo);
    @Override
    public int compareTo(final KitArticulo other) {
        return comparator.compare(this, other);
    }

    @Programmatic
    public void ping() {
        JDOQLTypedQuery<KitArticulo> q = jdoSupportService.newTypesafeQuery(KitArticulo.class);
        final QKitArticulo candidate = QKitArticulo.candidate();
        q.range(0,2);
        q.orderBy(candidate.codigo.asc());
        q.executeList();
    }



}
