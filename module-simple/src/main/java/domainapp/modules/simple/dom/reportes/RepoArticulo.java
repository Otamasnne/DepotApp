package domainapp.modules.simple.dom.reportes;

import domainapp.modules.simple.dom.EstadoHabDes;
import domainapp.modules.simple.dom.articulo.Articulo;
import lombok.Getter;
import lombok.Setter;

import javax.inject.Inject;

public class RepoArticulo {
    @Getter   @Setter
    private String codigo;
    @Getter   @Setter
    private String descripcion;
    @Getter   @Setter
    private int stock;
    @Getter @Setter
    private EstadoHabDes estado;


    public RepoArticulo (String codigo, String descripcion, int stock){
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.stock = stock;
        this.estado = getEstado();

    }

    public RepoArticulo(){}
    @Inject Articulo articulo;

}
