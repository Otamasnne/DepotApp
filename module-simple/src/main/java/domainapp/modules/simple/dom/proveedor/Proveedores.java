package domainapp.modules.simple.dom.proveedor;

import domainapp.modules.simple.types.articulo.Codigo;
import domainapp.modules.simple.types.articulo.RazonSocial;
import lombok.RequiredArgsConstructor;
import org.apache.isis.applib.annotation.*;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.apache.isis.persistence.jdo.applib.services.JdoSupportService;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.jdo.JDOQLTypedQuery;
import java.util.List;

@DomainService(
        nature = NatureOfService.VIEW,
        logicalTypeName = "depotapp.Proveedores"
)
@Priority(PriorityPrecedence.EARLY)
@RequiredArgsConstructor(onConstructor_ = {@Inject} )
public class Proveedores {

    final RepositoryService repositoryService;
    final JdoSupportService jdoSupportService;

    //USAMOS "CODIGO" PARA LA MAYORIA O DEBERIAMOS HACER UN IDENTIFICADOR APARTE PARA PROVEEDOR??
    @Action(semantics = SemanticsOf.NON_IDEMPOTENT)
    @ActionLayout(promptStyle = PromptStyle.DIALOG_SIDEBAR)
    public Proveedor create(@Codigo final String codigo,
    @RazonSocial final String razonSocial){
        return repositoryService.persist(Proveedor.withName(codigo,razonSocial));
    }

    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    public List<Proveedor> listAll(){
        return repositoryService.allInstances(Proveedor.class);
    }
    @Programmatic
    public void ping() {
        JDOQLTypedQuery<Proveedor> q = jdoSupportService.newTypesafeQuery(Proveedor.class);
        final QProveedor candidate = QProveedor.candidate();
        q.range(0,2);
        q.orderBy(candidate.codigo.asc());
        q.executeList();
    }

}
