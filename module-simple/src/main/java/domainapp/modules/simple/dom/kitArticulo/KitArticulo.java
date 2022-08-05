package domainapp.modules.simple.dom.kitArticulo;

import domainapp.modules.simple.dom.item.ItemKit;
import domainapp.modules.simple.types.articulo.CodigoKit;
import domainapp.modules.simple.types.articulo.Descripcion;
import lombok.*;
import org.apache.isis.applib.annotation.*;
import org.apache.isis.applib.jaxb.PersistentEntityAdapter;
import org.apache.isis.applib.services.message.MessageService;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.apache.isis.applib.services.title.TitleService;

import javax.inject.Inject;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.VersionStrategy;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Comparator;
import java.util.List;

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
                name = KitArticulo.NAMED_QUERY__FIND_BY_CODIGO_EXACT,
                value = "SELECT " +
                        "FROM domainapp.modules.simple.dom.articulo.Articulo " +
                        "WHERE codigo == :codigo"
        )
})
public class KitArticulo implements Comparable<KitArticulo>{

    @Inject
    RepositoryService repositoryService;
    @Inject
    TitleService titleService;
    @Inject
    MessageService messageService;

    static final String NAMED_QUERY__FIND_BY_CODIGO_EXACT = "Articulo.findByCodigoExact";

    public static KitArticulo withName(String codigo, String descripcion) {
        val kitArticulo = new KitArticulo();
        codigo = ("000000" + codigo).substring(codigo.length());
        kitArticulo.setCodigo(codigo);
        kitArticulo.setDescripcion(descripcion);
        return kitArticulo;
    }

    @Title
    @CodigoKit
    @Getter @Setter @ToString.Include
    @PropertyLayout(fieldSetId = "kitArticulo", sequence = "1")
    private String codigo;

    @Descripcion
    @Getter @Setter @ToString.Include
    @PropertyLayout(fieldSetId = "kitArticulo", sequence = "2")
    private String descripcion;

    @Getter@Setter@ToString.Include
    @Collection
    @PropertyLayout(fieldSetId = "kitArticulo", sequence ="3" )
    private List<ItemKit> articulos;


    @Action(semantics = NON_IDEMPOTENT_ARE_YOU_SURE)
    @ActionLayout(
            position = ActionLayout.Position.PANEL,
            describedAs = "Borra el kit y sus existencias.")
    public String borrar() {
        String nombre = this.getCodigo();
        final String title = titleService.titleOf(this);
        messageService.informUser(String.format("'%s' borrado", title));
        repositoryService.removeAndFlush(this);
        return "Se borró el Kit " + nombre;
    }


    //Prueba
    private final static Comparator<KitArticulo> comparator =
            Comparator.comparing(KitArticulo::getCodigo);
    @Override
    public int compareTo(final KitArticulo other) {
        return comparator.compare(this, other);
    }

}
