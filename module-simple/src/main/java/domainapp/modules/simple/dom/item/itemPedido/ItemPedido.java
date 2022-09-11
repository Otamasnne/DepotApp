package domainapp.modules.simple.dom.item.itemPedido;


import domainapp.modules.simple.dom.articulo.Articulo;
import domainapp.modules.simple.dom.pedido.Pedido;
import domainapp.modules.simple.types.comprobante.CantidadMueve;
import lombok.*;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.annotation.PropertyLayout;
import org.apache.isis.applib.annotation.Publishing;
import org.apache.isis.applib.jaxb.PersistentEntitiesAdapter;

import javax.jdo.annotations.*;
import javax.persistence.Table;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Comparator;

@PersistenceCapable(
        schema = "depotapp",
        identityType = IdentityType.DATASTORE
)
@Unique(
        name = "ItemPedido_articulo_pedido_UNQQ", members = {"articulo","pedido"}
)
@Queries(
        {
                @Query(
                        name = ItemPedido.NAMED_QUERY__BUSCAR_ITEM_POR_PEDIDO,
                        value = "SELECT " +
                                "FROM domainapp.modules.simple.dom.item.itemPedido.ItemPedido " +
                                "WHERE pedido == :pedido "
                ),
                @Query(
                        name = ItemPedido.NAMED_QUERY__BUSCAR_ITEM_POR_PEDIDO_Y_ARTICULO,
                        value = "SELECT " +
                                "FROM domainapp.modules.simple.dom.item.itemPedido.ItemPedido " +
                                "WHERE pedido == :pedido " +
                                "&& articulo == :articulo "
                )
        }
)
@DatastoreIdentity(strategy = IdGeneratorStrategy.IDENTITY, column = "id")
@Version(strategy = VersionStrategy.DATE_TIME, column = "version")
@DomainObject(logicalTypeName = "depotapp.ItemPedido", entityChangePublishing = Publishing.ENABLED)
@DomainObjectLayout()
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@XmlJavaTypeAdapter(PersistentEntitiesAdapter.class)
@ToString(onlyExplicitlyIncluded = true)
@Table(schema = "SIMPLE")
public class ItemPedido implements Comparable<ItemPedido> {

    static final String NAMED_QUERY__BUSCAR_ITEM_POR_PEDIDO = "ItemPedido.buscarItemPorPedido";
    static final String NAMED_QUERY__BUSCAR_ITEM_POR_PEDIDO_Y_ARTICULO = "ItemPedido.buscarItemPorPedidoYArticulo";

    ItemPedido(Pedido pedido, Articulo articulo, int cantidad){
        //val item = new ItemPedido();
        this.pedido = pedido;
        this.articulo = articulo;
        this.cantidad = cantidad;
    }

    @Getter@Setter@ToString.Include
    @Column(allowsNull = "false") //REVISAR ESTO
    @PropertyLayout(fieldSetId = "itemPedido", sequence = "1")
    private Articulo articulo;

    @Getter@Setter@ToString.Include
    @CantidadMueve
    @PropertyLayout(fieldSetId = "itemPedido", sequence = "2")
    private int cantidad;

    @Getter@Setter@ToString.Include
    @Column(allowsNull = "false")
    @PropertyLayout(fieldSetId = "itemPedido", sequence = "3")
    private Pedido pedido;



    private final static Comparator<ItemPedido> comparator =
            Comparator.comparing(ItemPedido::getPedido).thenComparing(ItemPedido::getArticulo);

    @Override
    public int compareTo(final ItemPedido other) {
        return comparator.compare(this, other);
    }
}
