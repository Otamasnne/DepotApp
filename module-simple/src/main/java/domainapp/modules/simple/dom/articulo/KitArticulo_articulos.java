package domainapp.modules.simple.dom.articulo;


import domainapp.modules.simple.dom.kitArticulo.KitArticulo;
import lombok.RequiredArgsConstructor;
import org.apache.isis.applib.annotation.Collection;
import org.apache.isis.applib.annotation.CollectionLayout;

import java.util.List;

@Collection
@CollectionLayout(defaultView = "table")
@RequiredArgsConstructor
public class KitArticulo_articulos {

    private final KitArticulo kitArticulo;

    public List<Articulo> coll(){
        return articuloRepository.findByKitArticulo(kitArticulo);
    }



}
