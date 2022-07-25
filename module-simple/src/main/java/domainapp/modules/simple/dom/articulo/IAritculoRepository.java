package domainapp.modules.simple.dom.articulo;

import domainapp.modules.simple.dom.pedidos.Pedido;

import java.util.List;


public interface IAritculoRepository {

    List<Articulo> findByPedido(Pedido pedido);

}
