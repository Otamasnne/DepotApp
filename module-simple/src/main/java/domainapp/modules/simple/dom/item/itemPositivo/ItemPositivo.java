package domainapp.modules.simple.dom.item.itemPositivo;

import domainapp.modules.simple.dom.articulo.Articulo;
import domainapp.modules.simple.dom.comprobante.ajuste.AjustePositivo;
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
        name = "ItemPositivo_articulo_ajustePositivo_UNQ", members = {"articulo", "ajustePositivo"}
)
@javax.jdo.annotations.Queries({
        @javax.jdo.annotations.Query(
                name = ItemPositivo.NAMED_QUERY__BUSCAR_ITEM_POR_AJUSTEPOSITIVO,
                value = "SELECT " +
                        "FROM domainapp.modules.simple.dom.item.itemPositivo.ItemPositivo " +
                        "WHERE ajustePositivo.indexOf(:ajustePositivo) >= 0 "
        )
})
@javax.jdo.annotations.DatastoreIdentity(strategy= IdGeneratorStrategy.IDENTITY, column="id")
@javax.jdo.annotations.Version(strategy= VersionStrategy.DATE_TIME, column="version")
@DomainObject(logicalTypeName = "depotapp.ItemPositivo", entityChangePublishing = Publishing.ENABLED)
@DomainObjectLayout()
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@XmlJavaTypeAdapter(PersistentEntityAdapter.class)
@ToString(onlyExplicitlyIncluded = true)
@javax.persistence.Table(schema = "SIMPLE")
public class ItemPositivo implements Comparable<ItemPositivo>{

    static final String NAMED_QUERY__BUSCAR_ITEM_POR_AJUSTEPOSITIVO = "ItemKit.buscarItemPorAjustePositivo";

    ItemPositivo(AjustePositivo ajustePositivo, Articulo articulo, int cantidad){
        this.ajustePositivo = ajustePositivo;
        this.articulo = articulo;
        this.cantidad = cantidad;
    }

    public static ItemPositivo creacionItem(AjustePositivo ajustePositivo,Articulo articulo, int cantidad) {
        val item = new ItemPositivo();
        item.setArticulo(articulo);
        item.setCantidad(cantidad);
        item.setAjustePositivo(ajustePositivo);
        return item;
    }

    @Getter
    @Setter
    @ToString.Include
    @Column(allowsNull = "false")
    @PropertyLayout(fieldSetId = "itemPositivo", sequence = "1")
    private Articulo articulo;

    @CantidadMueve
    @Getter
    @Setter
    @ToString.Include
    @PropertyLayout(fieldSetId = "itemPositivo", sequence = "2")
    private int cantidad;

    @Getter
    @Setter
    @ToString.Include
    @Column(allowsNull = "false")
    @PropertyLayout(fieldSetId = "itemPositivo", sequence = "3")
    private AjustePositivo ajustePositivo;

    private final static Comparator<ItemPositivo> comparator =
            Comparator.comparing(ItemPositivo::getAjustePositivo).thenComparing(ItemPositivo::getArticulo);
    @Override
    public int compareTo(final ItemPositivo other) {
        return comparator.compare(this, other);
    }

}
