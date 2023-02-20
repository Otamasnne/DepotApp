package domainapp.webapp.application.services.homepage;

import java.util.List;

import javax.inject.Inject;


import domainapp.modules.simple.dom.articulo.Articulo;
import domainapp.modules.simple.dom.articulo.Articulos;
import domainapp.modules.simple.dom.cliente.Cliente;
import domainapp.modules.simple.dom.cliente.Clientes;
import domainapp.modules.simple.dom.encabezado.ingreso.Ingreso;
import domainapp.modules.simple.dom.encabezado.ingreso.Ingresos;
import domainapp.modules.simple.dom.encabezado.pedido.Pedido;
import domainapp.modules.simple.dom.encabezado.pedido.Pedidos;
import domainapp.modules.simple.dom.proveedor.Proveedor;
import domainapp.modules.simple.dom.proveedor.Proveedores;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.annotation.HomePage;
import org.apache.isis.applib.annotation.Nature;
import org.hibernate.validator.constraints.ru.INN;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


@DomainObject(
        nature = Nature.VIEW_MODEL,
        logicalTypeName = "simple.HomePageViewModel"
        )
@HomePage
@DomainObjectLayout()
public class HomePageViewModel {

    public String title() {
        String ped = getPedidos().size() == 1 ? " pedido y " : " pedidos y ";
        String ing = getIngresos().size() == 1 ? " ingreso siendo procesados." : " ingresos siendo procesados.";
        return getPedidos().size() + ped + getIngresos().size() + ing;
    }

    public List<Ingreso> getIngresos() {
        return ingresos.listProcesando();
    }

    public List<Pedido> getPedidos() {
        return pedidos.listProcesando();
    }

    @Inject
    Pedidos pedidos;

    @Inject
    Ingresos ingresos;
}
