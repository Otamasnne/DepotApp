package domainapp.modules.simple.dom.articulo;



import domainapp.modules.simple.dom.proveedor.Proveedores;
import domainapp.modules.simple.dom.reportes.reportePadre;
import domainapp.modules.simple.dom.proveedor.Proveedor;
import domainapp.modules.simple.dom.ubicacion.Ubicacion;
import domainapp.modules.simple.types.articulo.CodigoArticulo;
import domainapp.modules.simple.types.articulo.Descripcion;
import net.sf.jasperreports.engine.JRException;
import org.apache.isis.applib.annotation.*;
import org.apache.isis.applib.query.Query;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.apache.isis.applib.value.Blob;
import org.apache.isis.persistence.jdo.applib.services.JdoSupportService;

import javax.inject.Inject;
import javax.jdo.JDOQLTypedQuery;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@DomainService(
        nature = NatureOfService.VIEW,
        logicalTypeName = "depotapp.Articulos"
)
@javax.annotation.Priority(PriorityPrecedence.EARLY)
@lombok.RequiredArgsConstructor(onConstructor_ = {@Inject} )
public class Articulos {

    @Inject RepositoryService repositoryService;
    @Inject JdoSupportService jdoSupportService;

//    @Inject
//    Proveedores proveedores;

    @Action(semantics = SemanticsOf.NON_IDEMPOTENT)
    @ActionLayout(promptStyle = PromptStyle.DIALOG_SIDEBAR, named = "Crear Artículo", sequence = "1")
    public Articulo create(
            @Descripcion final String descripcion,
            final Proveedor proveedor,
            final Ubicacion ubicacion) {
        return repositoryService.persist(Articulo.withName(descripcion, proveedor, ubicacion));
    }

    public List<Proveedor> choices1Create() {
        return repositoryService.allMatches(Query.named(Proveedor.class, Proveedor.NAMED_QUERY__FIND_BY_HABILITADO));
    }
    public List<Ubicacion> choices2Create() {
        return repositoryService.allMatches(Query.named(Ubicacion.class, Ubicacion.NAMED_QUERY__BUSCAR_HABILITADOS));
    }


    //REPORTE ARTICULO
    @Programmatic
    public Blob generarReporteArticulo() throws JRException, IOException {
        List<Articulo> articulos = new ArrayList<Articulo>();
        reportePadre ReportePadre = new reportePadre();
        articulos = repositoryService.allInstances(Articulo.class);
        return ReportePadre.ListadoArticulosPDF(articulos);
    }

    //antes findByCodigoExact
    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, promptStyle = PromptStyle.DIALOG_SIDEBAR, named = "Buscar por Código", sequence = "3")
    public Articulo findByCodigo(final Integer codigo) {
        return repositoryService.firstMatch(
                        Query.named(Articulo.class, Articulo.NAMED_QUERY__FIND_BY_CODIGO_EXACT)
                                .withParameter("codigo", codigo))
                .orElse(null);
    }

    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, named = "Listar todos los Artículos", sequence = "2")
    public List<Articulo> listAll() {
        return repositoryService.allInstances(Articulo.class);
    }

    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, sequence = "4")
    public List<Articulo> articulosHabilitados() {
        return repositoryService.allMatches(
                Query.named(Articulo.class, Articulo.NAMED_QUERY__FIND_BY_HABILITADO)
        );
    }

    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, sequence = "5")
    public List<Articulo> articulosDeshabilitados() {
        return repositoryService.allMatches(
                Query.named(Articulo.class, Articulo.NAMED_QUERY__FIND_BY_DESHABILITADO)
        );
    }

   @Programmatic
  public void ping() {
       JDOQLTypedQuery<Articulo> q = jdoSupportService.newTypesafeQuery(Articulo.class);
       final QArticulo candidate = QArticulo.candidate();
        q.range(0,2);
        q.orderBy(candidate.codigo.asc());
        q.executeList();
    }

}
