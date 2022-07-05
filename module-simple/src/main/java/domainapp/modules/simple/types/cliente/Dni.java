package domainapp.modules.simple.types.cliente;

import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Property;

import javax.jdo.annotations.Column;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Column(length = Dni.MAX_LEN, allowsNull = "false")
@Property(maxLength = Dni.MAX_LEN)
@Parameter(maxLength = Dni.MAX_LEN)
@ParameterLayout(named = "Stock")
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE })
public @interface Dni {
    int MAX_LEN = 8;
}