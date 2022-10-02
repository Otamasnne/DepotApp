package domainapp.modules.simple.dom.ubicacion;


import domainapp.modules.simple.dom.EstadoHabDes;
import domainapp.modules.simple.types.articulo.CodigoArticulo;
import domainapp.modules.simple.types.articulo.Descripcion;
import domainapp.modules.simple.types.articulo.Stock;
import lombok.*;
import org.apache.isis.applib.annotation.*;
import org.apache.isis.applib.jaxb.PersistentEntityAdapter;
import org.apache.isis.applib.services.message.MessageService;
import org.apache.isis.applib.services.title.TitleService;

import javax.inject.Inject;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.VersionStrategy;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Comparator;

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

    static final String NAMED_QUERY__BUSCAR_POR_CODIGO_LIKE = "Articulo.buscarPorCodigoLike";

    @Inject TitleService titleService;
    @Inject MessageService messageService;

    public static Ubicacion creacion(String codigo, String descripcion) {
        Ubicacion ubicacion = new Ubicacion();
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

    @Title
    @CodigoArticulo
    @Getter
    @Setter
    @ToString.Include
    @PropertyLayout(fieldSetId = "ubicacion", sequence = "1")
    private String codigo;

    @Descripcion
    @Getter
    @Setter
    @ToString.Include
    @PropertyLayout(fieldSetId = "ubicacion", sequence = "2")
    private String descripcion;

    @Stock
    @Getter
    @Setter
    @ToString.Include
    @PropertyLayout(fieldSetId = "ubicacion", sequence = "3")
    private Integer stock;

    @Getter
    @Setter
    @ToString.Include
    @PropertyLayout(fieldSetId = "ubicacion", sequence = "4")
    private EstadoHabDes estado;

    private final static Comparator<Ubicacion> comparator =
            Comparator.comparing(Ubicacion::getCodigo);
    @Override
    public int compareTo(final Ubicacion other) {
        return comparator.compare(this, other);
    }

}
