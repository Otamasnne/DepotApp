package domainapp.modules.simple.dom.item.itemKit;

import domainapp.modules.simple.dom.EstadoOperativo;
import domainapp.modules.simple.dom.articulo.Articulo;
import domainapp.modules.simple.dom.encabezado.kitArticulo.KitArticulo;
import lombok.RequiredArgsConstructor;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.Publishing;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.query.Query;
import org.apache.isis.applib.services.message.MessageService;
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
        if (articulo.getStock() < cantidad) {
            messageService.warnUser("El " + articulo.title() + " se encuentra sin el stock necesario, el pedido podría tener una espera elevada.");
        }
       ItemKit item = repositoryService.persist(new ItemKit(kitArticulo,articulo,cantidad));
       kitArticulo.agregarItem(item);
       return kitArticulo;
    }

    public List<Articulo> choices0Act() {
        return repositoryService.allMatches(Query.named(Articulo.class, Articulo.NAMED_QUERY__FIND_BY_HABILITADO));
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
    MessageService messageService;
    @Inject
    RepositoryService repositoryService;
    @Inject ItemKitRepository itemKitRepository;
}
