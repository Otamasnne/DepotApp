package domainapp.modules.simple.dom.item;

import domainapp.modules.simple.dom.kitArticulo.KitArticulo;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.query.NamedQuery;
import org.apache.isis.applib.query.Query;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.springframework.stereotype.Repository;


import javax.inject.Inject;
import java.util.List;

@Repository
public class ItemKitRepository {

    @Programmatic
    public List<ItemKit> Listar(final KitArticulo kitArticulo) {
        return repositoryService.allMatches(
          Query.named(ItemKit.class, ItemKit.NAMED_QUERY__BUSCAR_ITEM_POR_KIT).withParameter("kitArticulo", kitArticulo)
        );
    }

    @Inject
    RepositoryService repositoryService;
}
