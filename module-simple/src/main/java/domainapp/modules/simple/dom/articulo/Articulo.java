package domainapp.modules.simple.dom.articulo;


import domainapp.modules.simple.dom.kitArticulo.KitArticulo;
import domainapp.modules.simple.types.articulo.Codigo;
import domainapp.modules.simple.types.articulo.Descripcion;
import domainapp.modules.simple.types.articulo.Stock;
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
    static final String NAMED_QUERY__FIND_BY_CODIGO_EXACT = "Articulo.findByCodigoExact";

    @Inject RepositoryService repositoryService;
    @Inject TitleService titleService;
    @Inject MessageService messageService;

    // Agregar una entidad proveedor al constructor mas adelante.
    public static Articulo withName(String codigo, String descripcion) {
        val articulo = new Articulo();
        codigo = ("000000" + codigo).substring(codigo.length());
        articulo.setCodigo(codigo);
        articulo.setDescripcion(descripcion);
        return articulo;
    }


    @Action(semantics = NON_IDEMPOTENT_ARE_YOU_SURE)
    @ActionLayout(
            position = ActionLayout.Position.PANEL,
            describedAs = "Borra el artículo y sus existencias.")
    public String borrar() {
        String nombre = this.getCodigo();
        final String title = titleService.titleOf(this);
        messageService.informUser(String.format("'%s' borrado", title));
        repositoryService.removeAndFlush(this);
        return "Se borró el artículo " + nombre;
    }




    @Title
    @Codigo
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


    //@Stock
    @Getter
    @Setter
    @ToString.Include
    @PropertyLayout(fieldSetId = "articulo", sequence = "3")
    private Integer stock;

    //private Proveedor proveedor;

    @Getter
    @Setter
    @ToString.Include
    @PropertyLayout(fieldSetId = "name", sequence = "1")
    private KitArticulo kitArticulo;


    private final static Comparator<Articulo> comparator =
            Comparator.comparing(Articulo::getCodigo);
    @Override
    public int compareTo(final Articulo other) {
        return comparator.compare(this, other);
    }

}
