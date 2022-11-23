
package domainapp.modules.simple.dom.reportes;

import domainapp.modules.simple.dom.articulo.Articulos;
import domainapp.modules.simple.dom.cliente.Clientes;
import domainapp.modules.simple.dom.encabezado.ingreso.Ingresos;
import domainapp.modules.simple.dom.encabezado.pedido.Pedidos;
import domainapp.modules.simple.dom.ubicacion.Ubicacion;
import domainapp.modules.simple.dom.ubicacion.Ubicaciones;
import net.sf.jasperreports.engine.JRException;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.apache.isis.applib.annotation.*;
import org.apache.isis.applib.value.Blob;
import javax.inject.Inject;
import java.io.IOException;

@DomainService(nature = NatureOfService.VIEW, logicalTypeName = "depotapp.Reportes")
class Reportes {

    @Action(semantics = SemanticsOf.SAFE, restrictTo = RestrictTo.PROTOTYPING)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, named = "Exportar Articulos PDF", sequence = "1")
    public Blob generarReporteArticulo() throws JRException, IOException{
        return articulos.generarReporteArticulo();
    }

    @Action(semantics = SemanticsOf.SAFE, restrictTo = RestrictTo.PROTOTYPING)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, named = "Exportar Clientes PDF", sequence = "1")
    public Blob generarReporteClientes() throws JRException, IOException{
        return clientes.generarReporteCliente();
    }

    @Action(semantics = SemanticsOf.SAFE, restrictTo = RestrictTo.PROTOTYPING)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, named = "Exportar Ingresos PDF", sequence = "1")
    public Blob generarReporteIngresos() throws JRException, IOException{
        return ingresos.generarReporteIngreso();
    }


    @Action(semantics = SemanticsOf.SAFE, restrictTo = RestrictTo.PROTOTYPING)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, named = "Exportar Pedidos PDF", sequence = "1")
    public Blob generarReportePedidos() throws JRException, IOException{
        return pedidos.generarReportePedido();
    }

    @Action(semantics = SemanticsOf.SAFE, restrictTo = RestrictTo.PROTOTYPING)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, named = "Exportar Ubicaciones PDF", sequence = "1")
    public Blob generarReporteUbicaciones() throws JRException, IOException{
        return ubicaciones.generarReporteUbicacion();
    }


    @Inject RepositoryService repositoryService;
    @Inject Articulos articulos;
    @Inject Clientes clientes;
    @Inject Ingresos ingresos;

    @Inject Pedidos pedidos;

    @Inject Ubicaciones ubicaciones;


}