package domainapp.modules.simple.dom.cliente;

import lombok.Getter;
import lombok.Setter;
import org.apache.isis.applib.annotation.*;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.apache.isis.applib.services.title.TitleService;

import javax.inject.Inject;

public class Cliente implements Comparable<Cliente>{


    @Title
    @Getter
    @Setter
    @PropertyLayout(fieldSetId = "cliente", sequence = "1")
    private int codigo;

    @Setter
    @Getter
    @Property(editing = Editing.ENABLED)
    @PropertyLayout(fieldSetId = "cliente", sequence = "2")
    private int dni;

    @Setter
    @Getter
    @PropertyLayout(fieldSetId = "cliente", sequence = "3")
    private String nombre;

    @Getter
    @Setter
    @PropertyLayout(fieldSetId = "cliente", sequence = "4")
    private String apellido;

    @Getter
    @Setter
    @PropertyLayout(fieldSetId = "cliente", sequence = "5")
    private int edad;

    public Cliente(String cliente) {
    }

    @Override
    public int compareTo(Cliente cliente) {
        return 0;
    }


    @Inject TitleService titleService;
    @Inject RepositoryService repositoryService;

    @Action(semantics = SemanticsOf.NON_IDEMPOTENT_ARE_YOU_SURE)

    public String borrar(){
        int cliente = this.getCodigo();
        final String title =titleService.titleOf(this);
        repositoryService.removeAndFlush(this);
        return "Borrado";
    }


}