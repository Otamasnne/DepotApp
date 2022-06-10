package domainapp.webapp.application.services.homepage;

import java.util.List;

import javax.inject.Inject;

import domainapp.modules.simple.dom.articulo.Articulo;
import domainapp.modules.simple.dom.articulo.Articulos;
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
        return getObjects().size() + " artículos";
    }

    public List<Articulo> getObjects() {
        return articulos.listAll();
    }

    @Inject
    Articulos articulos;
}
