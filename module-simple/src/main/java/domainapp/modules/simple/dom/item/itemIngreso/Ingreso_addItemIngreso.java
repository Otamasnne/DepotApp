package domainapp.modules.simple.dom.item.itemIngreso;

import domainapp.modules.simple.dom.EstadoOperativo;
import domainapp.modules.simple.dom.articulo.Articulo;
import domainapp.modules.simple.dom.encabezado.ingreso.Ingreso;
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
@ActionLayout(associateWith = "itemsIngreso", sequence = "1")
@RequiredArgsConstructor
public class Ingreso_addItemIngreso {


    private final Ingreso ingreso;

    public Ingreso act(
            final Articulo articulo,
            final Integer cantidad
    ) {
        ItemIngreso item = repositoryService.persist(new ItemIngreso(ingreso,articulo,cantidad));
        ingreso.agregarItem(item);
        return ingreso;
    }

    public List<Articulo> choices0Act() {
        return repositoryService.allMatches(Query.named(Articulo.class, Articulo.NAMED_QUERY__FIND_BY_HABILITADO));
    }

    public String validate0Act(final Articulo articulo) {
        return itemIngresoRepository.buscarItemPorIngresoYArticulo(ingreso, articulo).isPresent()
                ? String.format("El artículo ingresado ya se encuentra en la lista de artículos de este Ingreso.")
                : null;
    }

    //Esta acción se esconde si el Ingreso tiene estado PROCESANDO, para que no se puedan agregar mas items en un ingreso que se encuentre siendo procesado.
    public boolean hideAct(){
        return ingreso.getEstadoOperativo() == EstadoOperativo.PROCESANDO;
    }

    @Inject
    MessageService messageService;
    @Inject
    RepositoryService repositoryService;
    @Inject
    ItemIngresoRepository itemIngresoRepository;
}
