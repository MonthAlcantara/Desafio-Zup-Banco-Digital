package io.github.monthalcantara.nossobancodigital.service.implementacoes;

import io.github.monthalcantara.nossobancodigital.dto.request.EnderecoDTO;
import io.github.monthalcantara.nossobancodigital.mappers.EnderecoMapper;
import io.github.monthalcantara.nossobancodigital.model.Cliente;
import io.github.monthalcantara.nossobancodigital.model.Endereco;
import io.github.monthalcantara.nossobancodigital.repository.EnderecoRepository;
import io.github.monthalcantara.nossobancodigital.service.interfaces.ClienteService;
import io.github.monthalcantara.nossobancodigital.service.interfaces.EnderecoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EnderecoServiceImpl implements EnderecoService {

    @Autowired
    EnderecoRepository enderecoRepository;

    @Autowired
    ClienteService clienteService;

    @Autowired
    EnderecoMapper enderecoMapper;

    @Override
    public Endereco salveNovoEndereco(Long id, EnderecoDTO enderecoDTO) {

        Cliente cliente = clienteService.busqueClientePeloId(id);

        Endereco endereco = converteParaEndereco(enderecoDTO);
        endereco.setClienteId(id);
        System.out.println(endereco);
        Endereco enderecoSalvo = enderecoRepository.save(endereco);

       clienteService.salveEnderecoCliente(cliente, enderecoSalvo);
        return(converteParaEndereco(enderecoDTO));
    }

    private Endereco converteParaEndereco(EnderecoDTO enderecoDTO) {
        System.out.println(enderecoDTO.toString());
        System.out.println(enderecoMapper.converteParaEndereco(enderecoDTO).toString());
        return enderecoMapper.converteParaEndereco(enderecoDTO);
    }
}
