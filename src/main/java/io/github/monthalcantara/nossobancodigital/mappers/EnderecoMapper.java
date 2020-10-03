package io.github.monthalcantara.nossobancodigital.mappers;

import io.github.monthalcantara.nossobancodigital.dto.request.EnderecoDTO;
import io.github.monthalcantara.nossobancodigital.dto.response.EnderecoResponseDTO;
import io.github.monthalcantara.nossobancodigital.model.Endereco;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EnderecoMapper {

    Endereco converteParaEndereco(EnderecoDTO enderecoDTO);

    EnderecoResponseDTO converteParaEnderecoResponseDTO (Endereco endereco);

    List<EnderecoResponseDTO> converteParaListaEnderecoResponseDTO(List<Endereco> listaEnderecos);

    List<Endereco> converteParaListaEndereco(List<EnderecoDTO> listaEnderecosDTO);

}
