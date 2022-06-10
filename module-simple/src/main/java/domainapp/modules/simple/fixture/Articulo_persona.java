package domainapp.modules.simple.fixture;

import domainapp.modules.simple.dom.articulo.Articulo;
import domainapp.modules.simple.types.articulo.Codigo;
import org.apache.isis.applib.services.registry.ServiceRegistry;
import org.apache.isis.testing.fixtures.applib.personas.PersonaWithBuilderScript;
import org.apache.isis.testing.fixtures.applib.personas.PersonaWithFinder;
import org.apache.isis.testing.fixtures.applib.setup.PersonaEnumPersistAll;

import domainapp.modules.simple.dom.articulo.Articulo;
import domainapp.modules.simple.dom.articulo.Articulos;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Articulo_persona
implements PersonaWithBuilderScript<ArticuloBuilder>, PersonaWithFinder<Articulo> {

    FOO("021423"),
    BAR("021342"),
    BAZ("022323"),
    FRODO("213343"),
    FROYO("025343"),
    FIZZ("061343"),
    BIP("121343"),
    BOP("021443"),
    BANG("021643"),
    BOO("012343");

    private final String codigo;

    @Override
    public ArticuloBuilder builder() {
        return new ArticuloBuilder().setCodigo(codigo);
    }

    @Override
    public Articulo findUsing(final ServiceRegistry serviceRegistry) {
        Articulos articulos = serviceRegistry.lookupService(Articulos.class).orElse(null);
        return articulos.findByCodigoExact(codigo);
    }

    public static class PersistAll
    extends PersonaEnumPersistAll<Articulo_persona, Articulo> {

        public PersistAll() {
            super(Articulo_persona.class);
        }
    }
}
