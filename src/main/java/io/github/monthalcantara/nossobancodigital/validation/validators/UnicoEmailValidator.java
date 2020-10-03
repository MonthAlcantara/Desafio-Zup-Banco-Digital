package io.github.monthalcantara.nossobancodigital.validation.validators;

import io.github.monthalcantara.nossobancodigital.repository.ClienteRepository;
import io.github.monthalcantara.nossobancodigital.validation.annotations.UnicoEmail;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UnicoEmailValidator implements ConstraintValidator<UnicoEmail, String> {
    @Autowired
    private ClienteRepository clienteRepository;
    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        return !clienteRepository.findByEmail(email).isPresent();
    }
}
