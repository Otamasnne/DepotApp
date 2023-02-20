package domainapp.modules.simple.dom.cliente;


import domainapp.modules.simple.dom.articulo.Articulo;
import domainapp.modules.simple.dom.reportes.reportePadre;
import domainapp.modules.simple.types.Email;
import domainapp.modules.simple.types.Telefono;
import domainapp.modules.simple.types.cliente.Dni;
import domainapp.modules.simple.types.cliente.RazonSocial;
import net.sf.jasperreports.engine.JRException;
import org.apache.isis.applib.annotation.*;
import org.apache.isis.applib.query.Query;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.apache.isis.applib.value.Blob;
import org.apache.isis.persistence.jdo.applib.services.JdoSupportService;

import javax.inject.Inject;
import javax.jdo.JDOQLTypedQuery;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

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
            @Dni final Integer dni,
            @RazonSocial final String razonSocial,
            final String localidad,
            final String direccion,
            @Telefono final String telefono,
            @Email final String email
            ) {
        return repositoryService.persist(Cliente.creacion(dni, razonSocial, localidad, direccion, telefono, email));
    }

    //
    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, promptStyle = PromptStyle.DIALOG_SIDEBAR)
    public Cliente findByCodigo(final int codigo) {
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


    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    public List<Cliente> clientesHabilitados() {
        return repositoryService.allMatches(
                Query.named(Cliente.class, Cliente.NAMED_QUERY__FIND_BY_HABILITADO)
        );
    }

    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    public List<Cliente> clientesDeshabilitados() {
        return repositoryService.allMatches(
                Query.named(Cliente.class, Cliente.NAMED_QUERY__FIND_BY_DESHABILITADO)
        );
    }


    @Programmatic
    public void ping() {
        JDOQLTypedQuery<Cliente> q = jdoSupportService.newTypesafeQuery(Cliente.class);
        final QCliente candidate = QCliente.candidate();
        q.range(0,2);
        q.orderBy(candidate.codigo.asc());
        q.executeList();
    }


    @Programmatic
    public Blob generarReporteCliente() throws JRException, IOException {
        List<Cliente> clientes = new ArrayList<Cliente>();
        reportePadre ReportePadre = new reportePadre();
        clientes = repositoryService.allInstances(Cliente.class);
        return ReportePadre.ListadoClientesPDF(clientes);
    }
}
