package domainapp.modules.simple.types.pedido;

import domainapp.modules.simple.types.articulo.CodigoArticulo;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Property;

import javax.jdo.annotations.Column;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Column(length = CodigoPedido.MAX_LEN, allowsNull = "false")
@Property(maxLength = CodigoPedido.MAX_LEN)
@Parameter(maxLength = CodigoPedido.MAX_LEN)
@ParameterLayout(named = "Codigo")
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface CodigoPedido {
    int MAX_LEN = 6;
}
