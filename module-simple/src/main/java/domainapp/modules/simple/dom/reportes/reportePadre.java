package domainapp.modules.simple.dom.reportes;
import com.sun.tools.ws.wsdl.document.Input;
import domainapp.modules.simple.dom.cliente.Cliente;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
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

import javax.swing.*;
import java.io.FileInputStream;
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
            RepoArticulo repoArticulo = new RepoArticulo(articulo.getCodigo(),articulo.getDescripcion(),articulo.getStock(),articulo.getEstado());
            repoArticulos.add(repoArticulo);
        }

        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(repoArticulos);
        return GenerarArchivoPDF("repoArticulos.jrxml", "ListadoArticulos.pdf", ds);
    }

    public Blob ListadoClientesPDF(List<Cliente> clientes) throws JRException, IOException {

        List<RepoCliente> repoClientes = new ArrayList<RepoCliente>();
        repoClientes.add(new RepoCliente());

        for (Cliente cliente : clientes) {
            RepoCliente repoCliente = new RepoCliente(cliente.getCodigo(),cliente.getDni(),cliente.getRazonSocial());
            repoClientes.add(repoCliente);
        }

        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(repoClientes);
        return GenerarArchivoPDF("repoClientes.jrxml", "ListadoClientes.pdf", ds);
    }

    private Blob GenerarArchivoPDF(String archivoDesign, String nombreSalida, JRBeanCollectionDataSource ds) throws JRException, IOException {


        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(archivoDesign);
        //InputStream is = new FileInputStream("assets/PerfilPaciente.jrxml");


        //InputStream inputStream = new FileInputStream("../webapp/src/main/resources/repoClientes.jrxml");
        JasperDesign jasperDesign = JRXmlLoader.load(inputStream);
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("ds", ds);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, ds);

        byte[] contentBytes = JasperExportManager.exportReportToPdf(jasperPrint);

        return new Blob(nombreSalida, "application/pdf", contentBytes);

    }
}
