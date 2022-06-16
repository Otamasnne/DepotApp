package domainapp.modules.simple.types.comprobante;

import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Property;

import javax.jdo.annotations.Column;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Column(length = CodigoCo.MAX_LEN, allowsNull = "false")
@Property(maxLength = CodigoCo.MAX_LEN)
@Parameter(maxLength = CodigoCo.MAX_LEN)
@ParameterLayout(named = "CodigoCo")
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface CodigoCo {
    int MAX_LEN = 6;

}