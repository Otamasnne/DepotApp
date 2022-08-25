package domainapp.modules.simple.dom.item.itemPedido;

import domainapp.modules.simple.dom.pedidos.Pedido;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.isis.applib.annotation.Collection;
import org.apache.isis.applib.annotation.CollectionLayout;

import javax.inject.Inject;
import java.util.List;

@Collection
@CollectionLayout(defaultView = "table")
@RequiredArgsConstructor
public class Pedido_ItemPedidos {


    @Getter
    private final Pedido pedido;

    public List<ItemPedido> coll() {
        return itemPedidoRepository.buscarItemPorPedido(pedido);
    }

    @Inject ItemPedidoRepository itemPedidoRepository;

}
