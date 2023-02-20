package domainapp.modules.simple.dom.encabezado.ingreso;

import domainapp.modules.simple.dom.cliente.Cliente;
import domainapp.modules.simple.dom.encabezado.ajuste.Ajuste;
import domainapp.modules.simple.dom.encabezado.pedido.Pedido;
import domainapp.modules.simple.dom.item.itemIngreso.ItemIngreso;
import domainapp.modules.simple.dom.item.itemPedido.ItemPedido;
import domainapp.modules.simple.dom.proveedor.Proveedor;
import domainapp.modules.simple.dom.reportes.reportePadre;
import domainapp.modules.simple.types.articulo.CodigoArticulo;
import domainapp.modules.simple.types.articulo.Descripcion;
import net.sf.jasperreports.engine.JRException;
import org.apache.isis.applib.annotation.*;
import org.apache.isis.applib.query.Query;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.apache.isis.applib.value.Blob;
import org.apache.isis.persistence.jdo.applib.services.JdoSupportService;

import javax.inject.Inject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@DomainService(
        nature = NatureOfService.VIEW,
        logicalTypeName = "depotapp.Ingresos"
)
@javax.annotation.Priority(PriorityPrecedence.EARLY)
@lombok.RequiredArgsConstructor(onConstructor_ = {@Inject} )
public class Ingresos {

    final RepositoryService repositoryService;
    final JdoSupportService jdoSupportService;

    @Action(semantics = SemanticsOf.NON_IDEMPOTENT)
    @ActionLayout(promptStyle = PromptStyle.DIALOG_SIDEBAR)
    public Ingreso persistir(
            final String descripcion
    )
    {
        return repositoryService.persist(Ingreso.crear(descripcion));
    }

    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    public List<Ingreso> listarTodos(){
        return repositoryService.allInstances(Ingreso.class);
    }

    /*
     * @Santi
     * Este metodo es llamado por la app movil para recuperar los ingresos en estado procesando
     * */
    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    public List<Ingreso> listProcesando() {
        return repositoryService.allMatches(
                Query.named(Ingreso.class, Ingreso.NAMED_QUERY_FIND_BY_PROCESANDO)
        );
    }

    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    public List<Ingreso> ingresosModificables() {
        return repositoryService.allMatches(
                Query.named(Ingreso.class, Ingreso.NAMED_QUERY__FIND_BY_MODIFICABLE)
        );
    }

    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    public List<Ingreso> ingresosCompletados() {
        return repositoryService.allMatches(
                Query.named(Ingreso.class, Ingreso.NAMED_QUERY__FIND_BY_COMPLETADO)
        );
    }

    /*
     * @Santi
     * es metodo es llamado por la app movil y recibe un codigo de ingreso para recuperar los articulos correspondientes
     * a este.
     * */
    @Action(semantics = SemanticsOf.SAFE)
    public List<ItemIngreso> listItems(Integer codigo) {

        Ingreso ingreso = porCodigo(codigo);

        return ingreso.getItems();
        //repositoryService.allInstances(ItemPedido.class);
    }

    //porCodigoExacto
    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, promptStyle = PromptStyle.DIALOG_SIDEBAR)
    public Ingreso porCodigo(final Integer codigo) {
        return repositoryService.firstMatch(
                        Query.named(Ingreso.class, Ingreso.NAMED_QUERY__FIND_BY_CODIGO_EXACT)
                                .withParameter("codigo", codigo))
                .orElse(null);
    }

    @Programmatic
    public Blob generarReporteIngreso() throws JRException, IOException {
        List<Ingreso> ingresos = new ArrayList<Ingreso>();
        reportePadre ReportePadre = new reportePadre();
        ingresos = repositoryService.allInstances(Ingreso.class);
        return ReportePadre.ListadoIngresosPDF(ingresos);
    }
}
