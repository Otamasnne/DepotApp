package domainapp.modules.simple.dom.item.itemAjuste;

import domainapp.modules.simple.dom.ajuste.Ajuste;
import domainapp.modules.simple.dom.articulo.Articulo;
import domainapp.modules.simple.types.comprobante.CantidadMueve;
import lombok.*;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.annotation.PropertyLayout;
import org.apache.isis.applib.annotation.Publishing;
import org.apache.isis.applib.jaxb.PersistentEntityAdapter;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.VersionStrategy;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Comparator;

@javax.jdo.annotations.PersistenceCapable(
        schema = "depotapp",
        identityType= IdentityType.DATASTORE)
@javax.jdo.annotations.Unique(
        name = "ItemAjuste_articulo_ajuste_UNQ", members = {"articulo", "ajuste"}
)
@javax.jdo.annotations.Queries({
        @javax.jdo.annotations.Query(
                name = ItemAjuste.NAMED_QUERY__BUSCAR_ITEM_POR_AJUSTE,
                value = "SELECT " +
                        "FROM domainapp.modules.simple.dom.item.itemAjuste.ItemAjuste " +
                        "WHERE ajuste == :ajuste"
        ),
        @javax.jdo.annotations.Query(
                name = ItemAjuste.NAMED_QUERY__BUSCAR_ITEM_POR_AJUSTE_Y_ARTICULO,
                value = "SELECT " +
                        "FROM domainapp.modules.simple.dom.item.itemAjuste.ItemAjuste " +
                        "WHERE ajuste == :ajuste " +
                        "&& articulo == :articulo "
        )

})
@javax.jdo.annotations.DatastoreIdentity(strategy= IdGeneratorStrategy.IDENTITY, column="id")
@javax.jdo.annotations.Version(strategy= VersionStrategy.DATE_TIME, column="version")
@DomainObject(logicalTypeName = "depotapp.ItemAjuste", entityChangePublishing = Publishing.ENABLED)
@DomainObjectLayout()
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@XmlJavaTypeAdapter(PersistentEntityAdapter.class)
@ToString(onlyExplicitlyIncluded = true)
@javax.persistence.Table(schema = "SIMPLE")
public class ItemAjuste implements Comparable<ItemAjuste>{

    static final String NAMED_QUERY__BUSCAR_ITEM_POR_AJUSTE = "ItemAjuste.buscarItemPorAjuste";
    static final String NAMED_QUERY__BUSCAR_ITEM_POR_AJUSTE_Y_ARTICULO = "ItemAjuste.buscarItemPorAjusteYArticulo";
    ItemAjuste(Ajuste ajuste, Articulo articulo, int cantidad){
        this.ajuste = ajuste;
        this.articulo = articulo;
        this.cantidad = cantidad;
    }

    public static ItemAjuste creacionItem(Ajuste ajuste, Articulo articulo, int cantidad) {
        val item = new ItemAjuste();
        item.setArticulo(articulo);
        item.setCantidad(cantidad);
        item.setAjuste(ajuste);
        return item;
    }

    @Getter
    @Setter
    @ToString.Include
    @Column(allowsNull = "false")
    @PropertyLayout(fieldSetId = "itemAjuste", sequence = "1")
    private Articulo articulo;

    @CantidadMueve
    @Getter
    @Setter
    @ToString.Include
    @PropertyLayout(fieldSetId = "itemAjuste", sequence = "2")
    private int cantidad;

    @Getter
    @Setter
    @ToString.Include
    @Column(allowsNull = "false")
    @PropertyLayout(fieldSetId = "itemAjuste", sequence = "3")
    private Ajuste ajuste;

    private final static Comparator<ItemAjuste> comparator =
            Comparator.comparing(ItemAjuste::getAjuste).thenComparing(ItemAjuste::getArticulo);

    @Override
    public int compareTo(final ItemAjuste other) {
        return comparator.compare(this, other);
    }

}
