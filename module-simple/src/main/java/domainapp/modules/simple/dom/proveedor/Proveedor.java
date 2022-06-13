package domainapp.modules.simple.dom.proveedor;

import domainapp.modules.simple.dom.articulo.Articulo;
import domainapp.modules.simple.types.articulo.Codigo;
import domainapp.modules.simple.types.articulo.Stock;
import lombok.*;
import org.apache.isis.applib.annotation.*;
import org.apache.isis.applib.jaxb.PersistentEntityAdapter;
import org.apache.isis.applib.services.message.MessageService;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.apache.isis.applib.services.title.TitleService;
import org.apache.isis.persistence.jdo.applib.types.PhoneNumber;

import javax.inject.Inject;
import javax.jdo.annotations.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Comparator;

@PersistenceCapable(
        schema = "depotapp",
        identityType = IdentityType.DATASTORE
)
@Unique(
        name= "Proveedor_codigo_UNQ", members = {"codigo"}
)
@DatastoreIdentity(strategy = IdGeneratorStrategy.IDENTITY, column="id")
@Version(strategy = VersionStrategy.DATE_TIME, column="version")
@DomainObject(logicalTypeName = "depotapp.Proveedor", entityChangePublishing = Publishing.ENABLED)
@DomainObjectLayout
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@XmlJavaTypeAdapter(PersistentEntityAdapter.class)
@ToString(onlyExplicitlyIncluded = true)
public class Proveedor implements Comparable<Proveedor>{

    @Inject
    RepositoryService repositoryService;
    @Inject
    TitleService titleService;
    @Inject
    MessageService messageService;

//PRUEBO SOLO CON CODIGO Y RAZON SOCIAL LUEGO AGREGO RESTO
//   public static Proveedor withName(String codigo, String direccion, String razonSocial, String telefono, String localidad, String email) {
//       val proveedor = new Proveedor();
//       codigo = ("000000" + codigo).substring(codigo.length());
//       proveedor.setCodigo(codigo);
//       proveedor.setDireccion(direccion);
//       proveedor.setRazonSocial(razonSocial);
//       proveedor.setTelefono(telefono);
//       proveedor.setLocalidad(localidad);
//       proveedor.setEmail(email);
//       return proveedor;
//   }

   public static Proveedor withName(String codigo, String razonSocial){
       val proveedor = new Proveedor();
       codigo = ("000000" + codigo).substring(codigo.length());
       proveedor.setCodigo(codigo);
       proveedor.setRazonSocial(razonSocial);
       return proveedor;
   }

   //Probando metodo title en lugar de anotacion (mejor personalizacion?)
    public String title(){
       return getCodigo() + getCodigo();
    }

    // Por que no usar anotacion column?
    @Codigo
    @Getter @Setter @ToString.Include
    @PropertyLayout(fieldSetId = "proveedor", sequence = "1")
    private String codigo;

    @Getter @Setter @ToString.Include
    private String direccion;

    @Getter @Setter @ToString.Include
    private String razonSocial;

    @Getter @Setter @ToString.Include
    @PhoneNumber //Investigar
    private String telefono;

    @Getter @Setter @ToString.Include
    private String localidad;

    //@EmailAddress no funciona
    @Getter @Setter @ToString.Include
    private String email;


    /**
     * PENDIENTE: agregar metodos para actualizar campos
     * Agregar Querys?
     * Definir nombre de la clase repo
     */

    public static final Comparator<Proveedor> comparator = Comparator.comparing(Proveedor::getCodigo);

    @Override
    public int compareTo(final Proveedor other) {
        return comparator.compare(this, other);
    }


}
