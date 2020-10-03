package io.github.monthalcantara.nossobancodigital.validation.validators;

import io.github.monthalcantara.nossobancodigital.repository.ClienteRepository;
import io.github.monthalcantara.nossobancodigital.validation.annotations.UnicoCNH;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UnicoCNHValidator implements ConstraintValidator<UnicoCNH, String> {
    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public boolean isValid(String cnh, ConstraintValidatorContext constraintValidatorContext) {
        return !clienteRepository.findByCnh(cnh).isPresent();
    }
}
