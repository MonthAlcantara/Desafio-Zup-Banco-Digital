package io.github.monthalcantara.nossobancodigital.controller;

import io.github.monthalcantara.nossobancodigital.dto.request.ClienteDTO;
import io.github.monthalcantara.nossobancodigital.dto.response.ClienteResponseDTO;
import io.github.monthalcantara.nossobancodigital.mappers.ClienteMapper;
import io.github.monthalcantara.nossobancodigital.model.Cliente;
import io.github.monthalcantara.nossobancodigital.service.interfaces.ClienteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import java.net.URI;

import static org.springframework.http.HttpStatus.CREATED;

@Api(value = "Endpoint de controle de clientes", description = "Controle interno dos dados do cliente", tags = {"Controle de Clientes"})
@RestController
@RequestMapping("v1/admin/cliente")
public class ClienteController {

    @Autowired
    ClienteService clienteService;

    @Autowired
    ClienteMapper clienteMapper;

    @GetMapping
    @ApiOperation(value = "Busca todos os clientes cadastrados na base de dados" )
    @ApiResponses(value = {@ApiResponse(code = 404, message = "Clientes não localizados"),
            @ApiResponse(code = 403, message = "Você não possui permissão para visualizar este recurso"),
            @ApiResponse(code = 401, message = "Você não possui credenciais de autenticação válidas"),
            @ApiResponse(code = 200, message = "Clientes localizados")})
    public Page<ClienteResponseDTO> busqueTodosClientes(@PageableDefault(size = 5) Pageable pageable) {

        return clienteService.busqueTodosClientes(pageable);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Busca o Cliente especificado pelo Id informado" )
    @ApiResponses(value = {@ApiResponse(code = 404, message = "Cliente não localizado"),
            @ApiResponse(code = 403, message = "Você não possui permissão para visualizar este recurso"),
            @ApiResponse(code = 401, message = "Você não possui credenciais de autenticação válidas"),
            @ApiResponse(code = 200, message = "Cliente localizado")})
    public ResponseEntity<ClienteResponseDTO> busqueClientePeloId(@PathVariable Long id) {
        Cliente clienteEncontradoPeloId = clienteService.busqueClientePeloId(id);
        return ResponseEntity.ok(converteParaClienteResponseDTO(clienteEncontradoPeloId));
    }

    @GetMapping("/nome/{nome}")
    @ApiOperation(value = "Busca o Cliente especificado pelo Nome informado" )
    @ApiResponses(value = {@ApiResponse(code = 404, message = "Clientes não localizados"),
            @ApiResponse(code = 403, message = "Você não possui permissão para visualizar este recurso"),
            @ApiResponse(code = 401, message = "Você não possui credenciais de autenticação válidas"),
            @ApiResponse(code = 200, message = "Clientes localizados")})
    public ResponseEntity<Page<ClienteResponseDTO>> busqueClientePeloNome(@PathVariable String nome, @PageableDefault(size = 5) Pageable pageable) {
        return new ResponseEntity<>(clienteService.busqueClientePeloNome(nome, pageable), HttpStatus.OK);
    }

    @GetMapping("/cnh/{cnh}")
    @ApiOperation(value = "Busca o Cliente especificado pela CNH informada" )
    @ApiResponses(value = {@ApiResponse(code = 404, message = "Cliente não localizado"),
            @ApiResponse(code = 403, message = "Você não possui permissão para visualizar este recurso"),
            @ApiResponse(code = 401, message = "Você não possui credenciais de autenticação válidas"),
            @ApiResponse(code = 200, message = "Cliente localizado")})
    public ResponseEntity busqueClientePeloCNH(@PathVariable String cnh) {
        return new ResponseEntity<>(clienteService.busqueClientePelaCNH(cnh), HttpStatus.OK);
    }

    @GetMapping("/cpf/{cpf}")
    @ApiOperation(value = "Busca o Cliente especificado pelo CPF informado" )
    @ApiResponses(value = {@ApiResponse(code = 404, message = "Cliente não localizado"),
            @ApiResponse(code = 403, message = "Você não possui permissão para visualizar este recurso"),
            @ApiResponse(code = 401, message = "Você não possui credenciais de autenticação válidas"),
            @ApiResponse(code = 200, message = "Cliente localizado")})
    public ResponseEntity busqueClientePeloCPF(@PathVariable String cpf) {
        return new ResponseEntity<>(clienteService.busqueClientePeloCPF(cpf), HttpStatus.OK);
    }

    @GetMapping("/email/{email}")
    @ApiOperation(value = "Busca o Cliente especificado pelo Email informado" )
    @ApiResponses(value = {@ApiResponse(code = 404, message = "Cliente não localizado"),
            @ApiResponse(code = 403, message = "Você não possui permissão para visualizar este recurso"),
            @ApiResponse(code = 401, message = "Você não possui credenciais de autenticação válidas"),
            @ApiResponse(code = 200, message = "Cliente localizado")})
    public ResponseEntity busqueClientePeloEmail(@PathVariable String email) {
        return new ResponseEntity<>(clienteService.busqueClientePeloEmail(email), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Atualiza o Cliente, se existir, especificado pelo Id informado" )
    @ApiResponses(value = {@ApiResponse(code = 404, message = "Cliente não localizado"),
            @ApiResponse(code = 403, message = "Você não possui permissão para visualizar este recurso"),
            @ApiResponse(code = 401, message = "Você não possui credenciais de autenticação válidas"),
            @ApiResponse(code = 200, message = "Cliente atualizado com sucesso")})
    public ClienteResponseDTO atualizeClientePeloId(@PathVariable Long id, @RequestBody @Valid ClienteDTO cliente) {
        Cliente clienteEncontrado = clienteService.atualizeClientePeloId(id, cliente);
        return converteParaClienteResponseDTO(clienteEncontrado);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Deleta o Cliente, se existir, especificado pelo Id informado" )
    @ApiResponses(value = {@ApiResponse(code = 404, message = "Usuário não localizado"),
            @ApiResponse(code = 403, message = "Você não possui permissão para visualizar este recurso"),
            @ApiResponse(code = 401, message = "Você não possui credenciais de autenticação válidas")})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteClientePeloId(@PathVariable Long id) {
        clienteService.deleteClientePeloId(id);
    }

    private ClienteResponseDTO converteParaClienteResponseDTO(Cliente cliente) {
        return clienteMapper.converteParaClienteResponseDTO(cliente);
    }
}

