package io.github.monthalcantara.nossobancodigital.service.interfaces;

import io.github.monthalcantara.nossobancodigital.dto.request.EnderecoDTO;
import io.github.monthalcantara.nossobancodigital.model.Endereco;

public interface EnderecoService {
    Endereco salveNovoEndereco(Long id, EnderecoDTO enderecoDTO);
}
