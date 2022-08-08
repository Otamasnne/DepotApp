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
import org.apache.isis.applib.query.Query;
import org.apache.isis.applib.services.message.MessageService;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.apache.isis.applib.services.title.TitleService;
import org.apache.isis.persistence.jdo.applib.services.JdoSupportService;

import javax.inject.Inject;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.VersionStrategy;
import javax.mail.FetchProfile;
import javax.persistence.EntityListeners;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Comparator;

@javax.jdo.annotations.Queries({
        @javax.jdo.annotations.Query(
                name = ItemKit.NAMED_QUERY__BUSCAR_ITEM_POR_KIT,
                value = "SELECT " +
                        "FROM domainapp.modules.simple.dom.item.ItemKit " +
                        "WHERE kitArticulo == :kitArticulo"
        )
})
@javax.jdo.annotations.DatastoreIdentity(strategy= IdGeneratorStrategy.IDENTITY, column="id")
@javax.jdo.annotations.Version(strategy= VersionStrategy.DATE_TIME, column="version")
@DomainObject(logicalTypeName = "depotapp.Item", entityChangePublishing = Publishing.ENABLED)
@DomainObjectLayout()
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@XmlJavaTypeAdapter(PersistentEntityAdapter.class)
@ToString(onlyExplicitlyIncluded = true)
public class ItemKit implements Comparable<ItemKit>{

    @Inject
    static
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
    public static ItemKit creacionItem(KitArticulo kitArticulo ,String codigo, int cantidad) {

        val item = new ItemKit();
        Articulo articulo = repositoryService.firstMatch(
                        Query.named(Articulo.class, Articulo.NAMED_QUERY__FIND_BY_CODIGO_EXACT)
                                .withParameter("codigo", codigo))
                .orElse(null);
        item.setArticulo(articulo);
        item.setCantidad(cantidad);
        return item;
    }

    @Getter
    @Setter
    @ToString.Include
    @PropertyLayout(fieldSetId = "item", sequence = "1")
    private String codigo;

    @Getter
    @Setter
    @ToString.Include
    @PropertyLayout(fieldSetId = "item", sequence = "2")
    private Articulo articulo;

    @CantidadMueve
    @Getter
    @Setter
    @ToString.Include
    @PropertyLayout(fieldSetId = "item", sequence = "3")
    private int cantidad;

    @Getter
    @Setter
    @ToString.Include
    @PropertyLayout(fieldSetId = "item", sequence = "4")
    private KitArticulo kitArticulo;


    private final static Comparator<ItemKit> comparator =
            Comparator.comparing(ItemKit::getCodigo);
    @Override
    public int compareTo(final ItemKit other) {
        return comparator.compare(this, other);
    }

}
