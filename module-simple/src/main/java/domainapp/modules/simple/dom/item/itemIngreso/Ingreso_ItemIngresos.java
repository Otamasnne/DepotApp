package domainapp.modules.simple.dom.item.itemIngreso;

import domainapp.modules.simple.dom.encabezado.ingreso.Ingreso;
import lombok.RequiredArgsConstructor;
import org.apache.isis.applib.annotation.Collection;
import org.apache.isis.applib.annotation.CollectionLayout;

import javax.inject.Inject;
import java.util.List;

@Collection
@CollectionLayout(defaultView = "table")
@RequiredArgsConstructor
public class Ingreso_ItemIngresos {

    private final Ingreso ingreso;

    public List<ItemIngreso> coll() { return itemIngresoRepository.buscarItemPorIngreso(ingreso) ; }

    @Inject
    ItemIngresoRepository itemIngresoRepository;
}
