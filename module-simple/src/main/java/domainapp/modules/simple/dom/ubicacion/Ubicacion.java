package domainapp.modules.simple.dom.ubicacion;


import domainapp.modules.simple.dom.EstadoHabDes;
import domainapp.modules.simple.dom.articulo.Articulo;
import domainapp.modules.simple.dom.proveedor.Proveedor;
import domainapp.modules.simple.types.articulo.CodigoArticulo;
import domainapp.modules.simple.types.articulo.Descripcion;
import domainapp.modules.simple.types.comprobante.CodigoCo;
import lombok.*;
import org.apache.isis.applib.annotation.*;
import org.apache.isis.applib.jaxb.PersistentEntityAdapter;
import org.apache.isis.applib.query.Query;
import org.apache.isis.applib.services.message.MessageService;
import org.apache.isis.applib.services.title.TitleService;

import javax.inject.Inject;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.VersionStrategy;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Comparator;
import java.util.List;

import static org.apache.isis.applib.annotation.SemanticsOf.NON_IDEMPOTENT_ARE_YOU_SURE;

@javax.jdo.annotations.PersistenceCapable(
        schema = "depotapp",
        identityType= IdentityType.DATASTORE)
@javax.jdo.annotations.Unique(
        name = "Ubicacion_codigo_UNQ", members = {"codigo"}
)
@javax.jdo.annotations.Queries({
        @javax.jdo.annotations.Query(
                name = Ubicacion.NAMED_QUERY__BUSCAR_POR_CODIGO_LIKE,
                value = "SELECT " +
                        "FROM domainapp.modules.simple.dom.ubicacion.Ubicacion " +
                        "WHERE codigo.indexOf(:codigo) >= 0"
        ),
        @javax.jdo.annotations.Query(
                name = Ubicacion.NAMED_QUERY__BUSCAR_POR_CODIGO_EXACTO,
                value = "SELECT " +
                        "FROM domainapp.modules.simple.dom.ubicacion.Ubicacion " +
                        "WHERE codigo == :codigo"
        ),
        @javax.jdo.annotations.Query(
                name = Ubicacion.NAMED_QUERY__BUSCAR_HABILITADOS,
                value = "SELECT " +
                        "FROM domainapp.modules.simple.dom.ubicacion.Ubicacion " +
                        "WHERE estado == 'HABILITADO'"
        ),
        @javax.jdo.annotations.Query(
                name = Ubicacion.NAMED_QUERY__BUSCAR_DESHABILITADOS,
                value = "SELECT " +
                        "FROM domainapp.modules.simple.dom.ubicacion.Ubicacion " +
                        "WHERE estado == 'DESHABILITADO'"
        )
})
@javax.jdo.annotations.DatastoreIdentity(strategy= IdGeneratorStrategy.IDENTITY, column="id")
@javax.jdo.annotations.Version(strategy= VersionStrategy.DATE_TIME, column="version")
@DomainObject(logicalTypeName = "depotapp.Ubicacion", entityChangePublishing = Publishing.ENABLED)
@DomainObjectLayout()
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@XmlJavaTypeAdapter(PersistentEntityAdapter.class)
@ToString(onlyExplicitlyIncluded = true)
@javax.persistence.Table(schema = "SIMPLE")
public  class Ubicacion implements Comparable<Ubicacion> {

    static final String NAMED_QUERY__BUSCAR_POR_CODIGO_LIKE = "Ubicacion.buscarPorCodigoLike";
    static final String NAMED_QUERY__BUSCAR_POR_CODIGO_EXACTO = "Ubicacion.buscarPorCodigoExacto";
    public static final String NAMED_QUERY__BUSCAR_HABILITADOS = "Ubicacion.buscarHabilitados";
    static final String NAMED_QUERY__BUSCAR_DESHABILITADOS = "Ubicacion.buscarDeshabilitados";

    @Inject TitleService titleService;
    @Inject MessageService messageService;

    public static Ubicacion creacion(String descripcion) {
        Ubicacion ubicacion = new Ubicacion();
        ubicacion.setDescripcion(descripcion);
        ubicacion.setEstado(EstadoHabDes.HABILITADO);
        return ubicacion;
    }

    @Action(semantics = NON_IDEMPOTENT_ARE_YOU_SURE)
    @ActionLayout(
            position = ActionLayout.Position.PANEL,
            describedAs = "Habilita la ubicación")
    public Ubicacion habilitar() {
        final String title = titleService.titleOf(this);
        messageService.informUser(String.format("'%s' habilitada", title));
        this.setEstado(EstadoHabDes.HABILITADO);
        return this;
    }

    @Action(semantics = NON_IDEMPOTENT_ARE_YOU_SURE)
    @ActionLayout(
            position = ActionLayout.Position.PANEL,
            describedAs = "Deshabilita la ubicación.")
    public Ubicacion deshabilitar() {
        final String title = titleService.titleOf(this);
        messageService.informUser(String.format("'%s' deshabilitada", title));
        this.setEstado(EstadoHabDes.DESHABILITADO);
        return this;
    }

    public boolean hideHabilitar() {
        return this.getEstado()== EstadoHabDes.HABILITADO;
    }
    public boolean hideDeshabilitar() {
        return this.getEstado()== EstadoHabDes.DESHABILITADO;
    }

    public String title() {
        return this.descripcion;
    }

    @CodigoCo
    @Getter
    @Setter
    @ToString.Include
    @PropertyLayout(fieldSetId = "ubicacion", sequence = "1")
    private int codigo;

    @Descripcion
    @Getter
    @Setter
    @ToString.Include @Property(editing = Editing.ENABLED)
    @PropertyLayout(fieldSetId = "ubicacion", sequence = "2")
    private String descripcion;

    @Getter
    @Setter
    @ToString.Include
    @PropertyLayout(fieldSetId = "ubicacion", sequence = "3")
    private EstadoHabDes estado;

    private final static Comparator<Ubicacion> comparator =
            Comparator.comparing(Ubicacion::getCodigo);
    @Override
    public int compareTo(final Ubicacion other) {
        return comparator.compare(this, other);
    }

}
