package domainapp.modules.simple.dom.usuario;


import domainapp.modules.simple.dom.EstadoHabDes;
import domainapp.modules.simple.dom.articulo.Articulo;
import domainapp.modules.simple.types.Email;
import domainapp.modules.simple.types.Telefono;
import lombok.*;
import org.apache.isis.applib.services.message.MessageService;
import org.apache.isis.applib.services.repository.RepositoryService;

import org.apache.isis.applib.annotation.*;
import org.apache.isis.applib.jaxb.PersistentEntityAdapter;

import org.apache.isis.applib.services.title.TitleService;

import javax.inject.Inject;
import javax.jdo.annotations.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import java.util.Comparator;
import java.util.List;

import static org.apache.isis.applib.annotation.SemanticsOf.IDEMPOTENT;
import static org.apache.isis.applib.annotation.SemanticsOf.NON_IDEMPOTENT_ARE_YOU_SURE;

@PersistenceCapable(schema = "depotapp",identityType= IdentityType.DATASTORE)

@Queries({
        @javax.jdo.annotations.Query(

                name = Usuario.NAMED_QUERY__FIND_BY_NAME_LIKE_USUARIO,
                value = "SELECT "
                        + " FROM domainapp.modules.simple.dom.usuario.Usuario "
                        + "ORDER BY nombre ASC"),
        @javax.jdo.annotations.Query(
                name = Usuario.NAMED_QUERY__FIND_BY_NAME_LIKE_USER_NAME,
                value = "SELECT "
                        + " FROM domainapp.modules.simple.dom.usuario.Usuario "
                        + "ORDER BY username ASC"),
        @javax.jdo.annotations.Query(
                name = Usuario.NAMED_QUERY__FIND_BY_USER_NAME_PASSWORD,
                value = "SELECT "
                        + "FROM domainapp.modules.simple.dom.usuario.Usuario "
                        + "WHERE username == :username "
                        + "&& password == :password "
                        + "ORDER BY username ASC"),

        @javax.jdo.annotations.Query(
                name = Usuario.NAMED_QUERY__FIND_BY_USER_BY_ID,
                value = "SELECT "
                        + "FROM domainapp.modules.simple.dom.usuario.Usuario"
                        + "WHERE UsuarioId == :UsuarioId"
                        + "ORDER BY username ASC"),
        @javax.jdo.annotations.Query(
                name = Usuario.NAMED_QUERY__FIND_BY_DESHABILITADO,
                value = "SELECT " +
                        "FROM domainapp.modules.simple.dom.usuario.Usuario " +
                        "WHERE estado == 'DESHABILITADO'"
        ),
        @javax.jdo.annotations.Query(
                name = Usuario.NAMED_QUERY__FIND_BY_HABILITADO,
                value = "SELECT " +
                        "FROM domainapp.modules.simple.dom.usuario.Usuario " +
                        "WHERE estado == 'HABILITADO'"
        )

})

@DatastoreIdentity(strategy=IdGeneratorStrategy.IDENTITY, column="UsuarioId")
@Version(strategy= VersionStrategy.DATE_TIME, column="version")
@DomainObject(logicalTypeName = "simple.Usuario",entityChangePublishing = Publishing.ENABLED,editing= Editing.DISABLED)
@RequiredArgsConstructor
@DomainObjectLayout(bookmarking = BookmarkPolicy.AS_ROOT)
@XmlJavaTypeAdapter(PersistentEntityAdapter.class)
@ToString(onlyExplicitlyIncluded = true)
@Unique(name= "Usuario_username_UNQ", members = {"username"})


public class Usuario {

    public static final String NAMED_QUERY__FIND_BY_NAME_LIKE_USUARIO = "Usuario.findByNombre";
    public static final String NAMED_QUERY__FIND_BY_NAME_LIKE_USER_NAME = "Usuario.findByUserName";
    public static final String NAMED_QUERY__FIND_BY_USER_NAME_PASSWORD ="Usuario.findByUsernamePassword";
    public static final String NAMED_QUERY__FIND_BY_USER_BY_ID ="Usuario.findById";

    public static final String NAMED_QUERY__FIND_BY_HABILITADO = "Usuario.findByHabilitado";
    static final String NAMED_QUERY__FIND_BY_DESHABILITADO = "Usuario.findByDeshabilitado";

    public Usuario(String userName, String nombre, String apellido, String telefono,
                   String email, String password) {
        this.username=userName;
        this.nombre=nombre;
        this.apellido=apellido;
        this.email=email;
        this.telefono=telefono;
        this.password=password;
        setEstado(EstadoHabDes.HABILITADO);

    }

    public String title() {
        return this.username + " - " + this.nombre + " " + this.apellido;
    }

    @Getter
    @Setter
    @Property(commandPublishing = Publishing.ENABLED, executionPublishing = Publishing.ENABLED)
    @PropertyLayout(fieldSetId = "credenciales", sequence = "1")
    private String username;

    @Getter@Setter
    @Property(commandPublishing = Publishing.ENABLED, executionPublishing = Publishing.ENABLED)
    @PropertyLayout(fieldSetId = "datos", sequence = "1")
    private String nombre;

    @Getter@Setter
    @Property(commandPublishing = Publishing.ENABLED, executionPublishing = Publishing.ENABLED)
    @PropertyLayout(fieldSetId = "datos", sequence = "2")
    @Column(allowsNull = "false")
    private String apellido;

    @Getter@Setter
    @Property(commandPublishing = Publishing.ENABLED, executionPublishing = Publishing.ENABLED)
    @PropertyLayout(fieldSetId = "datos", sequence = "3" )
    @Column(allowsNull = "false")
    private String telefono;

    @Getter@Setter
    @Property(commandPublishing = Publishing.ENABLED, executionPublishing = Publishing.ENABLED)
    @PropertyLayout(fieldSetId = "datos", sequence = "4")
    @Column(allowsNull = "true")
    private String email;

    @Getter@Setter
    @Property(commandPublishing = Publishing.ENABLED, executionPublishing = Publishing.ENABLED)
    @PropertyLayout(fieldSetId = "credenciales", sequence = "2", labelPosition=LabelPosition.LEFT, typicalLength=80,cssClass="x-key")
    @Column(allowsNull = "true")
    private String password;

    @Getter
    @Setter
    @ToString.Include
    @Column(allowsNull = "false")
    @PropertyLayout(fieldSetId = "datos", sequence = "5")
    private EstadoHabDes estado;

    public int compareTo(@NotNull Usuario o) {
        return 0;
    }

    private final static Comparator<Usuario> comparator =
            Comparator.comparing(Usuario::getNombre).thenComparing(Usuario:: getNombre);

    @Action(semantics = IDEMPOTENT, commandPublishing = Publishing.ENABLED, executionPublishing = Publishing.ENABLED)
    @ActionLayout( promptStyle =PromptStyle.DIALOG_MODAL ,associateWith = "name", sequence = "1", named = "Editar Usuario")
    public Object UpdateUsuario(String userName, String nombre, String apellido, @Telefono String telefono,
                                @Email String email, String password
    ) {
        setUsername(userName);
        setNombre(nombre);
        setApellido(apellido);
        setEmail(email);
        setTelefono(telefono);
        setPassword(password);
        return this;
    }

    public @NonNull String default0UpdateUsuario() {return getUsername(); }
    public @NonNull String default1UpdateUsuario() {return getNombre(); }
    public @NonNull String default2UpdateUsuario() {return getApellido(); }
    public @NonNull String default3UpdateUsuario() { return getEmail();  }
    public @NonNull String default4UpdateUsuario() { return getTelefono(); }
    public String default5UpdateUsuario() { return getPassword(); }

    @Action(semantics = NON_IDEMPOTENT_ARE_YOU_SURE)
    @ActionLayout(
            position = ActionLayout.Position.PANEL,
            describedAs = "Habilita el usuario")
    public Usuario habilitar() {
        final String title = titleService.titleOf(this);
        messageService.informUser(String.format("'%s' habilitado", title));
        this.setEstado(EstadoHabDes.HABILITADO);
        return this;
    }

    @Action(semantics = NON_IDEMPOTENT_ARE_YOU_SURE)
    @ActionLayout(
            position = ActionLayout.Position.PANEL,
            describedAs = "Deshabilita el usuario.")
    public Usuario deshabilitar() {
        final String title = titleService.titleOf(this);
        messageService.informUser(String.format("'%s' deshabilitado", title));
        this.setEstado(EstadoHabDes.DESHABILITADO);
        return this;
    }

    public boolean hideHabilitar() {
        return this.getEstado()== EstadoHabDes.HABILITADO;
    }

    public boolean hideDeshabilitar() {
        return this.getEstado()== EstadoHabDes.DESHABILITADO;
    }


    @Inject
    RepositoryService repositoryService;

    @Inject
    TitleService titleService;

    @Inject
    MessageService messageService;
    Usuarios usuarios;
}
