package domainapp.modules.simple.dom.reportes;

import lombok.Getter;
import lombok.Setter;

public class RepoCliente {

    @Getter
    @Setter
    private int codigo;

    @Getter
    @Setter
    private int dni;

    @Getter
    @Setter
    private String razonSocial;


    public RepoCliente(int codigo, Integer dni, String razonSocial) {
        this.codigo = codigo;
        this.dni = dni;
        this.razonSocial = razonSocial;
    }

    public RepoCliente(){}

   /* public String getCodigo(){
        return codigo;
    }
    public Integer getDni(){
        return dni;
    }
    public String getRazonSocial(){
        return razonSocial;
    }*/


}
