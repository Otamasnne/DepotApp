package domainapp.modules.simple.dom.item.itemPedido;

import domainapp.modules.simple.dom.articulo.Articulo;
import domainapp.modules.simple.dom.encabezado.pedido.Pedido;
import lombok.RequiredArgsConstructor;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.SemanticsOf;
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
        return pedido.getItems();
    }

    public Optional<ItemPedido> buscarItemPorPedidoYArticulo(Pedido pedido, Articulo articulo) {
        return pedido.getItems().stream().filter(item -> item.getArticulo().equals(articulo)).findFirst();
    }


}
