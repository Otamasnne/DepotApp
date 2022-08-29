package domainapp.modules.simple.dom.item.itemAjuste;

import domainapp.modules.simple.dom.ajuste.Ajuste;
import domainapp.modules.simple.dom.articulo.Articulo;
import lombok.RequiredArgsConstructor;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor(onConstructor_ = {@Inject} )
public class ItemAjusteRepository {

    private final RepositoryService repositoryService;

    public List<ItemAjuste> buscarItemPorAjuste(Ajuste ajuste) {
        return repositoryService.allInstances(ItemAjuste.class).stream()
                .filter(x -> x.getAjuste().equals(ajuste))
                .collect(Collectors.toList());
    }

    public Optional<ItemAjuste> buscarItemPorAjusteYArticulo(Ajuste ajuste, Articulo articulo) {
        return repositoryService.allInstances(ItemAjuste.class).stream()
                .filter(x -> x.getAjuste().equals(ajuste))
                .filter(x -> x.getArticulo().equals(articulo))
                .findFirst();

    }
}
