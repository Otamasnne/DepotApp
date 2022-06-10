package domainapp.webapp.application.services.health;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.stereotype.Service;

import org.apache.isis.applib.services.health.Health;
import org.apache.isis.applib.services.health.HealthCheckService;

import domainapp.modules.simple.dom.articulo.Articulos;

@Service
@Named("domainapp.HealthCheckServiceImpl")
public class HealthCheckServiceImpl implements HealthCheckService {

    private final Articulos articulos;

    @Inject
    public HealthCheckServiceImpl(Articulos articulos) {
        this.articulos = articulos;
    }

    @Override
    public Health check() {
        try {
            articulos.ping();
            return Health.ok();
        } catch (Exception ex) {
            return Health.error(ex);
        }
    }
}
