package io.github.monthalcantara.nossobancodigital.controller;

import io.github.monthalcantara.nossobancodigital.controller.interfaces.CadastroClienteControllerSwagger;
import io.github.monthalcantara.nossobancodigital.dto.request.ClienteDTO;
import io.github.monthalcantara.nossobancodigital.dto.request.EnderecoDTO;
import io.github.monthalcantara.nossobancodigital.dto.response.ClienteResponseDTO;
import io.github.monthalcantara.nossobancodigital.dto.response.EnderecoResponseDTO;
import io.github.monthalcantara.nossobancodigital.model.Cliente;
import io.github.monthalcantara.nossobancodigital.model.Endereco;
import io.github.monthalcantara.nossobancodigital.service.interfaces.ClienteService;
import io.github.monthalcantara.nossobancodigital.service.interfaces.DocumentoService;
import io.github.monthalcantara.nossobancodigital.service.interfaces.EnderecoService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.net.URI;
import java.util.function.Function;

@RestController
@RequestMapping("v1/cadastro")
public class CadastroClienteController implements CadastroClienteControllerSwagger {

    @Value("${caminho-arquivos-pasta}")
    String diretorioArquivos;

    private ClienteService clienteService;
    private EnderecoService enderecoService;
    private DocumentoService documentoService;

    public CadastroClienteController(ClienteService clienteService,
                                     EnderecoService enderecoService,
                                     DocumentoService documentoService) {
        this.clienteService = clienteService;
        this.enderecoService = enderecoService;
        this.documentoService = documentoService;
    }

    @PostMapping("/cliente")
    public ResponseEntity salveCliente(@RequestBody @Valid ClienteDTO client) {
        Cliente clienteSalvo = clienteService.salveNovoCliente(client);
        URI location = geradorLocation(clienteSalvo.getId(), "/{id}/endereco");
        return ResponseEntity.created(location).body(clienteSalvo.paraResponse());
    }


    @PostMapping("cliente/{id}/endereco")
    public ResponseEntity salveEndereco(@RequestBody @Valid EnderecoDTO endereco, @PathVariable("id") Long id) {
        Endereco enderecoSalvo = enderecoService.salveNovoEndereco(id, endereco);

        URI location = geradorLocation(id, "/arquivo");
        EnderecoResponseDTO enderecoResponseDTO = new EnderecoResponseDTO(enderecoSalvo);
        return ResponseEntity.created(location).body(enderecoResponseDTO);
    }

    @PostMapping("cliente/{id}/endereco/arquivo")
    public ResponseEntity salveDocumento(@RequestParam("frente") MultipartFile fotoDocumentoFrente,
                                         @RequestParam("verso") MultipartFile fotoDocumentoVerso,
                                         @PathVariable("id") Long id) {
        documentoService.salveArquivosDocumentoCliente(diretorioArquivos, id, fotoDocumentoFrente, fotoDocumentoVerso);
        URI location = geradorLocation(id, "/aceite");
        ClienteResponseDTO clienteResponseDTO = clienteService.busqueClientePeloId(id).paraResponse();
        return ResponseEntity.created(location).body(clienteResponseDTO);

    }

    @PostMapping("cliente/{id}/endereco/arquivo/aceite")
    public ResponseEntity aceiteContrato(@PathVariable("id") Long id, @PathParam("aceite") Boolean aceite) {
        Cliente cliente = clienteService.retorneSeExistirEnderecoParaClienteComId(id);
        enderecoService.retorneSeExistirEnderecoComId(id);
        return ResponseEntity.ok(montaMensagemSaida(cliente.getNome()).apply(aceite));
    }


    private URI geradorLocation(Long id, String proximoPassoCadastro) {
        Cliente clienteAtualizado = clienteService.busqueClientePeloId(id);
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path(proximoPassoCadastro)
                .buildAndExpand(clienteAtualizado.getId())
                .toUri();
    }

    private Function<Boolean, String> montaMensagemSaida(String nomeCliente) {
        String mensagemSucesso = new StringBuilder().append(nomeCliente).append("!!! Iremos criar a sua conta").toString();
        String mensagemRecusa = new StringBuilder().append("É uma pena ").append(nomeCliente).append(" =/ Vamos dar um tempo para você pensar melhor").toString();
        return fn -> fn ? mensagemSucesso : mensagemRecusa;
    }
}