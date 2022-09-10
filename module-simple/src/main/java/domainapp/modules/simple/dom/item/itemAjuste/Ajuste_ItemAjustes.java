package domainapp.modules.simple.dom.item.itemAjuste;

import domainapp.modules.simple.dom.ajuste.Ajuste;
import lombok.RequiredArgsConstructor;
import org.apache.isis.applib.annotation.Collection;
import org.apache.isis.applib.annotation.CollectionLayout;
import org.apache.isis.applib.query.Query;
import org.apache.isis.applib.services.repository.RepositoryService;

import javax.inject.Inject;
import java.util.List;

@Collection
@CollectionLayout(defaultView = "table")
@RequiredArgsConstructor
public class Ajuste_ItemAjustes {

    private final Ajuste ajuste;

    public List<ItemAjuste> coll() {
        return repositoryService.allMatches(
                Query.named(ItemAjuste.class, ItemAjuste.NAMED_QUERY__BUSCAR_ITEM_POR_AJUSTE)
                        .withParameter("ajuste", ajuste));
         }

    @Inject
    RepositoryService repositoryService;
}
