package domainapp.modules.simple.dom.item.itemKit;


import domainapp.modules.simple.dom.encabezado.kitArticulo.KitArticulo;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.isis.applib.annotation.Collection;
import org.apache.isis.applib.annotation.CollectionLayout;

import javax.inject.Inject;
import java.util.List;

@Collection
@CollectionLayout(defaultView = "table")
@RequiredArgsConstructor
public class KitArticulo_ItemKits {


    @Getter
    private final KitArticulo kitArticulo;

    public List<ItemKit> coll() {
        return itemKitRepository.buscarItemPorKit(kitArticulo) ;
    }

    @Inject ItemKitRepository itemKitRepository;
}