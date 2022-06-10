package domainapp.webapp.application.fixture.scenarios;

import javax.inject.Inject;

import domainapp.modules.simple.fixture.Articulo_persona;
import org.apache.isis.testing.fixtures.applib.fixturescripts.FixtureScript;
import org.apache.isis.testing.fixtures.applib.modules.ModuleWithFixturesService;

public class DomainAppDemo extends FixtureScript {


    @Override
    protected void execute(final ExecutionContext ec) {
        ec.executeChildren(this, moduleWithFixturesService.getTeardownFixture());
        ec.executeChild(this, new Articulo_persona.PersistAll());
    }

    @Inject ModuleWithFixturesService moduleWithFixturesService;

}
