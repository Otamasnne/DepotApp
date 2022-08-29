package domainapp.modules.simple.dom.item.itemPedido;

import domainapp.modules.simple.dom.articulo.Articulo;
import domainapp.modules.simple.dom.pedido.Pedido;
import lombok.RequiredArgsConstructor;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor(onConstructor_ = {@Inject} )
public class ItemPedidoRepository {

    private final RepositoryService repositoryService;

    public List<ItemPedido> buscarItemPorPedido(Pedido pedido) {
        return repositoryService.allInstances(ItemPedido.class).stream()
                .filter(x -> x.getPedido().equals(pedido))
                .collect(Collectors.toList());
    }


    public Optional<ItemPedido> buscarItemPorPedidoYArticulo(Pedido pedido, Articulo articulo) {
        return repositoryService.allInstances(ItemPedido.class).stream()
                .filter(x -> x.getPedido().equals(pedido))
                .filter(x -> x.getArticulo().equals(articulo))
                .findFirst();
    }


}
