package io.github.monthalcantara.nossobancodigital.controller;

import io.github.monthalcantara.nossobancodigital.dto.request.ClienteDTO;
import io.github.monthalcantara.nossobancodigital.dto.response.ClienteResponseDTO;
import io.github.monthalcantara.nossobancodigital.model.Cliente;
import io.github.monthalcantara.nossobancodigital.service.interfaces.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/v1/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;
    @GetMapping
    public Page<Cliente> buscaTodosCliente(Pageable pageable){
        return clienteService.busqueTodosClientes(pageable);
    }
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ClienteResponseDTO updateById(@PathVariable Long id,
                                         @RequestBody @Valid ClienteDTO cliente) {
        return clienteService.atualizeClientePeloId(id, cliente);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> findById(@PathVariable Long id) {

        return ResponseEntity.ok(clienteService.busqueClientePeloId(id));
    }

    @GetMapping("/byName/{name}")
    public ResponseEntity<Page<ClienteResponseDTO>> findByName(@PathVariable String name, @PageableDefault(size = 5) Pageable pageable) {
        return new ResponseEntity<Page<ClienteResponseDTO>>(clienteService.busqueClientePeloNome(name, pageable), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ClienteResponseDTO> save(@RequestBody @Valid ClienteDTO client) {
        return new ResponseEntity<>(clienteService.salve(client), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Integer id) {
        clienteService.deleteClientePeloId(id);
    }
}
