package domainapp.modules.simple.dom.ubicacion;


import domainapp.modules.simple.dom.EstadoHabDes;
import domainapp.modules.simple.types.articulo.CodigoArticulo;
import domainapp.modules.simple.types.articulo.Descripcion;
import org.apache.isis.applib.annotation.*;
import org.apache.isis.applib.query.Query;
import org.apache.isis.applib.services.message.MessageService;
import org.apache.isis.applib.services.repository.RepositoryService;

import javax.inject.Inject;
import java.util.List;

@DomainService(
        nature = NatureOfService.VIEW,
        logicalTypeName = "depotapp.Ubicaciones"
)
@javax.annotation.Priority(PriorityPrecedence.EARLY)
@lombok.RequiredArgsConstructor(onConstructor_ = {@Inject} )
public class Ubicaciones {

    @Inject RepositoryService repositoryService;
    @Inject MessageService messageService;

    @Action(semantics = SemanticsOf.NON_IDEMPOTENT)
    @ActionLayout(promptStyle = PromptStyle.DIALOG_SIDEBAR)
    public Ubicacion create(
            @Descripcion final String descripcion) {
        return repositoryService.persist(Ubicacion.creacion(descripcion));
    }

    /*
    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, promptStyle = PromptStyle.DIALOG_SIDEBAR)
    public List<Ubicacion> buscarPorCodigoLike(
            final int codigo
    ) {
        return repositoryService.allMatches(
                Query.named(Ubicacion.class, Ubicacion.NAMED_QUERY__BUSCAR_POR_CODIGO_LIKE)
                        .withParameter("codigo", codigo));
    }
*/


    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, promptStyle = PromptStyle.DIALOG_SIDEBAR)
    public Ubicacion buscarPorCodigoExacto(final int codigo) {
        Ubicacion ubicacion = repositoryService.firstMatch(
                        Query.named(Ubicacion.class, Ubicacion.NAMED_QUERY__BUSCAR_POR_CODIGO_EXACTO)
                                .withParameter("codigo", codigo)
                )
                .orElse(null);
        if (ubicacion.getEstado() == EstadoHabDes.DESHABILITADO) {
            messageService.informUser("ADVERTENCIA: Esta ubicación se encuentra deshabilitada. Habilitar nuevamente para poder utilizarla.");
        }
        return ubicacion;
    }

    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    public List<Ubicacion> listarTodos() {
        List<Ubicacion> ubicaciones = repositoryService.allInstances(Ubicacion.class);
        if (ubicaciones.size() == 1) {
            if (ubicaciones.get(0).getEstado() == EstadoHabDes.DESHABILITADO){
                messageService.informUser("ADVERTENCIA: Esta ubicación se encuentra deshabilitada. Habilitar nuevamente para poder utilizarla.");
            }
        }
        return ubicaciones;
    }

    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    public List<Ubicacion> ubicacionesHabilitadas() {
        return repositoryService.allMatches(
                Query.named(Ubicacion.class, Ubicacion.NAMED_QUERY__BUSCAR_HABILITADOS)
        );
    }

    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    public List<Ubicacion> ubicacionesDeshabilitadas() {
        return repositoryService.allMatches(
                Query.named(Ubicacion.class, Ubicacion.NAMED_QUERY__BUSCAR_DESHABILITADOS)
        );
    }

}
