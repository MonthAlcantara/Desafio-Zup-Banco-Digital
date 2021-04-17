package io.github.monthalcantara.nossobancodigital.validation.validators;

import io.github.monthalcantara.nossobancodigital.validation.annotations.MaiorIdade;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.Period;

public class MaiorIdadeValidator implements ConstraintValidator<MaiorIdade, LocalDate> {

    private int maiorQue;

    @Override
    public void initialize(MaiorIdade idade) {
        this.maiorQue = idade.maiorQue();
    }

    @Override
    public boolean isValid(LocalDate dataDeNascimento, ConstraintValidatorContext constraintValidatorContext) {
        return Period.between(dataDeNascimento, LocalDate.now()).toTotalMonths() >= 216;
    }
}
