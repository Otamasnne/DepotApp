package domainapp.modules.simple.dom.itemPedido;

import domainapp.modules.simple.dom.articulo.Articulo;
import domainapp.modules.simple.dom.pedidos.Pedido;
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


    //Agregar metodo para modificar cantidad?
//    public String valid0Act(final Articulo articulo) {
//        return itemPedidoRepository.buscarItemPorPedido(pedido, articulo).isPresent()
//                ? String.format("El articulo ingresado ya esta en la lista de este pedido")
//                : null;
//    }

    @Inject
    RepositoryService repositoryService;
    @Inject
    ItemPedidoRepository itemPedidoRepository;
}