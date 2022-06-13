package domainapp.modules.simple.dom.proveedor;

import domainapp.modules.simple.dom.articulo.Articulo;

import javax.jdo.annotations.PersistenceCapable;

@PersistenceCapable
public class Proveedor implements Comparable<Proveedor>{



    @Override
    public int compareTo(Proveedor proveedor) {
        return 0;
    }
}
