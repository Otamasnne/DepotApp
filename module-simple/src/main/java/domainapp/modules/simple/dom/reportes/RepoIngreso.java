package domainapp.modules.simple.dom.reportes;

import domainapp.modules.simple.dom.EstadoOperativo;
import domainapp.modules.simple.dom.proveedor.Proveedor;
import lombok.Getter;
import lombok.Setter;

public class RepoIngreso {

    @Getter @Setter
    private Integer codigo;


    @Getter @Setter
    private String descripcion;

    @Getter @Setter
    private EstadoOperativo estadoOperativo;



    public RepoIngreso(Integer codigo, String descripcion, EstadoOperativo estadoOperativo){
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.estadoOperativo = estadoOperativo;

    }


    public RepoIngreso() {}
}
