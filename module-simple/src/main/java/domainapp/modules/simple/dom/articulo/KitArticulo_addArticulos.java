package domainapp.modules.simple.dom.articulo;

import domainapp.modules.simple.dom.kitArticulo.KitArticulo;
import domainapp.modules.simple.types.articulo.Codigo;
import domainapp.modules.simple.types.articulo.Descripcion;
import lombok.RequiredArgsConstructor;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.Publishing;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.repository.RepositoryService;

import javax.inject.Inject;

@Action(
        semantics = SemanticsOf.IDEMPOTENT,
        commandPublishing = Publishing.ENABLED,
        executionPublishing = Publishing.ENABLED
)
@ActionLayout(associateWith = "articulos", sequence = "1")
@RequiredArgsConstructor
public class KitArticulo_addArticulos {
    @Inject
    RepositoryService repositoryService;

    private final KitArticulo kitArticulo;

    public KitArticulo act (
            @Codigo final String codigo,
            @Descripcion final String descripcion) {
        return repositoryService.persist(KitArticulo.withName(codigo, descripcion));
    }
}
