package domainapp.modules.simple.types;

import domainapp.modules.simple.types.cliente.RazonSocial;
import org.apache.isis.applib.annotation.*;
import org.apache.isis.persistence.jdo.applib.types.PhoneNumber;

import javax.jdo.annotations.Column;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Column(allowsNull = "false")
@Property(
        editing = Editing.ENABLED,
        regexPattern = "[^@]+@[^@]+[.][^@]+",
        regexPatternReplacement =
                "Correo invalido"
)
@Parameter(
        regexPattern = "[^@]+@[^@]+[.][^@]+",
        regexPatternReplacement =
                "Correo invalido")
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@ParameterLayout(named = "Email")
public @interface Email {
}