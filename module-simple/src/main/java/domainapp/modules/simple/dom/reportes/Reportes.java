package domainapp.modules.simple.dom.reportes;

import domainapp.modules.simple.dom.articulo.Articulos;
import net.sf.jasperreports.engine.JRException;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.apache.isis.applib.annotation.*;
import org.apache.isis.applib.value.Blob;
import javax.inject.Inject;
import java.io.IOException;

@DomainService(nature = NatureOfService.VIEW, logicalTypeName = "simple.Reportes")
    class Reportes {

    @Action(semantics = SemanticsOf.SAFE, restrictTo = RestrictTo.PROTOTYPING)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, named = "Exportar Articulos PDF", sequence = "1")
    public Blob generarReporteArticulo() throws JRException, IOException{
        return articulos.generarReporteArticulo();
    }


    @Inject RepositoryService repositoryService;
    @Inject Articulos articulos;

}
