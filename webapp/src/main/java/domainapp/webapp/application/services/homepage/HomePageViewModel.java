package domainapp.webapp.application.services.homepage;

import java.util.List;

import javax.inject.Inject;


import domainapp.modules.simple.dom.articulo.Articulo;
import domainapp.modules.simple.dom.articulo.Articulos;
import domainapp.modules.simple.dom.cliente.Cliente;
import domainapp.modules.simple.dom.cliente.Clientes;
import domainapp.modules.simple.dom.proveedor.Proveedores;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.annotation.HomePage;
import org.apache.isis.applib.annotation.Nature;


@DomainObject(
        nature = Nature.VIEW_MODEL,
        logicalTypeName = "simple.HomePageViewModel"
        )
@HomePage
@DomainObjectLayout()
public class HomePageViewModel {


    public String title() {
        return getObjects().size() + " DepotApp";
    }

    public List<Articulo> getObjects() {
        return articulos.listAll();
    }


    public List<Cliente> getObjects2() {
        return clientes.listAll();
    }

    public List<Cliente> getObjects3() {
        return clientes.listAll();
    }
    @Inject
    Articulos articulos;

    @Inject
    Clientes clientes;

    @Inject
    Proveedores proveedores;
}
