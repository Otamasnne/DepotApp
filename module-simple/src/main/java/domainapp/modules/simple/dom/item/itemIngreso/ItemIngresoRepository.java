package domainapp.modules.simple.dom.item.itemIngreso;

import domainapp.modules.simple.dom.articulo.Articulo;
import domainapp.modules.simple.dom.ingreso.Ingreso;
import lombok.RequiredArgsConstructor;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor(onConstructor_ = {@Inject} )
public class ItemIngresoRepository {

    private final RepositoryService repositoryService;

    public List<ItemIngreso> buscarItemPorIngreso(Ingreso ingreso) {
        return repositoryService.allInstances(ItemIngreso.class).stream()
                .filter(x -> x.getIngreso().equals(ingreso))
                .collect(Collectors.toList());
    }

    public Optional<ItemIngreso> buscarItemPorIngresoYArticulo(Ingreso ingreso, Articulo articulo) {
        return repositoryService.allInstances(ItemIngreso.class).stream()
                .filter(x -> x.getIngreso().equals(ingreso))
                .filter(x -> x.getArticulo().equals(articulo))
                .findFirst();
    }

}
