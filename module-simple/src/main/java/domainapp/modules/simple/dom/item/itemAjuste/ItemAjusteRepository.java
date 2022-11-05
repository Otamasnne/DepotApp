package domainapp.modules.simple.dom.item.itemAjuste;

import domainapp.modules.simple.dom.articulo.Articulo;
import domainapp.modules.simple.dom.encabezado.ajuste.Ajuste;
import lombok.RequiredArgsConstructor;
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
        return ajuste.getItems();
    }

    //TODO: Fijarse si esto se puede hacer mas efectivamente en una query, como estaba antes.
    public Optional<ItemAjuste> buscarItemPorAjusteYArticulo(Ajuste ajuste, Articulo articulo) {
        return ajuste.getItems().stream().filter(item -> item.getArticulo().equals(articulo)).findFirst();
    }
}
