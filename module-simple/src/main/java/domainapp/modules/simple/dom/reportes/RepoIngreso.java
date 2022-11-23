package domainapp.modules.simple.dom.reportes;

import domainapp.modules.simple.dom.EstadoOperativo;
import domainapp.modules.simple.dom.proveedor.Proveedor;
import lombok.Getter;
import lombok.Setter;

public class RepoIngreso {

    @Getter @Setter
    private String codigo;


    @Getter @Setter
    private String descripcion;

    @Getter @Setter
    private EstadoOperativo estadoOperativo;

    @Getter @Setter
    private Proveedor proveedor;

    public RepoIngreso(String codigo, String descripcion, EstadoOperativo estadoOperativo, Proveedor proveedor){
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.estadoOperativo = estadoOperativo;
        this.proveedor = proveedor;
    }


    public RepoIngreso() {}
}
