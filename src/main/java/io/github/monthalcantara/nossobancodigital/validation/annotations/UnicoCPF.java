package io.github.monthalcantara.nossobancodigital.validation.annotations;

import io.github.monthalcantara.nossobancodigital.validation.validators.UnicoCPFValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UnicoCPFValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UnicoCPF {

    String message() default "{io.github.monthalcantara.nossobancodigital.validation.annotations.UnicoCPF}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
