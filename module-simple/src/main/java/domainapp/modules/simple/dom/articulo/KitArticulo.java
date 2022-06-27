package domainapp.modules.simple.dom.articulo;

import domainapp.modules.simple.types.articulo.Codigo;
import domainapp.modules.simple.types.articulo.Descripcion;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.val;
import org.apache.isis.applib.annotation.*;
import org.apache.isis.applib.services.message.MessageService;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.apache.isis.applib.services.title.TitleService;

import javax.inject.Inject;
import java.util.ArrayList;

public class KitArticulo extends ItemComponent {

    @Inject
    RepositoryService repositoryService;
    @Inject
    TitleService titleService;
    @Inject
    MessageService messageService;
    private ArrayList<ItemComponent> articulos = new ArrayList<>();

    @Title
    @Codigo
    @Getter
    @Setter
    @ToString.Include
    @PropertyLayout(fieldSetId = "KitArticulo", sequence = "1")
    private String codigo;

    @Descripcion
    @Getter
    @Setter
    @ToString.Include
    @PropertyLayout(fieldSetId = "KitArticulo", sequence = "2")
    private String descripcion;

//    public KitArticulo(ArrayList<ItemComponent> articulos, String codigo, String descripcion) {
//        this.articulos = articulos;
//        this.codigo = codigo;
//        this.descripcion = descripcion;
//    }

    public static KitArticulo withName(String codigo, String descripcion) {
        val kitArticulo = new KitArticulo();
        codigo = ("000000" + codigo).substring(codigo.length());
        kitArticulo.setCodigo(codigo);
        kitArticulo.setDescripcion(descripcion);

        return kitArticulo;
    }

    @Action(semantics = SemanticsOf.NON_IDEMPOTENT)
    @ActionLayout(promptStyle = PromptStyle.DIALOG_SIDEBAR)
    @Override
    public void add(ItemComponent itemComponent) {

        articulos.add(itemComponent);
    }

    @Override
    public void remove(ItemComponent itemComponent) {
        articulos.remove(itemComponent);
    }
}
