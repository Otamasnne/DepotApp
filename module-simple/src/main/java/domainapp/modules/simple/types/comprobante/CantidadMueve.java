package domainapp.modules.simple.types.comprobante;

import org.apache.isis.applib.annotation.ParameterLayout;

import javax.jdo.annotations.Column;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Column(allowsNull = "false")
@ParameterLayout(named = "CantidadMueve")
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Column(allowsNull = "false")
public @interface CantidadMueve {
}
