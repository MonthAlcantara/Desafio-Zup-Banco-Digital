package io.github.monthalcantara.nossobancodigital.validation.annotations;

import io.github.monthalcantara.nossobancodigital.validation.validators.UnicoEmailValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UnicoEmailValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UnicoEmail {

    String message() default "{io.github.monthalcantara.nossobancodigital.validation.annotations.UnicoEmail}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
