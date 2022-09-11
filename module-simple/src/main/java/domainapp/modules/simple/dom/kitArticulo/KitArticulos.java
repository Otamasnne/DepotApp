package domainapp.modules.simple.dom.kitArticulo;

import domainapp.modules.simple.types.articulo.CodigoArticulo;
import domainapp.modules.simple.types.articulo.Descripcion;
import org.apache.isis.applib.annotation.*;
import org.apache.isis.applib.query.Query;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.apache.isis.persistence.jdo.applib.services.JdoSupportService;

import javax.inject.Inject;
import java.util.List;

@DomainService(
        nature = NatureOfService.VIEW,
        logicalTypeName = "depotapp.KitArticulos"
)
@javax.annotation.Priority(PriorityPrecedence.EARLY)
@lombok.RequiredArgsConstructor(onConstructor_ = {@Inject} )
public class KitArticulos {

    final RepositoryService repositoryService;
    final JdoSupportService jdoSupportService;

    @Action(semantics = SemanticsOf.NON_IDEMPOTENT)
    @ActionLayout(promptStyle = PromptStyle.DIALOG_SIDEBAR)
    public KitArticulo create(
            @Descripcion final String descripcion
            )
            {
        return repositoryService.persist(KitArticulo.withName(descripcion));
    }

    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    public List<KitArticulo> listAll(){
        return repositoryService.allInstances(KitArticulo.class);
    }

    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, promptStyle = PromptStyle.DIALOG_SIDEBAR)
    public List<KitArticulo> findByCodigo(
            @CodigoArticulo final String codigo
    ) {
        return repositoryService.allMatches(
                Query.named(KitArticulo.class, KitArticulo.NAMED_QUERY__FIND_BY_CODIGO_LIKE)
                        .withParameter("codigo", codigo));
    }

    @Programmatic
    public KitArticulo findByCodigoExact(final String codigo) {
        return repositoryService.firstMatch(
                        Query.named(KitArticulo.class, KitArticulo.NAMED_QUERY__FIND_BY_CODIGO_EXACT)
                                .withParameter("codigo", codigo))
                .orElse(null);
    }

    public List<KitArticulo> buscarPorDescripcion(
            final String descripcion
    ) {
        return repositoryService.allMatches(
                Query.named(KitArticulo.class, KitArticulo.NAMED_QUERY__BUSCAR_POR_DESCRIPCION)
                        .withParameter("descripcion", descripcion));
    }

}
