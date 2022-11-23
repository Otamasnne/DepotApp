package domainapp.modules.simple.fixture;

import javax.inject.Inject;

import domainapp.modules.simple.dom.articulo.Articulo;
import domainapp.modules.simple.dom.articulo.Articulos;
import domainapp.modules.simple.dom.proveedor.Proveedor;
import domainapp.modules.simple.dom.ubicacion.Ubicacion;
import org.apache.isis.testing.fixtures.applib.personas.BuilderScriptWithResult;


import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.datanucleus.store.rdbms.query.PersistentClassROF;

@Accessors(chain = true)
public class ArticuloBuilder extends BuilderScriptWithResult<Articulo> {

    @Getter @Setter
    private int codigo;

    @Getter @Setter
    private String descripcion;

    @Getter @Setter
    private Proveedor proveedor;

    @Getter @Setter
    private Ubicacion ubicacion;

    @Override
    protected Articulo buildResult(final ExecutionContext ec) {

        checkParam("codigo", ec, int.class);
        checkParam("descripcion", ec, String.class);

        return wrap(articulos).create(descripcion, proveedor, ubicacion);
    }

    // -- DEPENDENCIES

    @Inject
    Articulos articulos;

}
