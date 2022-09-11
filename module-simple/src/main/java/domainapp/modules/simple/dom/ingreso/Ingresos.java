package domainapp.modules.simple.dom.ingreso;

import domainapp.modules.simple.dom.proveedor.Proveedor;
import domainapp.modules.simple.types.articulo.CodigoArticulo;
import domainapp.modules.simple.types.articulo.Descripcion;
import org.apache.isis.applib.annotation.*;
import org.apache.isis.applib.query.Query;
import org.apache.isis.applib.services.repository.RepositoryService;

import javax.inject.Inject;
import java.util.List;

@DomainService(
        nature = NatureOfService.VIEW,
        logicalTypeName = "depotapp.Ingresos"
)
@javax.annotation.Priority(PriorityPrecedence.EARLY)
@lombok.RequiredArgsConstructor(onConstructor_ = {@Inject} )
public class Ingresos {

    @Inject RepositoryService repositoryService;

    //TODO: REEMPLAZAR CODIGO POR CODIGO INCREMENTAL BD
    @Action(semantics = SemanticsOf.NON_IDEMPOTENT)
    @ActionLayout(promptStyle = PromptStyle.DIALOG_SIDEBAR)
    public Ingreso persistir(
            @CodigoArticulo final String codigo,
            @Descripcion final String descripcion
    )
    {
        return repositoryService.persist(Ingreso.crear(codigo, descripcion));
    }

    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    public List<Ingreso> listarTodos(){
        return repositoryService.allInstances(Ingreso.class);
    }

    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, promptStyle = PromptStyle.DIALOG_SIDEBAR)
    public List<Ingreso> porCodigo(
            @CodigoArticulo final String codigo
    ) {
        return repositoryService.allMatches(
                Query.named(Ingreso.class, Ingreso.NAMED_QUERY__FIND_BY_CODIGO_LIKE)
                        .withParameter("codigo", codigo));
    }

    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    public List<Ingreso> porProveedor(
            final Proveedor proveedor
            ) {
        return repositoryService.allMatches(
                Query.named(Ingreso.class, Ingreso.NAMED_QUERY__BUSCAR_POR_PROVEEDOR)
                        .withParameter("proveedor", proveedor));
    }

    public List<Proveedor> choices0PorProveedor(){
        return repositoryService.allInstances(Proveedor.class);
    }

    @Programmatic
    public Ingreso porCodigoExacto(final String codigo) {
        return repositoryService.firstMatch(
                        Query.named(Ingreso.class, Ingreso.NAMED_QUERY__FIND_BY_CODIGO_EXACT)
                                .withParameter("codigo", codigo))
                .orElse(null);
    }

}
