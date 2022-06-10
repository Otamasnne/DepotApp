package domainapp.modules.simple.dom.articulo;


import domainapp.modules.simple.types.articulo.Codigo;
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

@javax.jdo.annotations.PersistenceCapable(
        schema = "DepotApp",
        identityType= IdentityType.DATASTORE)
@javax.jdo.annotations.Unique(
        name = "Articulo_codigo_UNQ", members = {"Codigo"}
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
        )
})
@javax.jdo.annotations.DatastoreIdentity(strategy= IdGeneratorStrategy.IDENTITY, column="id")
@javax.jdo.annotations.Version(strategy= VersionStrategy.DATE_TIME, column="version")
@DomainObject(logicalTypeName = "DepotApp.Articulo", entityChangePublishing = Publishing.ENABLED)
@DomainObjectLayout()
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@XmlJavaTypeAdapter(PersistentEntityAdapter.class)
@ToString(onlyExplicitlyIncluded = true)
public class Articulo implements Comparable<Articulo> {

    static final String NAMED_QUERY__FIND_BY_CODIGO_LIKE = "Articulo.findByCodigoLike";
    static final String NAMED_QUERY__FIND_BY_CODIGO_EXACT = "Articulo.findByCodigoExact";

    @Inject RepositoryService repositoryService;
    @Inject TitleService titleService;
    @Inject MessageService messageService;

    public Articulo(@NonNull int codigo) {
        this.codigo = codigo;
    }

    public static Articulo withName(int codigo ) {
        val articulo = new Articulo();
        articulo.setCodigo(codigo);
        return articulo;
    }

    @Title
    @Codigo
    @Getter
    @Setter
    @ToString.Include
    @PropertyLayout(fieldSetId = "codigo", sequence = "1")
    private int codigo;

    @Override
    public int compareTo(Articulo articulo) {
        return 0;
    }
}
