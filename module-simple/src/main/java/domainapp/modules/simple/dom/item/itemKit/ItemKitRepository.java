package domainapp.modules.simple.dom.item.itemKit;

import domainapp.modules.simple.dom.articulo.Articulo;
import domainapp.modules.simple.dom.kitArticulo.KitArticulo;
import lombok.RequiredArgsConstructor;
import org.apache.isis.applib.query.Query;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor(onConstructor_ = {@Inject} )
public class ItemKitRepository {
    private final RepositoryService repositoryService;

    public List<ItemKit> buscarItemPorKit(KitArticulo kitArticulo) {
        return repositoryService.allMatches(
                Query.named(ItemKit.class, ItemKit.NAMED_QUERY__BUSCAR_ITEM_POR_KIT)
                        .withParameter("kitArticulo", kitArticulo));
    }

    public Optional<ItemKit> buscarItemPorKitYArticulo(KitArticulo kitArticulo, Articulo articulo) {
        return repositoryService.firstMatch(
                Query.named(ItemKit.class, ItemKit.NAMED_QUERY__BUSCAR_ITEM_POR_KIT_Y_ARTICULO)
                        .withParameter("kitArticulo", kitArticulo)
                        .withParameter("articulo", articulo));
    }
}
