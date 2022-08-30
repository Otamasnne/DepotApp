package domainapp.modules.simple.dom.ajuste;

import domainapp.modules.simple.dom.EstadoOperativo;
import domainapp.modules.simple.dom.cliente.Cliente;
import domainapp.modules.simple.types.comprobante.CodigoCo;
import domainapp.modules.simple.types.comprobante.FechaAlta;
import lombok.*;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.annotation.PropertyLayout;
import org.apache.isis.applib.annotation.Publishing;
import org.apache.isis.applib.jaxb.PersistentEntityAdapter;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.VersionStrategy;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDateTime;
import java.util.Comparator;

@javax.jdo.annotations.PersistenceCapable(
        schema = "depotapp",
        identityType= IdentityType.DATASTORE)
@javax.jdo.annotations.Unique(
        name = "Ajuste_codigoCo_UNQ", members = {"codigoCo"}
)
@javax.jdo.annotations.Queries({
        @javax.jdo.annotations.Query(
                name = Ajuste.NAMED_QUERY__FIND_BY_CODIGO_LIKE,
                value = "SELECT " +
                        "FROM domainapp.modules.simple.dom.ajuste.Ajuste " +
                        "WHERE codigoCo.indexOf(:codigoCo) >= 0"
        ),
        @javax.jdo.annotations.Query(
                name = Ajuste.NAMED_QUERY__FIND_BY_CODIGO_EXACT,
                value = "SELECT " +
                        "FROM domainapp.modules.simple.dom.ajuste.Ajuste " +
                        "WHERE codigoCo == :codigoCo"
        )
})
@javax.jdo.annotations.DatastoreIdentity(strategy= IdGeneratorStrategy.IDENTITY, column="id")
@javax.jdo.annotations.Version(strategy= VersionStrategy.DATE_TIME, column="version")
@DomainObject(logicalTypeName = "depotapp.Ajuste", entityChangePublishing = Publishing.ENABLED)
@DomainObjectLayout()
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@XmlJavaTypeAdapter(PersistentEntityAdapter.class)
@ToString(onlyExplicitlyIncluded = true)
public class Ajuste implements Comparable<Ajuste>{

    static final String NAMED_QUERY__FIND_BY_CODIGO_LIKE = "Ajuste.findByCodigoLike";
    static final String NAMED_QUERY__FIND_BY_CODIGO_EXACT = "Ajuste.findByCodigoExact";

    public String title() {
        return getTipoAjuste() + "-" + getCodigoCo();
    }

    public static Ajuste creacion(TipoAjuste tipoAjuste, Cliente cliente) {
        val ajuste = new Ajuste();
        ajuste.setTipoAjuste(tipoAjuste);
        ajuste.setFechaAlta(LocalDateTime.now());
        ajuste.setCliente(cliente);
        return ajuste;
    }

    @Getter
    @Setter
    @ToString.Include
    @PropertyLayout(fieldSetId = "encabezado", sequence = "1")
    private TipoAjuste tipoAjuste;

    @CodigoCo
    @Getter
    @Setter
    @ToString.Include
    @PropertyLayout(fieldSetId = "encabezado", sequence = "2")
    private int codigoCo;

    @FechaAlta
    @Getter
    @Setter
    @ToString.Include
    @PropertyLayout(fieldSetId = "encabezado", sequence = "3")
    private LocalDateTime fechaAlta;

    @Getter
    @Setter
    @ToString.Include
    @PropertyLayout(fieldSetId = "encabezado", sequence = "4")
    private Cliente cliente;

    @Getter
    @Setter
    @ToString.Include
    @PropertyLayout(fieldSetId = "encabezado", sequence = "5")
    private EstadoOperativo estadoOperativo;

    private final static Comparator<Ajuste> comparator =
            Comparator.comparing(Ajuste::getCodigoCo);

    @Override
    public int compareTo(final Ajuste other) {
        return comparator.compare(this, other);
    }

}