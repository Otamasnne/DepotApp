package domainapp.modules.simple.dom.articulo;

import domainapp.modules.simple.dom.kitArticulo.KitArticulo;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface ArticuloRepository extends Repository {

    List<Articulo> findByKitArticulo(KitArticulo kitArticulo);

}
