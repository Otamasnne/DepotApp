package domainapp.modules.simple.dom.reportes;

import domainapp.modules.simple.dom.EstadoHabDes;

import lombok.Getter;
import lombok.Setter;



public class RepoArticulo {
    @Getter @Setter
    private int id;
    @Getter   @Setter
    private String codigo;
    @Getter   @Setter
    private String descripcion;
    @Getter   @Setter
    private int stock;
    @Getter @Setter
    private EstadoHabDes estado;



    public RepoArticulo(){}
    public RepoArticulo(String codigo, String descripcion, Integer stock, EstadoHabDes estado) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.stock = stock;
        this.estado = estado;
    }

}
