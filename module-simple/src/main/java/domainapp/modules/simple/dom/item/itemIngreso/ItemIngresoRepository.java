package domainapp.modules.simple.dom.item.itemIngreso;

import domainapp.modules.simple.dom.articulo.Articulo;
import domainapp.modules.simple.dom.ingreso.Ingreso;
import lombok.RequiredArgsConstructor;
import org.apache.isis.applib.query.Query;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor(onConstructor_ = {@Inject} )
public class ItemIngresoRepository {

    private final RepositoryService repositoryService;

    public List<ItemIngreso> buscarItemPorIngreso(Ingreso ingreso) {
        return repositoryService.allMatches(
                Query.named(ItemIngreso.class, ItemIngreso.NAMED_QUERY__BUSCAR_ITEM_POR_INGRESO)
                        .withParameter("ingreso", ingreso));
    }

    public Optional<ItemIngreso> buscarItemPorIngresoYArticulo(Ingreso ingreso, Articulo articulo) {
        return repositoryService.firstMatch(
                Query.named(ItemIngreso.class, ItemIngreso.NAMED_QUERY__BUSCAR_ITEM_POR_INGRESO_Y_ARTICULO)
                        .withParameter("ingreso", ingreso)
                        .withParameter("articulo", articulo));
    }

}
