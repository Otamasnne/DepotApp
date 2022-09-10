package domainapp.modules.simple.dom.item.itemAjuste;

import domainapp.modules.simple.dom.ajuste.Ajuste;
import domainapp.modules.simple.dom.articulo.Articulo;
import lombok.RequiredArgsConstructor;
import org.apache.isis.applib.query.Query;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor(onConstructor_ = {@Inject} )
public class ItemAjusteRepository {

    private final RepositoryService repositoryService;

    public List<ItemAjuste> buscarItemPorAjuste(Ajuste ajuste) {
        return repositoryService.allMatches(
                Query.named(ItemAjuste.class, ItemAjuste.NAMED_QUERY__BUSCAR_ITEM_POR_AJUSTE)
                        .withParameter("ajuste", ajuste));
    }

    public Optional<ItemAjuste> buscarItemPorAjusteYArticulo(Ajuste ajuste, Articulo articulo) {
        return repositoryService.firstMatch(
                Query.named(ItemAjuste.class, ItemAjuste.NAMED_QUERY__BUSCAR_ITEM_POR_AJUSTE_Y_ARTICULO)
                        .withParameter("ajuste", ajuste)
                        .withParameter("articulo", articulo));
    }
}
