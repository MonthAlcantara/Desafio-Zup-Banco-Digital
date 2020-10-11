package io.github.monthalcantara.nossobancodigital.validation.annotations;

import io.github.monthalcantara.nossobancodigital.validation.validators.UnicoCNHValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UnicoCNHValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UnicoCNH {

    String message() default "{io.github.monthalcantara.nossobancodigital.validation.annotations.UnicoCNH}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
