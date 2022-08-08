package domainapp.modules.simple.dom.item;


import domainapp.modules.simple.dom.kitArticulo.KitArticulo;
import lombok.RequiredArgsConstructor;
import org.apache.isis.applib.annotation.Collection;
import org.apache.isis.applib.annotation.CollectionLayout;

import javax.inject.Inject;
import java.util.List;

@Collection
@CollectionLayout(defaultView = "table")
@lombok.RequiredArgsConstructor(onConstructor_ = {@Inject} )
public class KitArticulo_items {

    private final KitArticulo kitArticulo;
    private final ItemsKit itemsKit;

    public List<ItemKit> coll() {
        return  itemsKit.listAll();
    }


}