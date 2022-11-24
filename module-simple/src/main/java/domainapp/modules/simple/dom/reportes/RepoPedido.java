package domainapp.modules.simple.dom.reportes;

import lombok.Getter;
import lombok.Setter;

public class RepoPedido {

    @Getter @Setter
    private Integer codigo;

    @Getter @Setter
    private String descripcion;


    public RepoPedido (Integer codigo, String descripcion){
        this.codigo = codigo;
        this.descripcion = descripcion;

    }

    public RepoPedido (){}

}
