package domainapp.modules.simple.dom.item.itemPedido;

import domainapp.modules.simple.dom.articulo.Articulo;
import domainapp.modules.simple.dom.EstadoOperativo;
import domainapp.modules.simple.dom.encabezado.pedido.Pedido;
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
@ActionLayout(associateWith = "itemPedidos", sequence = "1")
@RequiredArgsConstructor
public class Pedido_addItemPedido {

    private final Pedido pedido;

    public Pedido act(
            final Articulo articulo,
            final int cantidad
            ) {
        if (articulo.getStock() < cantidad) {
            messageService.warnUser("El " + articulo.title() + " se encuentra sin el stock necesario, el pedido podría tener una espera elevada.");
        }
        ItemPedido item = repositoryService.persist(new ItemPedido(pedido,articulo,cantidad));
        pedido.agregarItem(item);
        return pedido;
    }

    public List<Articulo> choices0Act() {
        return repositoryService.allMatches(Query.named(Articulo.class, Articulo.NAMED_QUERY__FIND_BY_HABILITADO));
    }

    public String validate0Act(final Articulo articulo){
        return itemPedidoRepository.buscarItemPorPedidoYArticulo(pedido, articulo).isPresent()
                ? String.format("El artículo ingresado ya se encuentra en el pedido")
                : null;
    }

    //Esconde la acción para agregar items si está en estado PREPARADO
    public boolean hideAct() {
        return pedido.getEstadoOperativo() == EstadoOperativo.PROCESANDO;
    }

    @Inject
    RepositoryService repositoryService;
    @Inject
    ItemPedidoRepository itemPedidoRepository;
    @Inject
    MessageService messageService;
}
