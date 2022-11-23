package domainapp.modules.simple.fixture;

import domainapp.modules.simple.dom.articulo.Articulo;
import org.apache.isis.applib.services.registry.ServiceRegistry;
import org.apache.isis.testing.fixtures.applib.personas.PersonaWithBuilderScript;
import org.apache.isis.testing.fixtures.applib.personas.PersonaWithFinder;
import org.apache.isis.testing.fixtures.applib.setup.PersonaEnumPersistAll;

import domainapp.modules.simple.dom.articulo.Articulos;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Articulo_persona
implements PersonaWithBuilderScript<ArticuloBuilder>, PersonaWithFinder<Articulo> {

    FOO(1),
    BAR(2),
    BAZ(3),
    FRODO(4),
    FROYO(5),
    FIZZ(6);

    private final int codigo;

    @Override
    public ArticuloBuilder builder() {
        return new ArticuloBuilder().setCodigo(codigo);
    }

    @Override
    public Articulo findUsing(final ServiceRegistry serviceRegistry) {
        Articulos articulos = serviceRegistry.lookupService(Articulos.class).orElse(null);
        return articulos.findByCodigo(codigo);
    }

    public static class PersistAll
    extends PersonaEnumPersistAll<Articulo_persona, Articulo> {

        public PersistAll() {
            super(Articulo_persona.class);
        }
    }
}
