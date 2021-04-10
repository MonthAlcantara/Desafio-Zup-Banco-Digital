package io.github.monthalcantara.nossobancodigital.controller;

import io.github.monthalcantara.nossobancodigital.controller.interfaces.ClienteControllerSwagger;
import io.github.monthalcantara.nossobancodigital.dto.request.ClienteDTO;
import io.github.monthalcantara.nossobancodigital.dto.response.ClienteResponseDTO;
import io.github.monthalcantara.nossobancodigital.model.Cliente;
import io.github.monthalcantara.nossobancodigital.service.interfaces.ClienteService;
import io.swagger.annotations.Api;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("v1/admin/cliente")
public class ClienteController implements ClienteControllerSwagger {

    private ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping
    public ResponseEntity<Page<ClienteResponseDTO>> busqueTodosClientes(@PageableDefault(size = 5) Pageable pageable) {

        return new ResponseEntity<>(clienteService.busqueTodosClientes(pageable), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> busqueClientePeloId(@PathVariable Long id) {
        Cliente clienteEncontradoPeloId = clienteService.busqueClientePeloId(id);
        return new ResponseEntity(clienteEncontradoPeloId.paraResponse(), HttpStatus.OK);
    }

    @GetMapping("/nome/{nome}")
    public ResponseEntity<Page<ClienteResponseDTO>> busqueClientePeloNome(@PathVariable("nome") String nome, @PageableDefault(size = 5) Pageable pageable) {
        return new ResponseEntity<>(clienteService.busqueClientePeloNome(nome, pageable), HttpStatus.OK);
    }

    @GetMapping("/cnh/{cnh}")
    public ResponseEntity busqueClientePeloCNH(@PathVariable("cnh") String cnh) {
        return new ResponseEntity<>(clienteService.busqueClientePelaCNH(cnh), HttpStatus.OK);
    }

    @GetMapping("/cpf/{cpf}")
    public ResponseEntity busqueClientePeloCPF(@PathVariable("cpf") String cpf) {
        return new ResponseEntity<>(clienteService.busqueClientePeloCPF(cpf), HttpStatus.OK);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity busqueClientePeloEmail(@PathVariable("email") String email) {
        return new ResponseEntity<>(clienteService.busqueClientePeloEmail(email), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity atualizeClientePeloId(@PathVariable Long id, @RequestBody @Valid ClienteDTO cliente) {
        Cliente clienteEncontrado = clienteService.atualizeClientePeloId(id, cliente);
        return new ResponseEntity<>(clienteEncontrado.paraResponse(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteClientePeloId(@PathVariable Long id) {
        clienteService.deleteClientePeloId(id);
        return ResponseEntity.noContent().build();
    }

}

