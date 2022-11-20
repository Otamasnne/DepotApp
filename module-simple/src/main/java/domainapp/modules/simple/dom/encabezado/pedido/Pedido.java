package domainapp.modules.simple.dom.encabezado.pedido;

import domainapp.modules.simple.dom.EstadoOperativo;
import domainapp.modules.simple.dom.cliente.Cliente;
import domainapp.modules.simple.dom.item.itemPedido.ItemPedido;
import domainapp.modules.simple.types.comprobante.CodigoCo;
import lombok.*;
import org.apache.isis.applib.annotation.*;
import org.apache.isis.applib.jaxb.PersistentEntityAdapter;
import org.apache.isis.applib.services.message.MessageService;
import org.apache.isis.applib.services.title.TitleService;
import org.apache.isis.persistence.jdo.applib.services.JdoSupportService;

import javax.inject.Inject;
import javax.jdo.annotations.*;
import javax.persistence.FetchType;
import javax.persistence.Table;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Comparator;
import java.util.List;

import static org.apache.isis.applib.annotation.SemanticsOf.NON_IDEMPOTENT_ARE_YOU_SURE;

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
                        "FROM domainapp.modules.simple.dom.encabezado.pedido.Pedido" +
                        "WHERE codigo.indexOf(:codigo) >= 0"
        ),
        @javax.jdo.annotations.Query(
                name = Pedido.NAMED_QUERY_FIND_BY_CODIGO_EXACT,
                value = "SELECT " +
                        "FROM domainapp.modules.simple.dom.encabezado.pedido.Pedido" +
                        "WHERE codigo == :codigo"
        )
})
public class Pedido implements Comparable<Pedido> {
                                            
    @Inject
    TitleService titleService;
    @Inject
    MessageService messageService;

    @Inject
    JdoSupportService jdoSupportService;

    static final String NAMED_QUERY_FIND_BY_CODIGO_EXACT = "pedido.findByCodigoExact";
    static final String NAMED_QUERY_FIND_BY_CODIGO_LIKE = "pedido.findByCodigoLike";

    @Title
    @CodigoCo
    @Getter@Setter @ToString.Include
    @PropertyLayout(fieldSetId = "pedido", sequence = "1")
    private String codigo;

    @Getter@Setter @ToString.Include
    @PropertyLayout(fieldSetId = "pedido", sequence = "2")
    private EstadoOperativo estadoOperativo;

    @Getter
    @Setter @ToString.Include
    @PropertyLayout(fieldSetId = "pedido", sequence = "3")
    private String descripcion;

    @Getter @Setter @ToString.Include
    @Persistent(mappedBy="pedido")
    @PropertyLayout(hidden = Where.EVERYWHERE)
    List<ItemPedido> items;


    @Getter @Setter
    @PropertyLayout(fieldSetId = "pedido", sequence = "4")
    @Column(allowsNull = "false")
    private Cliente cliente;

    public static Pedido withName(String descripcion, Cliente cliente) {
        val pedido = new Pedido();
        pedido.setDescripcion(descripcion);
        pedido.setEstadoOperativo(EstadoOperativo.MODIFICABLE);
        pedido.setCliente(cliente);
        return pedido;
    }

    @Programmatic
    public void agregarItem(ItemPedido item) {
        this.items.add(item);
        jdoSupportService.refresh(this);
    }


    @Action(semantics = NON_IDEMPOTENT_ARE_YOU_SURE)
    @ActionLayout(
            position = ActionLayout.Position.PANEL,
            describedAs = "Envía el pedido a ser procesado.")
    public Pedido procesar() {
        String nombre = this.getCodigo();
        final String title = titleService.titleOf(this);
        messageService.informUser(String.format("'%s' siendo procesado.", title));
        this.setEstadoOperativo(EstadoOperativo.PROCESANDO);
        return this;
    }

    public boolean hideProcesar() {
        return this.getEstadoOperativo()==EstadoOperativo.PROCESANDO;
    }

    private final static Comparator<Pedido> comparator =
            Comparator.comparing(Pedido::getCodigo);
    @Override
    public int compareTo(final Pedido other) {
        return comparator.compare(this, other);
    }
}
