package io.github.monthalcantara.nossobancodigital.validation.validators;

import io.github.monthalcantara.nossobancodigital.dto.request.ClienteDTO;
import io.github.monthalcantara.nossobancodigital.model.Cliente;
import io.github.monthalcantara.nossobancodigital.repository.ClienteRepository;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

@Component
public class ProibeCadastroRepetidoEmailValidator implements Validator {

    private ClienteRepository repository;

    public ProibeCadastroRepetidoEmailValidator(ClienteRepository clienteRepository){
        this.repository = clienteRepository;
    }
    @Override
    public boolean supports(Class<?> clazz) {
        return ClienteDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
    if(errors.hasErrors()){
        return;
    }
    ClienteDTO clienteDTO = (ClienteDTO) target;

    Optional<Cliente> possivelCliente = repository.findByEmail(clienteDTO.getEmail());
    if(possivelCliente.isPresent()){
        errors.rejectValue("email", null, "Já existe um cadastro de cliente com esse Email.");
    }

    }
}
