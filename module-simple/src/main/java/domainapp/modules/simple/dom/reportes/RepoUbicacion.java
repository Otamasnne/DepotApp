package domainapp.modules.simple.dom.reportes;

import lombok.Getter;
import lombok.Setter;

public class RepoUbicacion {

    @Getter @Setter
    private Integer codigo;

    @Getter @Setter
    private String descripcion;

    public RepoUbicacion(Integer codigo, String descripcion){
        this.codigo = codigo;
        this.descripcion = descripcion;

    }

    public RepoUbicacion(){}



}
