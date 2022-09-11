package domainapp.modules.simple.dom.item.itemPedido;

import domainapp.modules.simple.dom.articulo.Articulo;
import domainapp.modules.simple.dom.pedido.Pedido;
import lombok.RequiredArgsConstructor;
import org.apache.isis.applib.query.Query;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor(onConstructor_ = {@Inject} )
public class ItemPedidoRepository {

    private final RepositoryService repositoryService;

    public List<ItemPedido> buscarItemPorPedido(Pedido pedido) {
        return repositoryService.allMatches(
                Query.named(ItemPedido.class, ItemPedido.NAMED_QUERY__BUSCAR_ITEM_POR_PEDIDO)
                        .withParameter("pedido", pedido));
    }

    public Optional<ItemPedido> buscarItemPorPedidoYArticulo(Pedido pedido, Articulo articulo) {
        return repositoryService.firstMatch(
                Query.named(ItemPedido.class, ItemPedido.NAMED_QUERY__BUSCAR_ITEM_POR_PEDIDO_Y_ARTICULO)
                        .withParameter("pedido", pedido)
                        .withParameter("articulo", articulo));
    }


}
