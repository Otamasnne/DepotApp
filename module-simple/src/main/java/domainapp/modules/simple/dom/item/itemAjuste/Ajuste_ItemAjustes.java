package domainapp.modules.simple.dom.item.itemAjuste;

import domainapp.modules.simple.dom.encabezado.ajuste.Ajuste;
import lombok.RequiredArgsConstructor;
import org.apache.isis.applib.annotation.Collection;
import org.apache.isis.applib.annotation.CollectionLayout;

import javax.inject.Inject;
import java.util.List;

@Collection
@CollectionLayout(defaultView = "table")
@RequiredArgsConstructor
public class Ajuste_ItemAjustes {

    private final Ajuste ajuste;

    public List<ItemAjuste> coll() {
        return itemAjusteRepository.buscarItemPorAjuste(ajuste);
    }

    @Inject ItemAjusteRepository itemAjusteRepository;

}
