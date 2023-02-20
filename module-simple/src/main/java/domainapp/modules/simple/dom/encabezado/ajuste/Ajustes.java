package domainapp.modules.simple.dom.encabezado.ajuste;

import domainapp.modules.simple.dom.articulo.Articulo;
import domainapp.modules.simple.types.comprobante.CodigoCo;
import org.apache.isis.applib.annotation.*;
import org.apache.isis.applib.query.Query;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.apache.isis.persistence.jdo.applib.services.JdoSupportService;

import javax.inject.Inject;
import java.util.List;

@DomainService(
        nature = NatureOfService.VIEW,
        logicalTypeName = "depotapp.Ajustes"
)
@javax.annotation.Priority(PriorityPrecedence.EARLY)
@lombok.RequiredArgsConstructor(onConstructor_ = {@Inject} )
public class Ajustes {

    final RepositoryService repositoryService;
    final JdoSupportService jdoSupportService;

    @Action(semantics = SemanticsOf.NON_IDEMPOTENT)
    @ActionLayout(promptStyle = PromptStyle.DIALOG_SIDEBAR)
    public Ajuste ajustePositivo(
            final String descripcion
    )
    {
        return repositoryService.persist(Ajuste.creacion(TipoAjuste.AJP, descripcion));
    }

    @Action(semantics = SemanticsOf.NON_IDEMPOTENT)
    @ActionLayout(promptStyle = PromptStyle.DIALOG_SIDEBAR)
    public Ajuste ajusteNegativo(
            final String descripcion
    )
    {
        return repositoryService.persist(Ajuste.creacion(TipoAjuste.AJN, descripcion));
    }


    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    public List<Ajuste> listAll(){
        return repositoryService.allInstances(Ajuste.class);
    }

/*
    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, promptStyle = PromptStyle.DIALOG_SIDEBAR)
    public List<Ajuste> findByCodigo(
            final int codigo
    ) {
        return repositoryService.allMatches(
                Query.named(Ajuste.class, Ajuste.NAMED_QUERY__FIND_BY_CODIGO_LIKE)
                        .withParameter("codigo", codigo));
    }
*/

    //findByCodigoExact
    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, promptStyle = PromptStyle.DIALOG_SIDEBAR)
    public Ajuste findByCodigo(final Integer codigo) {
        return repositoryService.firstMatch(
                        Query.named(Ajuste.class, Ajuste.NAMED_QUERY__FIND_BY_CODIGO_EXACT)
                                .withParameter("codigoCo", codigo))
                .orElse(null);
    }

    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    public List<Ajuste> ajustesModificables() {
        return repositoryService.allMatches(
                Query.named(Ajuste.class, Ajuste.NAMED_QUERY__FIND_BY_MODIFICABLE)
        );
    }

    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    public List<Ajuste> ajustesCompletados() {
        return repositoryService.allMatches(
                Query.named(Ajuste.class, Ajuste.NAMED_QUERY__FIND_BY_COMPLETADO)
        );
    }

}
