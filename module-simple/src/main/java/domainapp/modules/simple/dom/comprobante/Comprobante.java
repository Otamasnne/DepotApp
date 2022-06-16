package domainapp.modules.simple.dom.comprobante;

import domainapp.modules.simple.dom.articulo.Articulo;
import domainapp.modules.simple.types.comprobante.CodigoCo;
import domainapp.modules.simple.types.comprobante.FechaAlta;
import lombok.*;
import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.annotation.PropertyLayout;
import org.apache.isis.applib.jaxb.PersistentEntityAdapter;
import org.apache.isis.applib.services.message.MessageService;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.apache.isis.applib.services.title.TitleService;

import javax.annotation.OverridingMethodsMustInvokeSuper;
import javax.inject.Inject;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.VersionStrategy;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDateTime;
import java.util.Comparator;

@javax.jdo.annotations.PersistenceCapable(detachable = "true")
@javax.jdo.annotations.Inheritance(strategy= InheritanceStrategy.NEW_TABLE)
@javax.jdo.annotations.DatastoreIdentity(strategy= IdGeneratorStrategy.IDENTITY, column="id")
@javax.jdo.annotations.Version(strategy= VersionStrategy.DATE_TIME, column="version")
@DomainObjectLayout()
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@XmlJavaTypeAdapter(PersistentEntityAdapter.class)
@ToString(onlyExplicitlyIncluded = true)
public abstract class Comprobante implements Comparable<Comprobante>{

        @Inject
        RepositoryService repositoryService;
        @Inject
        TitleService titleService;
        @Inject
        MessageService messageService;
        
        public String title() {
                return getTipoComprobante() + "-" + getCodigoCo();
        }

        @Getter
        @Setter
        @ToString.Include
        @PropertyLayout(fieldSetId = "encabezado", sequence = "1")
        private TipoComprobante tipoComprobante;

        @CodigoCo
        @Getter
        @Setter
        @ToString.Include
        @PropertyLayout(fieldSetId = "encabezado", sequence = "2")
        private String codigoCo;

        @FechaAlta
        @Getter
        @Setter
        @ToString.Include
        @PropertyLayout(fieldSetId = "encabezado", sequence = "3")
        private LocalDateTime fechaAlta;


        private final static Comparator<Comprobante> comparator =
                Comparator.comparing(Comprobante::getCodigoCo);

        @Override
        public int compareTo(final Comprobante other) {
                return comparator.compare(this, other);
        }

}
