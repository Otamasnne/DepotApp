package domainapp.modules.simple.dom.pedidos;

import domainapp.modules.simple.dom.Item;
import domainapp.modules.simple.dom.articulo.Articulo;
import domainapp.modules.simple.dom.kitArticulo.KitArticulo;
import domainapp.modules.simple.types.pedido.CodigoPedido;
import lombok.*;
import org.apache.isis.applib.annotation.*;
import org.apache.isis.applib.jaxb.PersistentEntityAdapter;
import org.apache.isis.applib.query.Query;
import org.apache.isis.applib.services.message.MessageService;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.apache.isis.applib.services.title.TitleService;

import javax.inject.Inject;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.VersionStrategy;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Comparator;
import java.util.List;
import java.util.Vector;

@PersistenceCapable(
        schema = "depotapp",
        identityType= IdentityType.DATASTORE
)
@javax.jdo.annotations.Unique(
        name = "Pedido_codigo_UNQ", members = {"codigo"}
)
@javax.jdo.annotations.DatastoreIdentity(strategy= IdGeneratorStrategy.IDENTITY, column="id")
@javax.jdo.annotations.Version(strategy= VersionStrategy.DATE_TIME, column="version")
@DomainObject(logicalTypeName = "depotapp.Pedido", entityChangePublishing = Publishing.ENABLED)
@DomainObjectLayout()
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@XmlJavaTypeAdapter(PersistentEntityAdapter.class)
@ToString(onlyExplicitlyIncluded = true)
@javax.persistence.Table(schema = "SIMPLE")
@javax.jdo.annotations.Queries({
        @javax.jdo.annotations.Query(
                name = Pedido.findByPedido,
                value = "SELECT " +
                        "FROM domainapp.modules.simple.dom.articulo.Articulo " +
                        "WHERE pedido == :pedido "
        )
})
public class Pedido implements Comparable<Pedido> {


    @Inject
    RepositoryService repositoryService;
    @Inject
    TitleService titleService;
    @Inject
    MessageService messageService;

    static final String findByPedido = "Articulo.findByCodigoExact";

    @Title
    @CodigoPedido
    @Getter@Setter @ToString.Include
    private String codigo;

    @Getter@Setter@ToString.Include
    @Collection
    @PropertyLayout(fieldSetId = "pedido", sequence ="3" )
    private Vector<Item> items;


    public static Pedido withName(String codigo) {
        val pedido = new Pedido();
        codigo = ("000000" + codigo).substring(codigo.length());
        pedido.setCodigo(codigo);
        return pedido;
    }

    public Articulo findByCodigoExact(final String codigo) {
        return repositoryService.firstMatch(
                        Query.named(Articulo.class, Pedido.findByPedido)
                                .withParameter("codigo", codigo))
                .orElse(null);
    }


//    @Action(semantics = SemanticsOf.NON_IDEMPOTENT)
//    //@ActionLayout(promptStyle = PromptStyle.DIALOG_SIDEBAR)
//    @ActionLayout(associateWith = "articulos")
//    public void addItem(Articulo newItem){
//
//        //comprobar si ya existe este item en el pedido
//        int index = items.indexOf(newItem);
//
//        // fragmento de evento de pedido
//        if (index == -1) {
//            // Si el item es nuevo lo agrego
//            items.addElement(newItem);
//        }else {
//            //si ya existe, agrego cantidad
//            Articulo currItem = (Articulo) items.elementAt(index);
//
//            //currItem.add(newItem);
//        }
//
//    }

//    @Action(semantics = SemanticsOf.NON_IDEMPOTENT)
//    //@ActionLayout(promptStyle = PromptStyle.DIALOG_SIDEBAR)
//    @ActionLayout(associateWith = "articulos")
//    public void addItem(Articulo newItem){
//
//        //comprobar si ya existe este item en el pedido
//        int index = items.indexOf(newItem);
//
//        // fragmento de evento de pedido
//        if (index == -1) {
//            // Si el item es nuevo lo agrego
//            items.addElement(newItem);
//        }else {
//            //si ya existe, agrego cantidad
//            Articulo currItem = (Articulo) items.elementAt(index);
//
//            //currItem.add(newItem);
//        }
//
//    }

    @Action(semantics = SemanticsOf.NON_IDEMPOTENT)
    //@ActionLayout(promptStyle = PromptStyle.DIALOG_SIDEBAR)
    @ActionLayout(associateWith = "articulos")
    public void addArticulo(Articulo item, int cantidad){
        Item objetivo = Item.creacionItem(item, cantidad);

        repositoryService.persist(items.add(objetivo));
    }
//    @Action(semantics = SemanticsOf.NON_IDEMPOTENT)
//    //@ActionLayout(promptStyle = PromptStyle.DIALOG_SIDEBAR)
//    @ActionLayout(associateWith = "articulos")
//    public void addArticulo(String item){
//        Item objetivo = findByCodigoExact(item);
//
//        repositoryService.persist(items.add(objetivo));;
//    }

    private final static Comparator<Pedido> comparator =
            Comparator.comparing(Pedido::getCodigo);
    @Override
    public int compareTo(final Pedido other) {
        return comparator.compare(this, other);
    }
}
