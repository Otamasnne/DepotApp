package domainapp.modules.simple.dom.articulo;

import lombok.Getter;

public abstract class ItemComponent {


    /**
     * Agrego excepcion en los metodos para manejar el caso en el que algunos metodos sean solo necesarios para
     * articulo y otros solo relevantes para los kit de articulos.
     * @param itemComponent
     */
    public void add(ItemComponent itemComponent) {
        throw new UnsupportedOperationException("No se puede agregar item al kit");
    }

    public void remove(ItemComponent itemComponent){
        throw new UnsupportedOperationException("No se puede remover item del kit");
    }


//    public void getDescripcion() {
//        throw new UnsupportedOperationException("No se puede devolver el nombre");
//    }
//
//    public void print() {
//        throw new UnsupportedOperationException("No se puede mostrar");
//    }
}
