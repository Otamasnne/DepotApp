package domainapp.modules.simple.types.cliente;

import org.apache.isis.applib.annotation.Parameter;
        import org.apache.isis.applib.annotation.Property;

        import javax.jdo.annotations.Column;
        import java.lang.annotation.ElementType;
        import java.lang.annotation.Target;

@Property(maxLength = Codigo.MAX_LEN)
@Column(length = Codigo.MAX_LEN, allowsNull = "false")
@Parameter(maxLength = Codigo.MAX_LEN)
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})

public @interface Codigo {
    int MAX_LEN =12;


}