package domainapp.modules.simple.dom.item.itemKit;

import domainapp.modules.simple.dom.articulo.Articulo;
import domainapp.modules.simple.dom.kitArticulo.KitArticulo;
import lombok.RequiredArgsConstructor;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor(onConstructor_ = {@Inject} )
public class ItemKitRepository {
    private final RepositoryService repositoryService;

    public List<ItemKit> buscarItemPorKit(KitArticulo kitArticulo) {
        //return itemsKit.listAll(kitArticulo);
        return repositoryService.allInstances(ItemKit.class).stream()
                .filter(x -> x.getKitArticulo().equals(kitArticulo))
                .collect(Collectors.toList());
    }

    public Optional<ItemKit> buscarItemPorKitYArticulo(KitArticulo kitArticulo, Articulo articulo) {
        return repositoryService.allInstances(ItemKit.class).stream()
                .filter(x -> x.getKitArticulo().equals(kitArticulo))
                .filter(x -> x.getArticulo().equals(articulo))
                .findFirst();
    }
}
