package domainapp.modules.simple.dom.item.itemKit;

import domainapp.modules.simple.dom.articulo.Articulo;
import domainapp.modules.simple.dom.kitArticulo.EstadoKit;
import domainapp.modules.simple.dom.kitArticulo.KitArticulo;
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
@ActionLayout(associateWith = "itemsKit", sequence = "2")
@RequiredArgsConstructor
public class KitArticulo_removeItemKit {

    private final KitArticulo kitArticulo;

    public KitArticulo act(final Articulo articulo) {

        itemKitRepository.buscarItemPorKitYArticulo(kitArticulo, articulo)
                .ifPresent(itemKit -> repositoryService.remove(itemKit));

        return kitArticulo;

    }

    //public boolean hideAct() {return itemKitRepository.buscarItemPorKit(kitArticulo).isEmpty();}

    public boolean hideAct() {
        return itemKitRepository.buscarItemPorKit(kitArticulo).isEmpty() || kitArticulo.getEstadoKit() == EstadoKit.PREPARADO;
    }

    public List<Articulo> choices0Act() {
        return itemKitRepository.buscarItemPorKit(kitArticulo).stream()
                .map(ItemKit::getArticulo).collect(Collectors.toList()); //Levanta el listado de artículos contenidos por los distintos items en el Kit
    }

    //Devuelve el primer artículo de item por defecto si no hay mas que un solo item en la lista del Kit.
    public Articulo default0Act() {
        List<Articulo> articulos = choices0Act(); //La lista se rellena a partir de lo que levanta como valores posibles el metodo choices0Act()
        return articulos.size() == 1 ? articulos.get(0) : null;
    }

    @Inject ItemKitRepository itemKitRepository;
    @Inject RepositoryService repositoryService;

}
