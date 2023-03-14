package domainapp.modules.simple.dom.proveedor;

import domainapp.modules.simple.dom.articulo.Articulo;
import domainapp.modules.simple.types.Email;
import domainapp.modules.simple.types.Telefono;
import domainapp.modules.simple.types.articulo.CodigoArticulo;
import domainapp.modules.simple.types.proveedor.RazonSocial;
import lombok.RequiredArgsConstructor;
import org.apache.isis.applib.annotation.*;
import org.apache.isis.applib.query.Query;
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

    @Action(semantics = SemanticsOf.NON_IDEMPOTENT)
    @ActionLayout(promptStyle = PromptStyle.DIALOG_SIDEBAR, named = "Crear Proveedor", sequence = "1")
    public Proveedor create(@RazonSocial final String razonSocial,
                            final String direccion,
                            final String localidad,
                            final @Telefono String telefono,
                            final @Email String email){
        return repositoryService.persist(Proveedor.withName(razonSocial, direccion, localidad, telefono, email));
    }

    //findByCodigoExact
    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, promptStyle = PromptStyle.DIALOG_SIDEBAR, named = "Buscar por Código", sequence = "2")
    public Proveedor findByCodigo(final Integer codigo) {
        return repositoryService.firstMatch(
                        Query.named(Proveedor.class, Proveedor.NAMED_QUERY__FIND_BY_CODIGO_EXACT)
                                .withParameter("codigo", codigo))
                .orElse(null);
    }

    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, sequence = "4")
    public List<Proveedor> proveedoresHabilitados() {
        return repositoryService.allMatches(
                Query.named(Proveedor.class, Proveedor.NAMED_QUERY__FIND_BY_HABILITADO)
        );
    }
    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, sequence = "5")
    public List<Proveedor> proveedoresDeshabilitados() {
        return repositoryService.allMatches(
                Query.named(Proveedor.class, Proveedor.NAMED_QUERY__FIND_BY_DESHABILITADO)
        );
    }

    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, named = "Lista de Proveedores", sequence = "3")
    public List<Proveedor> listAll(){
        return repositoryService.allInstances(Proveedor.class);
    }


}
