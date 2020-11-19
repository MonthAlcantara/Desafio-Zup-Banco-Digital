package io.github.monthalcantara.nossobancodigital.mappers;

import io.github.monthalcantara.nossobancodigital.dto.request.ClienteDTO;
import io.github.monthalcantara.nossobancodigital.dto.response.ClienteResponseDTO;
import io.github.monthalcantara.nossobancodigital.model.Cliente;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ClienteMapper {

    //List<ClienteResponseDTO> converteParaListaClienteResponseDTO(List<Cliente> listaClientes);

}
