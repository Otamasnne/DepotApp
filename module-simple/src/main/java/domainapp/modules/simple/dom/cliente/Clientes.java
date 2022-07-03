package domainapp.modules.simple.dom.cliente;

import domainapp.modules.simple.types.cliente.CodigoCliente;
import domainapp.modules.simple.types.cliente.Dni;
import domainapp.modules.simple.types.cliente.RazonSocial;
import org.apache.isis.applib.annotation.*;
import org.apache.isis.applib.query.Query;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.apache.isis.persistence.jdo.applib.services.JdoSupportService;

import javax.inject.Inject;
import javax.jdo.JDOQLTypedQuery;
import java.util.List;

@DomainService(
        nature = NatureOfService.VIEW,
        logicalTypeName = "depotapp.Clientes"
)
@javax.annotation.Priority(PriorityPrecedence.EARLY)
@lombok.RequiredArgsConstructor(onConstructor_ = {@Inject} )
public class Clientes {

    final RepositoryService repositoryService;
    final JdoSupportService jdoSupportService;


    @Action(semantics = SemanticsOf.NON_IDEMPOTENT)
    @ActionLayout(promptStyle = PromptStyle.DIALOG_SIDEBAR)
    public Cliente create(
            @CodigoCliente final String codigo,
            @Dni final int dni,
            @RazonSocial final String razonSocial) {
        return repositoryService.persist(Cliente.creacion(codigo, dni, razonSocial));
    }

    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, promptStyle = PromptStyle.DIALOG_SIDEBAR)
    public List<Cliente> findByCodigo(
            @CodigoCliente final String codigo
    ) {
        return repositoryService.allMatches(
                Query.named(Cliente.class, Cliente.NAMED_QUERY__FIND_BY_CODIGO_LIKE )
                        .withParameter("codigo", codigo));
    }



    @Programmatic
    public Cliente findByCodigoExact(final String codigo) {
        return repositoryService.firstMatch(
                        Query.named(Cliente.class, Cliente.NAMED_QUERY__FIND_BY_CODIGO_EXACT)
                                .withParameter("codigo", codigo))
                .orElse(null);
    }



    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    public List<Cliente> listAll() {
        return repositoryService.allInstances(Cliente.class);
    }


    @Programmatic
    public void ping() {
        JDOQLTypedQuery<Cliente> q = jdoSupportService.newTypesafeQuery(Cliente.class);
        final QCliente candidate = QCliente.candidate();
        q.range(0,2);
        q.orderBy(candidate.codigo.asc());
        q.executeList();
    }



}
