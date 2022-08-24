package domainapp.webapp.application.services.homepage;

import java.util.List;

import javax.inject.Inject;


import domainapp.modules.simple.dom.articulo.Articulo;
import domainapp.modules.simple.dom.articulo.Articulos;
import domainapp.modules.simple.dom.cliente.Cliente;
import domainapp.modules.simple.dom.cliente.Clientes;
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
        return getArticulos().size() + " artículos";
    }

    public List<Articulo> getArticulos() {
        return articulos.listAll();
    }

    public List<Cliente> getClientes() {
        return clientes.listAll();
    }

    public List<Proveedor> getProveedores() {
        return proveedores.listAll();
    }

    @Inject
    Articulos articulos;

    @Inject
    Clientes clientes;

    @Inject
    Proveedores proveedores;
}
