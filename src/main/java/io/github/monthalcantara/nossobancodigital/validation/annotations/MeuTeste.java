package io.github.monthalcantara.nossobancodigital.validation.annotations;

import org.hibernate.validator.constraints.CompositionType;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static org.hibernate.validator.constraints.CompositionType.AND;

@Documented
@Target({ ANNOTATION_TYPE })
@Retention(RUNTIME)
public @interface MeuTeste {
    /**
     * The value of this element specifies the boolean operator,
     * namely disjunction (OR), negation of the conjunction (ALL_FALSE),
     * or, the default, simple conjunction (AND).
     *
     * @return the {@code CompositionType} value
     */
    CompositionType value() default AND;

    String message() default "";
}
