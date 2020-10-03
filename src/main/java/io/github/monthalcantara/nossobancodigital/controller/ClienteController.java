package io.github.monthalcantara.nossobancodigital.controller;

import io.github.monthalcantara.nossobancodigital.dto.request.ClienteDTO;
import io.github.monthalcantara.nossobancodigital.dto.response.ClienteResponseDTO;
import io.github.monthalcantara.nossobancodigital.mappers.ClienteMapper;
import io.github.monthalcantara.nossobancodigital.model.Cliente;
import io.github.monthalcantara.nossobancodigital.service.interfaces.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("v1/cliente")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    ClienteMapper clienteMapper;

    @GetMapping
    public Page<ClienteResponseDTO> busqueTodosCliente(@PageableDefault(size = 5) Pageable pageable) {

        return clienteService.busqueTodosClientes(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> busqueClientePeloId(@PathVariable Long id) {
        Cliente clienteEncontrado = clienteService.busqueClientePeloId(id);
        return ResponseEntity.ok(converteParaClienteResponseDTO(clienteEncontrado));
    }

    @GetMapping("/nome/{nome}")
    public ResponseEntity<Page<ClienteResponseDTO>> busqueClientePeloNome(@PathVariable String name, @PageableDefault(size = 5) Pageable pageable) {
        return new ResponseEntity<>(clienteService.busqueClientePeloNome(name, pageable), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ClienteResponseDTO> salveCliente(@RequestBody @Valid ClienteDTO client) {
        Cliente clienteAtualizado = clienteService.salveNovoCliente(client);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(clienteAtualizado.getId())
                .toUri();
       // return new ResponseEntity<>( converteParaClienteResponseDTO(clienteAtualizado), HttpStatus.CREATED).;
    return ResponseEntity.status(CREATED).header(HttpHeaders.LOCATION, String.valueOf(location)).build();
    }

    @PutMapping("/{id}")
    public ClienteResponseDTO atualizeClientePeloId(@PathVariable Long id, @RequestBody @Valid ClienteDTO cliente) {
        Cliente clienteEncontrado = clienteService.atualizeClientePeloId(id, cliente);
        return converteParaClienteResponseDTO(clienteEncontrado);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteClientePeloId(@PathVariable Long id) {
        clienteService.deleteClientePeloId(id);
    }

    private ClienteResponseDTO converteParaClienteResponseDTO(Cliente cliente) {
        return clienteMapper.converteParaClienteResponseDTO(cliente);
    }
}

