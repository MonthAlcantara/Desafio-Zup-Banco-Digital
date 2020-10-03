package io.github.monthalcantara.nossobancodigital.controller;

import io.github.monthalcantara.nossobancodigital.dto.request.ClienteDTO;
import io.github.monthalcantara.nossobancodigital.dto.request.EnderecoDTO;
import io.github.monthalcantara.nossobancodigital.dto.response.ClienteResponseDTO;
import io.github.monthalcantara.nossobancodigital.mappers.ClienteMapper;
import io.github.monthalcantara.nossobancodigital.model.Cliente;
import io.github.monthalcantara.nossobancodigital.model.Endereco;
import io.github.monthalcantara.nossobancodigital.service.interfaces.ClienteService;
import io.github.monthalcantara.nossobancodigital.service.interfaces.EnderecoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

import static org.springframework.http.HttpStatus.CREATED;
@RestController
@RequestMapping("v1/cadastro")
public class CadastroClienteController {
    @Autowired
    private ClienteService clienteService;

    @Autowired
    private EnderecoService enderecoService;

    @Autowired
    ClienteMapper clienteMapper;

    @PostMapping("/cliente")
    public ResponseEntity salveCliente(@RequestBody @Valid ClienteDTO client) {
        Cliente clienteSalvo = clienteService.salveNovoCliente(client);
        URI location = geradorLocation(clienteSalvo.getId(), "/{id}/endereco");
        return ResponseEntity.status(CREATED).header(HttpHeaders.LOCATION, String.valueOf(location)).build();
    }

    @PostMapping("cliente/{id}/endereco")
    public ResponseEntity salveEndereco(@RequestBody @Valid EnderecoDTO endereco, @PathVariable("id") Long id) {
        Endereco enderecoSalvo = enderecoService.salveNovoEndereco(id, endereco);

        URI location = geradorLocation(id, "/arquivo");
        return ResponseEntity.status(CREATED).header(HttpHeaders.LOCATION, String.valueOf(location)).build();
    }

    @PostMapping("cliente/{id}/endereco/arquivo")
    public ResponseEntity salveDocumento(@RequestBody MultipartFile fotoDocumentoFrente, MultipartFile fotoDocumentoVerso, @PathVariable("id") Long id) {
        Cliente clienteAtualizado = clienteService.busqueClientePeloId(id);
        URI location = geradorLocation(id, "/arquivo");
        return ResponseEntity.status(CREATED).header(HttpHeaders.LOCATION, String.valueOf(location)).build();
    }

    private URI geradorLocation(Long id, String proximoPassoCadastro){
        Cliente clienteAtualizado = clienteService.busqueClientePeloId(id);
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path(proximoPassoCadastro)
                .buildAndExpand(clienteAtualizado.getId())
                .toUri();
    }
}
