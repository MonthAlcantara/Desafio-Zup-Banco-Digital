package io.github.monthalcantara.nossobancodigital.validation.validators;

import io.github.monthalcantara.nossobancodigital.model.Cliente;
import io.github.monthalcantara.nossobancodigital.repository.ClienteRepository;
import io.github.monthalcantara.nossobancodigital.validation.annotations.ValorUnico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.servlet.HandlerMapping;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ValorUnicoValidator implements ConstraintValidator<ValorUnico, Object> {

    private String fieldName;
    private Class<?> domainClass;

    @PersistenceContext
    private EntityManager manager;

    public ValorUnicoValidator(EntityManager manager) {
        this.manager = manager;
    }

    @Override
    public void initialize(ValorUnico constraintAnnotation) {
        this.fieldName = constraintAnnotation.fieldName();
        this.domainClass = constraintAnnotation.domainClass();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        Query query = manager.createQuery("Select 1 from " + domainClass.getSimpleName() + " where " + fieldName + " =:value");
        query.setParameter("value", value);
        List<?> list = query.getResultList();
        Assert.state(list.size() < 1, "Foram encontrados mais de um(a) " + domainClass.getSimpleName() + " com o mesmo '" + fieldName + "'" + value);
        return list.isEmpty();
    }
}
