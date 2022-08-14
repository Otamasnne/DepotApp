package domainapp.modules.simple.dom.item;

import domainapp.modules.simple.dom.kitArticulo.KitArticulo;
import lombok.RequiredArgsConstructor;
import org.apache.isis.applib.query.Query;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.util.List;

@Repository
@RequiredArgsConstructor(onConstructor_ = {@Inject} )
public class ItemKitRepository {
    private final RepositoryService repositoryService;

    public List<ItemKit> buscarItemPorKit(KitArticulo kitArticulo) {
        return repositoryService.allMatches(
                Query.named(ItemKit.class, "buscarItemPorKit")
                        .withParameter("kitArticulo", kitArticulo)
        );
    }
}
