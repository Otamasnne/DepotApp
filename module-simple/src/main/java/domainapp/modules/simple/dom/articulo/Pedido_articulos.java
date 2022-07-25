package domainapp.modules.simple.dom.articulo;

import domainapp.modules.simple.dom.pedidos.Pedido;
import lombok.RequiredArgsConstructor;
import org.apache.isis.applib.annotation.Collection;
import org.apache.isis.applib.annotation.CollectionLayout;

import javax.inject.Inject;
import java.util.List;

@Collection
@CollectionLayout(defaultView = "table")
@RequiredArgsConstructor
public class Pedido_articulos {

    private final Pedido pedido;

    public List<Articulo> coll() {
        return aritculoRepository.findByPedido(pedido);
    }

//    @Override
//    public List<Articulo> findByPedido(Pedido pedido) {
//        return aritculoRepository.findByPedido(pedido);
//    }

    @Inject
    IAritculoRepository aritculoRepository;

}
