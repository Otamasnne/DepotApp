package domainapp.modules.simple.dom.pedido;

import domainapp.modules.simple.dom.EstadoOperativo;
import domainapp.modules.simple.types.pedido.CodigoPedido;
import lombok.*;
import org.apache.isis.applib.annotation.*;
import org.apache.isis.applib.jaxb.PersistentEntityAdapter;
import org.apache.isis.applib.services.message.MessageService;
import org.apache.isis.applib.services.title.TitleService;

import javax.inject.Inject;
import javax.jdo.annotations.*;
import javax.persistence.Table;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Comparator;

@PersistenceCapable(
        schema = "depotapp",
        identityType= IdentityType.DATASTORE
)
@Unique(
        name = "Pedido_codigo_UNQ", members = {"codigo"}
)
@DatastoreIdentity(strategy= IdGeneratorStrategy.IDENTITY, column="id")
@Version(strategy= VersionStrategy.DATE_TIME, column="version")
@DomainObject(logicalTypeName = "depotapp.Pedido", entityChangePublishing = Publishing.ENABLED)
@DomainObjectLayout()
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@XmlJavaTypeAdapter(PersistentEntityAdapter.class)
@ToString(onlyExplicitlyIncluded = true)
@Table(schema = "SIMPLE")
@Queries({
        @javax.jdo.annotations.Query(
                name = Pedido.NAMED_QUERY_FIND_BY_CODIGO_LIKE,
                value = "SELECT " +
                        "FROM domainapp.modules.simple.dom.pedido.Pedido" +
                        "WHERE codigo.indexOf(:codigo) >= 0"
        ),
        @javax.jdo.annotations.Query(
                name = Pedido.NAMED_QUERY_FIND_BY_CODIGO_EXACT,
                value = "SELECT " +
                        "FROM domainapp.modules.simple.dom.pedido.Pedido" +
                        "WHERE codigo == :codigo"
        )
})
public class Pedido implements Comparable<Pedido> {
    
    @Inject
    TitleService titleService;
    @Inject
    MessageService messageService;

    static final String NAMED_QUERY_FIND_BY_CODIGO_EXACT = "pedido.findByCodigoExact";
    static final String NAMED_QUERY_FIND_BY_CODIGO_LIKE = "pedido.findByCodigoLike";

    @Title
    @CodigoPedido
    @Getter@Setter @ToString.Include
    @PropertyLayout(fieldSetId = "pedido", sequence = "1")
    private String codigo;

    @Getter@Setter
    @PropertyLayout(fieldSetId = "pedido", sequence = "3")
    private EstadoOperativo estadoOperativo;


    public static Pedido withName(String codigo) {
        val pedido = new Pedido();
        codigo = ("000000" + codigo).substring(codigo.length());
        pedido.setCodigo(codigo);
        pedido.setEstadoOperativo(EstadoOperativo.MODIFICABLE);
        return pedido;
    }


    // ESTADO PREPARADO
    @Action(semantics = SemanticsOf.NON_IDEMPOTENT_ARE_YOU_SURE)
    @ActionLayout(
            position = ActionLayout.Position.PANEL,
            describedAs = "Permite que el pedido este listo"
    )
    public String preparado() {
        String nombre = this.getCodigo();
        final String title = titleService.titleOf(this);
        messageService.informUser(String.format("'$s' preparado.", title));
        this.setEstadoOperativo(EstadoOperativo.PREPARADO);
        return "Se paso el pedido " + nombre + " a Preparado.";
    }

    //ESTADO MODIFICABLE
    @Action(semantics = SemanticsOf.NON_IDEMPOTENT_ARE_YOU_SURE)
    @ActionLayout(
            position = ActionLayout.Position.PANEL,
            describedAs = "Permite que el pedido sea modificado"
    )
    public String modificable() {
        String nombre = this.getCodigo();
        final String title = titleService.titleOf(this);
        messageService.informUser(String.format("'$s' modificable.", title));
        this.setEstadoOperativo(EstadoOperativo.MODIFICABLE);
        return "Se paso el pedido " + nombre + " a Modificable";
    }

    // Esconde la acción dependiendo del estado actual.
    public boolean hidePreparado() {
        return this.getEstadoOperativo()==EstadoOperativo.PREPARADO;
    }

    public boolean hideModificable() {
        return this.getEstadoOperativo()==EstadoOperativo.MODIFICABLE;
    }

    private final static Comparator<Pedido> comparator =
            Comparator.comparing(Pedido::getCodigo);
    @Override
    public int compareTo(final Pedido other) {
        return comparator.compare(this, other);
    }
}
