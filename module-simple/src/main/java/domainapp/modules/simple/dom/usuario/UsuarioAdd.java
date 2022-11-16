package domainapp.modules.simple.dom.usuario;

import org.apache.isis.applib.annotation.*;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.apache.isis.persistence.jdo.applib.services.JdoSupportService;

import static org.apache.isis.applib.annotation.SemanticsOf.IDEMPOTENT;
@DomainService( nature = NatureOfService.REST, logicalTypeName = "simple.UsuarioAdd")
public class UsuarioAdd {
    @javax.inject.Inject
    RepositoryService repositoryService;
    JdoSupportService jdoSupportService;

    @Action(semantics = IDEMPOTENT, commandPublishing = Publishing.ENABLED, executionPublishing = Publishing.ENABLED)
    @ActionLayout(promptStyle = PromptStyle.DIALOG_MODAL,named = "Agregar Usuario")
    public Usuario AddUsuario(String userName, String nombre, String apellido, String telefono,
                              String email, String password
    ) {
        return repositoryService.persist(new Usuario(userName,nombre,apellido,email,telefono,password));
    }


}
