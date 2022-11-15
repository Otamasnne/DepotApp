package domainapp.modules.simple.dom.reportes;

import lombok.Getter;
import lombok.Setter;

public class RepoCliente {

    @Getter @Setter
    private String codigo;

    @Getter @Setter
    private int dni;

    @Getter @Setter
    private String razonSocial;


    public RepoCliente(String codigo, int dni, String razonSocial){
        this.codigo = codigo;
        this.dni = dni;
        this.razonSocial = razonSocial;


    }

    public RepoCliente() {}
}
