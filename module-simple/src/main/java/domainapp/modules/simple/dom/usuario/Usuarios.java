package domainapp.modules.simple.dom.usuario;

import org.apache.isis.applib.annotation.*;
import org.apache.isis.applib.query.Query;
import org.apache.isis.applib.services.message.MessageService;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.apache.isis.persistence.jdo.applib.services.JdoSupportService;

import javax.inject.Inject;

import java.util.List;
import java.util.Optional;

@DomainService(
        nature = NatureOfService.VIEW,
        logicalTypeName = "simple.Usuarios"
)
public class Usuarios {
    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(named = "Usuarios Registrados")
    public List<Usuario> listarUsuario() {
        return repositoryService.allInstances(Usuario.class);
    }

    @ActionLayout(named = "Usuarios por Nombre")
    public List<Usuario> listarUsuarioPorNombre() {
        return repositoryService.allMatches(Query.named(Usuario.class,Usuario.NAMED_QUERY__FIND_BY_NAME_LIKE_USUARIO));
    }

    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(named = "Usuarios por Id")
    public Optional<Usuario> getByUserById(final int id) {
        return repositoryService.uniqueMatch(
                Query.named(Usuario.class, Usuario.NAMED_QUERY__FIND_BY_USER_BY_ID)
                        .withParameter("UsuarioId", id)
        );

    }

    @Programmatic
    public Usuario buscarPorUserName ( final String userName){
        return repositoryService.uniqueMatch(
                        Query.named(Usuario.class, Usuario.NAMED_QUERY__FIND_BY_NAME_LIKE_USER_NAME)
                                .withParameter("userName", userName))
                .orElse(null);
    }

    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(named = "Validar Usuario")
    public Usuario userValidation(final String username, final String password) throws Exception {
        return repositoryService.uniqueMatch(
                        Query.named(Usuario.class, Usuario.NAMED_QUERY__FIND_BY_USER_NAME_PASSWORD)
                                .withParameter("username", username)
                                .withParameter("password", password))
                .orElseThrow(() ->
                        new Exception("Usuario - " + username + " no encontrado"));


    }



    @Inject
    RepositoryService repositoryService;
    JdoSupportService jdoSupportService;
    MessageService messageServic;

}
