package domainapp.modules.simple.dom.pedido;

import domainapp.modules.simple.types.pedido.CodigoPedido;
import lombok.RequiredArgsConstructor;
import org.apache.isis.applib.annotation.*;
import org.apache.isis.applib.query.Query;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.apache.isis.persistence.jdo.applib.services.JdoSupportService;

import javax.inject.Inject;
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
            @CodigoPedido final String codigo) {
        return repositoryService.persist(Pedido.withName(codigo));
    }

    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    public List<Pedido> listAll() {
        return repositoryService.allInstances(Pedido.class);
    }

    public Pedido findByCodigoExact (final String codigo) {
        return repositoryService.firstMatch(
                Query.named(Pedido.class, Pedido.NAMED_QUERY_FIND_BY_CODIGO_EXACT)
                        .withParameter("codigo", codigo))
                .orElse(null);
    }

}
