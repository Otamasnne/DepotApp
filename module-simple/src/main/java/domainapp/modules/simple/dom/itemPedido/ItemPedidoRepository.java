package domainapp.modules.simple.dom.itemPedido;

import domainapp.modules.simple.dom.pedidos.Pedido;
import lombok.RequiredArgsConstructor;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.util.List;
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



}
