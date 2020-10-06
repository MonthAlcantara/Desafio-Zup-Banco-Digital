package io.github.monthalcantara.nossobancodigital.controller;

import io.github.monthalcantara.nossobancodigital.dto.request.ClienteDTO;
import io.github.monthalcantara.nossobancodigital.dto.request.EnderecoDTO;
import io.github.monthalcantara.nossobancodigital.dto.response.ClienteResponseDTO;
import io.github.monthalcantara.nossobancodigital.dto.response.EnderecoResponseDTO;
import io.github.monthalcantara.nossobancodigital.mappers.ClienteMapper;
import io.github.monthalcantara.nossobancodigital.mappers.EnderecoMapper;
import io.github.monthalcantara.nossobancodigital.model.Cliente;
import io.github.monthalcantara.nossobancodigital.model.Endereco;
import io.github.monthalcantara.nossobancodigital.service.implementacoes.DocumentoServiceImpl;
import io.github.monthalcantara.nossobancodigital.service.interfaces.ClienteService;
import io.github.monthalcantara.nossobancodigital.service.interfaces.EnderecoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.net.URI;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("v1/cadastro")
public class CadastroClienteController {

    @Value("${caminho-arquivos-pasta}")
    String diretorioArquivos;

    @Autowired
    ClienteService clienteService;

    @Autowired
    EnderecoService enderecoService;

    @Autowired
    EnderecoMapper enderecoMapper;

    @Autowired
    ClienteMapper clienteMapper;

    @Autowired
    DocumentoServiceImpl documentoService;

    @PostMapping("/cliente")
    public ResponseEntity salveCliente(@RequestBody @Valid ClienteDTO client) {
        Cliente clienteSalvo = clienteService.salveNovoCliente(client);
        URI location = geradorLocation(clienteSalvo.getId(), "/{id}/endereco");
        return ResponseEntity.status(CREATED).header(HttpHeaders.LOCATION, String.valueOf(location)).body(clienteMapper.converteParaClienteResponseDTO(clienteSalvo));
    }

    @PostMapping("cliente/{id}/endereco")
    public ResponseEntity salveEndereco(@RequestBody @Valid EnderecoDTO endereco, @PathVariable("id") Long id) {
        Endereco enderecoSalvo = enderecoService.salveNovoEndereco(id, endereco);

        URI location = geradorLocation(id, "/arquivo");
        EnderecoResponseDTO enderecoResponseDTO = enderecoMapper.converteParaEnderecoResponseDTO(enderecoSalvo);
        return ResponseEntity.status(CREATED).header(HttpHeaders.LOCATION, String.valueOf(location)).body(enderecoResponseDTO);
    }

    @PostMapping("cliente/{id}/endereco/arquivo")
    public ResponseEntity salveDocumento(@RequestParam("frente") MultipartFile fotoDocumentoFrente,
                                         @RequestParam("verso") MultipartFile fotoDocumentoVerso,
                                         @PathVariable("id") Long id) {
        documentoService.salveArquivosDocumentoCliente(diretorioArquivos, id, fotoDocumentoFrente, fotoDocumentoVerso);
        URI location = geradorLocation(id, "/aceite");
        ClienteResponseDTO clienteResponseDTO = clienteMapper.converteParaClienteResponseDTO(clienteService.busqueClientePeloId(id));
        return ResponseEntity.status(CREATED).header(HttpHeaders.LOCATION, String.valueOf(location)).body(clienteResponseDTO);

    }

    @PostMapping("cliente/{id}/endereco/arquivo/aceite")
    public String aceiteContrato(@PathVariable("id") Long id, @PathParam("aceite") Boolean aceite) {
        Cliente cliente = clienteService.retorneSeExistirEnderecoParaClienteComId(id);
        enderecoService.retorneSeExistirEnderecoComId(id);
        return (aceite ? "Que ótima notícia " + cliente.getNome() + "!!! Iremos criar a sua conta" : "É uma pena " + cliente.getNome() + " =/ Vamos dar um tempo para você pensar melhor");
    }


    private URI geradorLocation(Long id, String proximoPassoCadastro) {
        Cliente clienteAtualizado = clienteService.busqueClientePeloId(id);
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path(proximoPassoCadastro)
                .buildAndExpand(clienteAtualizado.getId())
                .toUri();
    }
}