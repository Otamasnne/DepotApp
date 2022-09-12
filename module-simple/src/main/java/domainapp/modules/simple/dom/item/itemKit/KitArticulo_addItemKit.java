package domainapp.modules.simple.dom.item.itemKit;

import domainapp.modules.simple.dom.articulo.Articulo;
import domainapp.modules.simple.dom.EstadoOperativo;
import domainapp.modules.simple.dom.encabezado.kitArticulo.KitArticulo;
import lombok.RequiredArgsConstructor;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.Publishing;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.repository.RepositoryService;

import javax.inject.Inject;
import java.util.List;


@Action(
        semantics = SemanticsOf.IDEMPOTENT,
        commandPublishing = Publishing.ENABLED,
        executionPublishing = Publishing.ENABLED
)
@ActionLayout(associateWith = "itemsKit", sequence = "1")
@RequiredArgsConstructor
public class KitArticulo_addItemKit {


    private final KitArticulo kitArticulo;

    public KitArticulo act(
            final Articulo articulo,
            final int cantidad
    ) {
       repositoryService.persist(new ItemKit(kitArticulo,articulo,cantidad));
       return kitArticulo;
    }

    public List<Articulo> choices0Act() {
        return repositoryService.allInstances(Articulo.class);
    }

    public String validate0Act(final Articulo articulo) {
        return itemKitRepository.buscarItemPorKitYArticulo(kitArticulo, articulo).isPresent()
                ? String.format("El artículo ingresado ya se encuentra en la lista de artículos de este Kit.")
                : null;
    }

    //Esta acción se esconde si el Kit tiene estado PREPARADO, para que no se puedan agregar mas items en un kit que se encuentra en uso.
    public boolean hideAct(){
        return kitArticulo.getEstadoOperativo() == EstadoOperativo.PREPARADO;
    }
    @Inject
    RepositoryService repositoryService;
    @Inject ItemKitRepository itemKitRepository;
}
