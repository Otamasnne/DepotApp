package domainapp.modules.simple.dom.reportes;
import org.apache.isis.applib.value.Blob;
import domainapp.modules.simple.dom.articulo.Articulo;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class reportePadre {

    public Blob ListadoArticulosPDF(List<Articulo> articulos) throws JRException, IOException {

        List<RepoArticulo> repoArticulos = new ArrayList<RepoArticulo>();
        repoArticulos.add(new RepoArticulo());

        for (Articulo articulo : articulos) {
            RepoArticulo repoArticulo = new RepoArticulo();
            repoArticulos.add(repoArticulo);
        }

        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(repoArticulos);
        return GenerarArchivoPDF("reportePrueba.jrxml", "Listado de Articulos.pdf", ds);
    }

    public Blob ListadoArticulosPDF() throws JRException, IOException {

        return GenerarArchivoPDF("reportePrueba.jrxml", "Listado de Equipos.pdf", null);
    }

    private Blob GenerarArchivoPDF(String archivoDesing, String nombreSalida, JRBeanCollectionDataSource ds) throws JRException, IOException {

        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(archivoDesing);
        JasperDesign jasperDesign = JRXmlLoader.load(inputStream);
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("ds", ds);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, ds);
        byte[] contentBytes = JasperExportManager.exportReportToPdf(jasperPrint);

        return new Blob(nombreSalida, "application/pdf", contentBytes);
    }
}
