package domainapp.modules.simple.dom.item;


import domainapp.modules.simple.dom.kitArticulo.KitArticulo;
import lombok.RequiredArgsConstructor;
import org.apache.isis.applib.annotation.Collection;
import org.apache.isis.applib.annotation.CollectionLayout;

import javax.inject.Inject;
import java.util.List;

@Collection
@CollectionLayout(defaultView = "table")
@RequiredArgsConstructor
public class KitArticulo_items {


    private final KitArticulo kitArticulo;

    public List<ItemKit> coll() {
        return  itemKitRepository.Listar(kitArticulo);
    }

    @Inject ItemKitRepository itemKitRepository;

}