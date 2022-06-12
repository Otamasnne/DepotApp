package domainapp.modules.simple.fixture;

import javax.inject.Inject;

import domainapp.modules.simple.dom.articulo.Articulo;
import domainapp.modules.simple.dom.articulo.Articulos;
import org.apache.isis.testing.fixtures.applib.personas.BuilderScriptWithResult;


import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(chain = true)
public class ArticuloBuilder extends BuilderScriptWithResult<Articulo> {

    @Getter @Setter
    private String codigo;

    @Getter @Setter
    private String descripcion;

    @Override
    protected Articulo buildResult(final ExecutionContext ec) {

        checkParam("codigo", ec, int.class);
        checkParam("descripcion", ec, String.class);

        return wrap(articulos).create(codigo, descripcion);
    }

    // -- DEPENDENCIES

    @Inject
    Articulos articulos;

}
