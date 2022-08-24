package domainapp.modules.simple.dom.pedidos;

import domainapp.modules.simple.dom.articulo.Articulo;
import domainapp.modules.simple.dom.itemPedido.ItemPedido;
import domainapp.modules.simple.dom.kitArticulo.KitArticulo;
import domainapp.modules.simple.types.pedido.CodigoPedido;
import lombok.*;
import org.apache.isis.applib.annotation.*;
import org.apache.isis.applib.jaxb.PersistentEntityAdapter;
import org.apache.isis.applib.query.Query;
import org.apache.isis.applib.services.message.MessageService;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.apache.isis.applib.services.title.TitleService;
import org.apache.isis.persistence.jdo.applib.services.JdoSupportService;

import javax.inject.Inject;
import javax.jdo.annotations.*;
import javax.persistence.Table;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Comparator;
import java.util.List;
import java.util.Vector;

@PersistenceCapable(
        schema = "depotapp",
        identityType= IdentityType.DATASTORE
)
@Unique(
        name = "Pedido_codigo_UNQ", members = {"codigo"}
)
@DatastoreIdentity(strategy= IdGeneratorStrategy.IDENTITY, column="id")
@Version(strategy= VersionStrategy.DATE_TIME, column="version")
@DomainObject(logicalTypeName = "depotapp.Pedido", entityChangePublishing = Publishing.ENABLED)
@DomainObjectLayout()
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@XmlJavaTypeAdapter(PersistentEntityAdapter.class)
@ToString(onlyExplicitlyIncluded = true)
@Table(schema = "SIMPLE")
@Queries({
        @javax.jdo.annotations.Query(
                name = Pedido.findByPedido,
                value = "SELECT " +
                        "FROM domainapp.modules.simple.dom.articulo.Articulo " +
                        "WHERE pedido == :pedido "
        ),
        @javax.jdo.annotations.Query(
                name = Pedido.NAMED_QUERY_FIND_BY_CODIGO_LIKE,
                value = "SELECT " +
                        "FROM domainapp.modules.simple.dom.pedidos.Pedido" +
                        "WHERE codigo.indexOf(:codigo) >= 0"
        ),
        @javax.jdo.annotations.Query(
                name = Pedido.NAMED_QUERY_FIND_BY_CODIGO_EXACT,
                value = "SELECT " +
                        "FROM domainapp.modules.simple.dom.pedidos.Pedido" +
                        "WHERE codigo == :codigo"
        )
})
public class Pedido implements Comparable<Pedido> {


    @Inject
    RepositoryService repositoryService;
    @Inject
    TitleService titleService;
    @Inject
    MessageService messageService;
    @Inject
    JdoSupportService jdoSupportService;

    static final String findByPedido = "Articulo.findByCodigoExact";
    static final String NAMED_QUERY_FIND_BY_CODIGO_EXACT = "pedido.findByCodigoExact";
    static final String NAMED_QUERY_FIND_BY_CODIGO_LIKE = "pedido.findByCodigoLike";

    @Title
    @CodigoPedido
    @Getter@Setter @ToString.Include
    @PropertyLayout(fieldSetId = "pedido", sequence = "1")
    private String codigo;

//    @javax.jdo.annotations.Column(allowsNull = "true", name = "estado")
//    @Property()
    @Getter@Setter
    @PropertyLayout(fieldSetId = "pedido", sequence = "3")
    private EstadoPedido estadoPedido;

    //private EstadoHabDes estadoHabDes;





    public static Pedido withName(String codigo) {
        val pedido = new Pedido();
        codigo = ("000000" + codigo).substring(codigo.length());
        pedido.setCodigo(codigo);
        return pedido;
    }

    // Pasa un pedido a estado Preparado
    public String preparado() {
        String nombre = this.getCodigo();
        final String title = titleService.titleOf(this);
        messageService.informUser(String.format("'$s' preparado.", title));
        this.setEstadoPedido(EstadoPedido.PREPARADO);
        return "Se paso el pedido " + nombre + " a Preparado.";
    }

    public String modificable() {
        String nombre = this.getCodigo();
        final String title = titleService.titleOf(this);
        messageService.informUser(String.format("'$s' modificable.", title));
        this.setEstadoPedido(EstadoPedido.MODIFICABLE);
        return "Se paso el pedido " + nombre + " a Modificable";
    }


    private final static Comparator<Pedido> comparator =
            Comparator.comparing(Pedido::getCodigo);
    @Override
    public int compareTo(final Pedido other) {
        return comparator.compare(this, other);
    }
}
