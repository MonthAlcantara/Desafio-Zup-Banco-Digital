package io.github.monthalcantara.nossobancodigital.service.interfaces;

import io.github.monthalcantara.nossobancodigital.dto.request.EnderecoDTO;
import io.github.monthalcantara.nossobancodigital.dto.response.EnderecoResponseDTO;
import io.github.monthalcantara.nossobancodigital.model.Endereco;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EnderecoService {
    Endereco salveNovoEndereco(Long id, EnderecoDTO enderecoDTO);

    Page<EnderecoResponseDTO> busqueTodosClientes(Pageable pageable);

    Endereco busqueEnderecoPeloId(Long id);

    Endereco busqueEnderecoPeloCep(String cep);

    Endereco atualizeEnderecoSeExistir(Long id, EnderecoDTO enderecoDTO);

    Endereco retorneSeExistirEnderecoComId(Long id);

}
