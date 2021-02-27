package io.github.monthalcantara.nossobancodigital.validation.annotations;

import org.hibernate.validator.constraints.CompositionType;
import org.hibernate.validator.constraints.ConstraintComposition;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.Constraint;
import javax.validation.OverridesAttribute;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = {})
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@CPF
@CNPJ
@ConstraintComposition(value = CompositionType.OR)
public @interface CPFOuCNPJ {

    @OverridesAttribute.List({
            @OverridesAttribute(constraint = CPF.class),
            @OverridesAttribute(constraint = CNPJ.class)
    })
    String message() default "O valor informado deve ser um CPF ou um CNPJ válido";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
