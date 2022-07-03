package domainapp.modules.simple.types.articulo;

import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Property;

import javax.jdo.annotations.Column;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Column(length = CodigoArticulo.MAX_LEN, allowsNull = "false")
@Property(maxLength = CodigoArticulo.MAX_LEN)
@Parameter(maxLength = CodigoArticulo.MAX_LEN)
@ParameterLayout(named = "Codigo")
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface CodigoArticulo {
    int MAX_LEN = 6;

}
