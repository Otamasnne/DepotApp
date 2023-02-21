package domainapp.modules.simple.dom.reportes;

import lombok.Getter;
import lombok.Setter;

public class RepoCliente {

    @Getter
    @Setter
    private Integer codigo;

    @Getter
    @Setter
    private Integer dni;

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
