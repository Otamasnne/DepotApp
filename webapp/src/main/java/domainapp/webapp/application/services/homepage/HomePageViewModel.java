package domainapp.webapp.application.services.homepage;

import java.util.List;

import javax.inject.Inject;

import domainapp.modules.simple.config.Config;
import domainapp.modules.simple.dom.articulo.Articulo;
import domainapp.modules.simple.dom.articulo.Articulos;
import domainapp.modules.simple.service.IService;
import domainapp.modules.simple.service.Service;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.annotation.HomePage;
import org.apache.isis.applib.annotation.Nature;
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
        return getObjects().size() + " artículos";
    }

    public List<Articulo> getObjects() {
        return articulos.listAll();
    }

    @Inject
    Articulos articulos;
}
