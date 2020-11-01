package io.github.monthalcantara.nossobancodigital.validation.validators;

import io.github.monthalcantara.nossobancodigital.model.Cliente;
import io.github.monthalcantara.nossobancodigital.repository.ClienteRepository;
import io.github.monthalcantara.nossobancodigital.validation.annotations.UnicoCPF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Map;
import java.util.Optional;

public class UnicoCPFValidator implements ConstraintValidator<UnicoCPF, String> {
    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private HttpServletRequest request;

    @Override
    public boolean isValid(String cpf, ConstraintValidatorContext constraintValidatorContext) {
        Map<String, String> map = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        Optional<Cliente> cliente = clienteRepository.findByCpf(cpf);

        if (map.containsKey("id") && cliente.isPresent()) {
            Long uriId = Long.parseLong(map.get("id"));
            return cliente.get().getId().equals(uriId);
        }
        return !cliente.isPresent();
    }
}
