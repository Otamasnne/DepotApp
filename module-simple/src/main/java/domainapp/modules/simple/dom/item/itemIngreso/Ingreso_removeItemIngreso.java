package domainapp.modules.simple.dom.item.itemIngreso;

import domainapp.modules.simple.dom.EstadoOperativo;
import domainapp.modules.simple.dom.articulo.Articulo;
import domainapp.modules.simple.dom.ingreso.Ingreso;
import lombok.RequiredArgsConstructor;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.Publishing;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.repository.RepositoryService;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@Action(
        semantics = SemanticsOf.IDEMPOTENT,
        commandPublishing = Publishing.ENABLED,
        executionPublishing = Publishing.ENABLED
)
@ActionLayout(associateWith = "itemsIngreso", sequence = "2")
@RequiredArgsConstructor
public class Ingreso_removeItemIngreso {

    private final Ingreso ingreso;

    public Ingreso act(final Articulo articulo) {
        itemIngresoRepository.buscarItemPorIngresoYArticulo(ingreso, articulo)
                .ifPresent(itemIngreso -> repositoryService.remove(itemIngreso));
        return ingreso;
    }

    public boolean hideAct() {
        return itemIngresoRepository.buscarItemPorIngreso(ingreso).isEmpty() || ingreso.getEstadoOperativo() == EstadoOperativo.PROCESANDO;
    }

    public List<Articulo> choices0Act() {
        return itemIngresoRepository.buscarItemPorIngreso(ingreso).stream()
                .map(ItemIngreso::getArticulo).collect(Collectors.toList()); //Levanta el listado de artículos contenidos por los distintos items en el Ingreso
    }

    //Devuelve el primer artículo de item por defecto si no hay mas que un solo item en la lista del Ingreso.
    public Articulo default0Act() {
        List<Articulo> articulos = choices0Act(); //La lista se rellena a partir de lo que levanta como valores posibles el metodo choices0Act()
        return articulos.size() == 1 ? articulos.get(0) : null;
    }

    @Inject
    ItemIngresoRepository itemIngresoRepository;
    @Inject RepositoryService repositoryService;

}
