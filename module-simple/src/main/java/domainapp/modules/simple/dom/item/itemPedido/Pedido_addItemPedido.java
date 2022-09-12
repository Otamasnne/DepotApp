package domainapp.modules.simple.dom.item.itemPedido;

import domainapp.modules.simple.dom.articulo.Articulo;
import domainapp.modules.simple.dom.EstadoOperativo;
import domainapp.modules.simple.dom.encabezado.pedido.Pedido;
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
@ActionLayout(associateWith = "itemPedidos", sequence = "1")
@RequiredArgsConstructor
public class Pedido_addItemPedido {

    private final Pedido pedido;

    public Pedido act(
            final Articulo articulo,
            final int cantidad
            ) {
        repositoryService.persist(new ItemPedido(pedido,articulo,cantidad));
        return pedido;
    }

    public List<Articulo> choices0Act() {
        return repositoryService.allInstances(Articulo.class);
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
}
