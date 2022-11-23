package domainapp.modules.simple.dom.reportes;

import domainapp.modules.simple.dom.EstadoHabDes;

import domainapp.modules.simple.dom.EstadoOperativo;
import lombok.Getter;
import lombok.Setter;



public class RepoArticulo {
    @Getter   @Setter
    private int codigo;
    @Getter   @Setter
    private String descripcion;
    @Getter   @Setter
    private Integer stock;
    @Getter @Setter
    private EstadoHabDes estado;


    public RepoArticulo(int codigo, String descripcion, Integer stock, EstadoHabDes estado) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.stock = stock;
        this.estado = estado;
    }

    public RepoArticulo() {}


   /* public String getCodigo(){
        return codigo;
    }
    public String getDescripcion(){
        return descripcion;
    }
    public Integer getStock(){
        return stock;
    }
    public EstadoHabDes getEstado(){
        return estado;
    }*/


}
