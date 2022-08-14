package domainapp.modules.simple.dom.item;

import domainapp.modules.simple.dom.articulo.Articulo;
import domainapp.modules.simple.dom.kitArticulo.KitArticulo;
import org.apache.isis.applib.annotation.*;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.apache.isis.persistence.jdo.applib.services.JdoSupportService;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;


@DomainService(
        nature = NatureOfService.VIEW,
        logicalTypeName = "depotapp.ItemsKit"
)
@javax.annotation.Priority(PriorityPrecedence.EARLY)
@lombok.RequiredArgsConstructor(onConstructor_ = {@Inject} )
public class ItemsKit {

    final RepositoryService repositoryService;
    final JdoSupportService jdoSupportService;

    @Action(semantics = SemanticsOf.NON_IDEMPOTENT)
    @ActionLayout(promptStyle = PromptStyle.DIALOG_SIDEBAR)
    public ItemKit create(
            final Articulo articulo,
            final int cantidad,
            final KitArticulo kitArticulo) {
        return repositoryService.persist(ItemKit.creacionItem(kitArticulo, articulo, cantidad));
    }


    public List<Articulo> choices0Create() {
        return repositoryService.allInstances(Articulo.class);
    }



    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    public List<ItemKit> listAll(KitArticulo kitArticulo) {
        return repositoryService.allInstances(ItemKit.class).stream().filter(x -> x.getKitArticulo().equals(kitArticulo)).collect(Collectors.toList());
    }

}
