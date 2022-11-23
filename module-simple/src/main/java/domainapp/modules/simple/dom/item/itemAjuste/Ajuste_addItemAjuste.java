package domainapp.modules.simple.dom.item.itemAjuste;

import domainapp.modules.simple.dom.EstadoOperativo;
import domainapp.modules.simple.dom.encabezado.ajuste.Ajuste;
import domainapp.modules.simple.dom.articulo.Articulo;
import domainapp.modules.simple.dom.encabezado.ajuste.TipoAjuste;
import lombok.RequiredArgsConstructor;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.Publishing;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.query.Query;
import org.apache.isis.applib.services.message.MessageService;
import org.apache.isis.applib.services.repository.RepositoryService;

import javax.inject.Inject;
import java.util.List;

@Action(
        semantics = SemanticsOf.IDEMPOTENT,
        commandPublishing = Publishing.ENABLED,
        executionPublishing = Publishing.ENABLED
)
@ActionLayout(associateWith = "itemsAjuste", sequence = "1")
@RequiredArgsConstructor
public class Ajuste_addItemAjuste {

    private final Ajuste ajuste;

    public Ajuste act(
            final Articulo articulo,
            final int cantidad
    ) {
        if (articulo.getStock() < cantidad & this.ajuste.getTipoAjuste() == TipoAjuste.AJN) {
            messageService.warnUser("El ajuste negativo por " + cantidad + " dejaria al artículo " + articulo.getCodigo() + " con stock negativo, esto podría generar problemas de stock a largo plazo.");
        }
        ItemAjuste item = repositoryService.persist(new ItemAjuste(ajuste,articulo,cantidad));
        ajuste.agregarItem(item);
        return ajuste;
    }

    public List<Articulo> choices0Act() {
        return repositoryService.allMatches(Query.named(Articulo.class, Articulo.NAMED_QUERY__FIND_BY_HABILITADO));
    }

    public String validate0Act(final Articulo articulo) {

        return itemAjusteRepository.buscarItemPorAjusteYArticulo(ajuste, articulo).isPresent()
                ? String.format("El artículo ingresado ya se encuentra en la lista de artículos de este ajuste.")
                : null;

    }

    public boolean hideAct() { return ajuste.getEstadoOperativo() == EstadoOperativo.COMPLETADO || ajuste.getEstadoOperativo() == EstadoOperativo.ANULADO; }

    @Inject
    MessageService messageService;
    @Inject ItemAjusteRepository itemAjusteRepository;
    @Inject
    RepositoryService repositoryService;

}
