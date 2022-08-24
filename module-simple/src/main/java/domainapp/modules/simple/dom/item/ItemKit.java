package domainapp.modules.simple.dom.item;

import domainapp.modules.simple.dom.articulo.Articulo;
import domainapp.modules.simple.dom.kitArticulo.KitArticulo;
import domainapp.modules.simple.types.comprobante.CantidadMueve;
import lombok.*;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.annotation.PropertyLayout;
import org.apache.isis.applib.annotation.Publishing;
import org.apache.isis.applib.jaxb.PersistentEntityAdapter;
import org.apache.isis.applib.services.message.MessageService;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.apache.isis.applib.services.title.TitleService;

import javax.inject.Inject;
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
        name = "ItemKit_articulo_kit_UNQ", members = {"articulo", "kitArticulo"}
)
@javax.jdo.annotations.Queries({
        @javax.jdo.annotations.Query(
                name = ItemKit.NAMED_QUERY__BUSCAR_ITEM_POR_KIT,
                value = "SELECT " +
                        "FROM domainapp.modules.simple.dom.item.ItemKit " +
                        "WHERE kitArticulo.indexOf(:kitArticulo) >= 0 "
        )
})
@javax.jdo.annotations.DatastoreIdentity(strategy= IdGeneratorStrategy.IDENTITY, column="id")
@javax.jdo.annotations.Version(strategy= VersionStrategy.DATE_TIME, column="version")
@DomainObject(logicalTypeName = "depotapp.ItemKit", entityChangePublishing = Publishing.ENABLED)
@DomainObjectLayout()
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@XmlJavaTypeAdapter(PersistentEntityAdapter.class)
@ToString(onlyExplicitlyIncluded = true)
@javax.persistence.Table(schema = "SIMPLE")
public class ItemKit implements Comparable<ItemKit>{

    @Inject
    RepositoryService repositoryService;
    @Inject
    TitleService titleService;
    @Inject
    MessageService messageService;


    static final String NAMED_QUERY__BUSCAR_ITEM_POR_KIT = "ItemKit.buscarItemPorKit";
    ItemKit(KitArticulo kitArticulo, Articulo articulo, int cantidad){
        this.kitArticulo = kitArticulo;
        this.articulo = articulo;
        this.cantidad = cantidad;
    }
    public static ItemKit creacionItem(KitArticulo kitArticulo ,Articulo articulo, int cantidad) {

        val item = new ItemKit();
        item.setArticulo(articulo);
        item.setCantidad(cantidad);
        item.setKitArticulo(kitArticulo);
        return item;
    }

    @Getter
    @Setter
    @ToString.Include
    @Column(allowsNull = "false")
    @PropertyLayout(fieldSetId = "itemKit", sequence = "1")
    private Articulo articulo;

    @CantidadMueve
    @Getter
    @Setter
    @ToString.Include
    @PropertyLayout(fieldSetId = "itemKit", sequence = "2")
    private int cantidad;

    @Getter
    @Setter
    @ToString.Include
    @Column(allowsNull = "false")
    @PropertyLayout(fieldSetId = "itemKit", sequence = "3")
    private KitArticulo kitArticulo;

    private final static Comparator<ItemKit> comparator =
            Comparator.comparing(ItemKit::getKitArticulo).thenComparing(ItemKit::getArticulo);
    @Override
    public int compareTo(final ItemKit other) {
        return comparator.compare(this, other);
    }

}
