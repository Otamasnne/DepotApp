package domainapp.modules.simple.dom.item.itemKit;

import domainapp.modules.simple.dom.articulo.Articulo;
import domainapp.modules.simple.dom.encabezado.kitArticulo.KitArticulo;
import lombok.RequiredArgsConstructor;
import org.apache.isis.applib.query.Query;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import javax.mail.FetchProfile;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor(onConstructor_ = {@Inject} )
public class ItemKitRepository {

    public List<ItemKit> buscarItemPorKit(KitArticulo kitArticulo) {
        return kitArticulo.getItems();
    }

    public Optional<ItemKit> buscarItemPorKitYArticulo(KitArticulo kitArticulo, Articulo articulo) {
        return kitArticulo.getItems().stream().filter(itemKit -> itemKit.getArticulo().equals(articulo)).findFirst();
    }

}
