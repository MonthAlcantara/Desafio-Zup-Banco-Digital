package io.github.monthalcantara.nossobancodigital.validation.validators;

import io.github.monthalcantara.nossobancodigital.repository.ClienteRepository;
import io.github.monthalcantara.nossobancodigital.validation.annotations.UnicoCPF;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UnicoCPFValidator implements ConstraintValidator<UnicoCPF, String> {
    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public boolean isValid(String cpf, ConstraintValidatorContext constraintValidatorContext) {
        return !clienteRepository.findByCpf(cpf).isPresent();
    }
}
