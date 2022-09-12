package domainapp.modules.simple.dom.item.itemIngreso;


import domainapp.modules.simple.dom.articulo.Articulo;
import domainapp.modules.simple.dom.encabezado.ingreso.Ingreso;
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
        name = "ItemIngreso_articulo_ingreso_UNQQ", members = {"articulo","ingreso"}
)
@Queries(
        {
                @Query(
                        name = ItemIngreso.NAMED_QUERY__BUSCAR_ITEM_POR_INGRESO,
                        value = "SELECT " +
                                "FROM domainapp.modules.simple.dom.item.itemIngreso.ItemIngreso " +
                                "WHERE ingreso == :ingreso "
                ),
                @Query(
                        name = ItemIngreso.NAMED_QUERY__BUSCAR_ITEM_POR_INGRESO_Y_ARTICULO,
                        value = "SELECT " +
                                "FROM domainapp.modules.simple.dom.item.itemIngreso.ItemIngreso " +
                                "WHERE ingreso == :ingreso " +
                                "&& articulo == :articulo "
                )
        }
)
@DatastoreIdentity(strategy = IdGeneratorStrategy.IDENTITY, column = "id")
@Version(strategy = VersionStrategy.DATE_TIME, column = "version")
@DomainObject(logicalTypeName = "depotapp.ItemIngreso", entityChangePublishing = Publishing.ENABLED)
@DomainObjectLayout()
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@XmlJavaTypeAdapter(PersistentEntitiesAdapter.class)
@ToString(onlyExplicitlyIncluded = true)
@Table(schema = "SIMPLE")
public class ItemIngreso implements Comparable<ItemIngreso> {

    static final String NAMED_QUERY__BUSCAR_ITEM_POR_INGRESO = "ItemIngreso.buscarItemPorIngreso";
    static final String NAMED_QUERY__BUSCAR_ITEM_POR_INGRESO_Y_ARTICULO = "ItemIngreso.buscarItemPorIngresoYArticulo";
    ItemIngreso(Ingreso ingreso, Articulo articulo, int cantidad){
        this.ingreso = ingreso;
        this.articulo = articulo;
        this.cantidad = cantidad;
    }

    @Getter @Setter @ToString.Include
    @Column(allowsNull = "false")
    @PropertyLayout(fieldSetId = "itemPedido", sequence = "1")
    private Articulo articulo;

    @Getter @Setter @ToString.Include
    @CantidadMueve
    @PropertyLayout(fieldSetId = "itemPedido", sequence = "2")
    private int cantidad;

    @Getter @Setter @ToString.Include
    @Column(allowsNull = "false")
    @PropertyLayout(fieldSetId = "itemPedido", sequence = "3")
    private Ingreso ingreso;

    private final static Comparator<ItemIngreso> comparator =
            Comparator.comparing(ItemIngreso::getIngreso).thenComparing(ItemIngreso::getArticulo);

    @Override
    public int compareTo(final ItemIngreso other) {
        return comparator.compare(this, other);
    }
}
