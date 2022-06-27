package domainapp.modules.simple.dom.kitArticulo;

import domainapp.modules.simple.dom.articulo.Articulo;
import domainapp.modules.simple.types.articulo.Codigo;
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
public class KitArticulo implements Comparable<KitArticulo>{

    @Inject
    RepositoryService repositoryService;
    @Inject
    TitleService titleService;
    @Inject
    MessageService messageService;

    public static KitArticulo withName(String codigo, String descripcion) {
        val kitArticulo = new KitArticulo();
        codigo = ("000000" + codigo).substring(codigo.length());
        kitArticulo.setCodigo(codigo);
        kitArticulo.setDescripcion(descripcion);
        return kitArticulo;
    }

    @Title
    @Codigo
    @Getter @Setter @ToString.Include
    @PropertyLayout(fieldSetId = "Kitarticulo", sequence = "1")
    private String codigo;

    @Descripcion
    @Getter @Setter @ToString.Include
    @PropertyLayout(fieldSetId = "Kitarticulo", sequence = "2")
    private String descripcion;


    private final static Comparator<KitArticulo> comparator =
            Comparator.comparing(KitArticulo::getCodigo);
    @Override
    public int compareTo(final KitArticulo other) {
        return comparator.compare(this, other);
    }

}
