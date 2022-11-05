package domainapp.modules.simple.dom.item.itemIngreso;

import domainapp.modules.simple.dom.articulo.Articulo;
import domainapp.modules.simple.dom.encabezado.ingreso.Ingreso;
import lombok.RequiredArgsConstructor;
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
        return ingreso.getItems();
    }

    public Optional<ItemIngreso> buscarItemPorIngresoYArticulo(Ingreso ingreso, Articulo articulo) {
        return ingreso.getItems().stream().filter(item -> item.getArticulo().equals(articulo)).findFirst();
    }

}
