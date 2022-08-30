package domainapp.modules.simple.dom.item.itemAjuste;

import domainapp.modules.simple.dom.EstadoOperativo;
import domainapp.modules.simple.dom.ajuste.Ajuste;
import domainapp.modules.simple.dom.articulo.Articulo;
import lombok.RequiredArgsConstructor;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.Publishing;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.repository.RepositoryService;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@Action(
        semantics = SemanticsOf.IDEMPOTENT,
        commandPublishing = Publishing.ENABLED,
        executionPublishing = Publishing.ENABLED
)
@ActionLayout(associateWith = "itemsAjuste", sequence = "2")
@RequiredArgsConstructor
public class Ajuste_removeItemAjuste {

    private final Ajuste ajuste;

    public Ajuste act(final Articulo articulo) {
        itemAjusteRepository.buscarItemPorAjusteYArticulo(ajuste, articulo)
                .ifPresent(itemAjuste -> repositoryService.remove(itemAjuste));

        return ajuste;
    }

    public boolean hideAct() {
        return itemAjusteRepository.buscarItemPorAjuste(ajuste).isEmpty() || ajuste.getEstadoOperativo() == EstadoOperativo.PREPARADO;
    }

    public List<Articulo> choices0Act() {
        return itemAjusteRepository.buscarItemPorAjuste(ajuste).stream()
                .map(ItemAjuste::getArticulo).collect(Collectors.toList());
    }

    public Articulo default0Act() {
        List<Articulo> articulos = choices0Act();
        return articulos.size() == 1 ? articulos.get(0) : null;
    }

    @Inject
    ItemAjusteRepository itemAjusteRepository;

    @Inject
    RepositoryService repositoryService;
}
