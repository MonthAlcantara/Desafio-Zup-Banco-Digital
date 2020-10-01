package io.github.monthalcantara.nossobancodigital.mappers;

import io.github.monthalcantara.nossobancodigital.dto.request.ClienteDTO;
import io.github.monthalcantara.nossobancodigital.dto.response.ClienteResponseDTO;
import io.github.monthalcantara.nossobancodigital.model.Cliente;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClienteMapper {

    Cliente converteParaCliente(ClienteDTO clienteDTO);
    //testar usando mesmo nome de método para esses dois
    Cliente converteResponseDTOParaCliente(ClienteResponseDTO clienteResponseDTO);

    ClienteDTO converteParaClienteDTO (Cliente cliente);

    ClienteResponseDTO converteParaClienteResponseDTO (Cliente cliente);
}
