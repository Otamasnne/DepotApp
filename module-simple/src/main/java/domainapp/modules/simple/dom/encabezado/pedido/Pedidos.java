package domainapp.modules.simple.dom.encabezado.pedido;


import domainapp.modules.simple.dom.articulo.Articulo;
import domainapp.modules.simple.dom.cliente.Cliente;
import domainapp.modules.simple.dom.item.itemPedido.ItemPedido;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.JRException;
import org.apache.isis.applib.annotation.*;
import org.apache.isis.applib.query.Query;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.apache.isis.applib.value.Blob;
import org.apache.isis.persistence.jdo.applib.services.JdoSupportService;

import javax.inject.Inject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@DomainService(
        nature = NatureOfService.VIEW,
        logicalTypeName = "depotapp.Pedidos"
)
@javax.annotation.Priority(PriorityPrecedence.EARLY)
@RequiredArgsConstructor(onConstructor_ = {@Inject} )
public class Pedidos {

    final RepositoryService repositoryService;
    final JdoSupportService jdoSupportService;

    @Action(semantics = SemanticsOf.NON_IDEMPOTENT)
    @ActionLayout(promptStyle = PromptStyle.DIALOG_SIDEBAR)
    public Pedido create(
            final String descripcion,
            final Cliente cliente) {
        return repositoryService.persist(Pedido.withName(descripcion, cliente));
    }

    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(named = "Listado de Items")
    public List<ItemPedido> listItems() {
        return repositoryService.allInstances(ItemPedido.class);
    }

    public List<Cliente> choices1Create () {
        return repositoryService.allInstances(Cliente.class);
    }

    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    public List<Pedido> listAll() {
        return repositoryService.allInstances(Pedido.class);
    }

    public Pedido findByCodigoExact (final int codigo) {
        return repositoryService.firstMatch(
                Query.named(Pedido.class, Pedido.NAMED_QUERY_FIND_BY_CODIGO_EXACT)
                        .withParameter("codigo", codigo))
                .orElse(null);
    }

    @Programmatic
    public Blob generarReportePedido() throws JRException, IOException {
        List<Pedido> pedidos = new ArrayList<Pedido>();
        reportePadre ReportePadre = new reportePadre();
        pedidos = repositoryService.allInstances(Pedido.class);
        return ReportePadre.ListadoPedidosPDF(pedidos);
    }


}
