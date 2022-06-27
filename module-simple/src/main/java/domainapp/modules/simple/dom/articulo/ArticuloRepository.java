package domainapp.modules.simple.dom.articulo;

import domainapp.modules.simple.dom.kitArticulo.KitArticulo;
import domainapp.modules.simple.types.articulo.Codigo;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArticuloRepository  extends Repository {

    //List<Articulo> findByKitArticulo(KitArticulo kitArticulo);

    Optional<Articulo> findByPetOwnerAndName(KitArticulo kitArticulo, String name);
}
