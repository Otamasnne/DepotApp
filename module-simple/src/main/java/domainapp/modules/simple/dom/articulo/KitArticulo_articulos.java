package domainapp.modules.simple.dom.articulo;

import domainapp.modules.simple.dom.kitArticulo.KitArticulo;
import lombok.RequiredArgsConstructor;
import org.apache.isis.applib.annotation.Collection;
import org.apache.isis.applib.annotation.CollectionLayout;
import org.apache.isis.applib.services.repository.RepositoryService;


import javax.inject.Inject;
import java.util.List;

//INVESTIGAR SOBRE ANOTACIONES @COLLECTION

@Collection
@CollectionLayout(defaultView = "table")
@RequiredArgsConstructor
public class KitArticulo_articulos {

    private final KitArticulo kitArticulo;

//    public List<Articulo> coll(){
//        return articuloRepository.findByKitArticulo(kitArticulo);
//    }

//    public List<Articulo> coll(){
//        return repositoryService.allMatches(KitArticulo.);
//    }

   //@Inject ArticuloRepository articuloRepository;

    //@Inject RepositoryService repositoryService;

}
