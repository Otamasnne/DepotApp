package domainapp.modules.simple.dom.item;

import domainapp.modules.simple.dom.articulo.Articulo;
import domainapp.modules.simple.dom.kitArticulo.KitArticulo;
import lombok.RequiredArgsConstructor;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.Publishing;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.repository.RepositoryService;

import javax.inject.Inject;
import java.util.List;


@Action(
        semantics = SemanticsOf.IDEMPOTENT,
        commandPublishing = Publishing.ENABLED,
        executionPublishing = Publishing.ENABLED
)
@ActionLayout(associateWith = "itemsKit", sequence = "1")
@RequiredArgsConstructor
public class KitArticulo_addItemKit {


    private final KitArticulo kitArticulo;

    public KitArticulo act(
            final Articulo articulo,
            final int cantidad
    ) {
       repositoryService.persist(new ItemKit(kitArticulo,articulo,cantidad));
       return kitArticulo;
    }

    public List<Articulo> choices0Act() {
        return repositoryService.allInstances(Articulo.class);
    }

    @Inject
    RepositoryService repositoryService;

}
