package domainapp.modules.simple.dom.item;

import domainapp.modules.simple.dom.articulo.Articulo;
import domainapp.modules.simple.dom.kitArticulo.KitArticulo;
import domainapp.modules.simple.types.articulo.CodigoArticulo;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.Publishing;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.query.Query;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.apache.isis.persistence.jdo.applib.services.JdoSupportService;


import javax.inject.Inject;


@Action(
        semantics = SemanticsOf.IDEMPOTENT,
        commandPublishing = Publishing.ENABLED,
        executionPublishing = Publishing.ENABLED
)
@ActionLayout(associateWith = "items", sequence = "1")
@lombok.RequiredArgsConstructor(onConstructor_ = {@Inject} )
public class KitArticulo_addItem {


    private final KitArticulo kitArticulo;

    final RepositoryService repositoryService;
    final JdoSupportService jdoSupportService;
    public KitArticulo act(
            @CodigoArticulo final String codigo,
            final int cantidad
    ) {
        ItemKit.creacionItem(kitArticulo,codigo,cantidad);
        return kitArticulo;
    }


}
