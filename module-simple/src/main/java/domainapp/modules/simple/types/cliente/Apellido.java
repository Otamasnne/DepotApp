package domainapp.modules.simple.types.cliente;

import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Property;

import javax.jdo.annotations.Column;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Column(length = Apellido.MAX_LEN, allowsNull = "false")
@Property(maxLength = Apellido.MAX_LEN)
@Parameter(maxLength = Apellido.MAX_LEN)
@ParameterLayout(named = "Apellido")
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface Apellido {
    int MAX_LEN = 50;
}