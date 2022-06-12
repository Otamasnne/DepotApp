package domainapp.modules.simple.types.articulo;

import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Property;

import javax.jdo.annotations.Column;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Column(length = Stock.MAX_LEN, allowsNull = "false")
@Property(maxLength = Stock.MAX_LEN)
@Parameter(maxLength = Stock.MAX_LEN)
@ParameterLayout(named = "Stock")
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface Stock {
    int MAX_LEN = 6;
}
