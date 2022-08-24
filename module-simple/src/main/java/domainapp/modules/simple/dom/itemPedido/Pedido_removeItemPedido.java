package domainapp.modules.simple.dom.itemPedido;


import domainapp.modules.simple.dom.articulo.Articulo;
import domainapp.modules.simple.dom.pedidos.EstadoPedido;
import domainapp.modules.simple.dom.pedidos.Pedido;
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
@ActionLayout(associateWith = "itemPedidos", sequence = "2")
@RequiredArgsConstructor
public class Pedido_removeItemPedido {

    private final Pedido pedido;

    public Pedido act(final Articulo articulo) {
        itemPedidoRepository.buscarItemPorPedidoYArticulo(pedido, articulo)
                .ifPresent(itemPedido -> repositoryService.remove(itemPedido));

        return pedido;
    }

    public boolean hideAct() {
        return itemPedidoRepository.buscarItemPorPedido(pedido).isEmpty() || pedido.getEstadoPedido() == EstadoPedido.PREPARADO;
    }

    public List<Articulo> choices0Act(){
        return itemPedidoRepository.buscarItemPorPedido(pedido).stream()
                .map(ItemPedido::getArticulo).collect(Collectors.toList());
    }





    @Inject
    RepositoryService repositoryService;
    @Inject
    ItemPedidoRepository itemPedidoRepository;

}
