package io.github.monthalcantara.nossobancodigital.controller.interfaces;

import io.github.monthalcantara.nossobancodigital.dto.request.ClienteDTO;
import io.github.monthalcantara.nossobancodigital.dto.response.ClienteResponseDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.Valid;

@Api(value = "Endpoint de controle de clientes", description = "Controle interno dos dados do cliente", tags = {"Controle de Clientes"})

public interface ClienteControllerSwagger {

    @ApiOperation(value = "Busca todos os clientes cadastrados na base de dados")
    @ApiResponses(value = {@ApiResponse(code = 404, message = "Clientes não localizados"),
            @ApiResponse(code = 403, message = "Você não possui permissão para visualizar este recurso"),
            @ApiResponse(code = 401, message = "Você não possui credenciais de autenticação válidas"),
            @ApiResponse(code = 200, message = "Clientes localizados")})
    public ResponseEntity<Page<ClienteResponseDTO>> busqueTodosClientes(@PageableDefault(size = 5) Pageable pageable);

    @ApiOperation(value = "Busca o Cliente especificado pelo Id informado")
    @ApiResponses(value = {@ApiResponse(code = 404, message = "Cliente não localizado"),
            @ApiResponse(code = 403, message = "Você não possui permissão para visualizar este recurso"),
            @ApiResponse(code = 401, message = "Você não possui credenciais de autenticação válidas"),
            @ApiResponse(code = 200, message = "Cliente localizado")})
    public ResponseEntity<ClienteResponseDTO> busqueClientePeloId(@PathVariable Long id);

    @ApiOperation(value = "Busca o Cliente especificado pelo Nome informado")
    @ApiResponses(value = {@ApiResponse(code = 404, message = "Clientes não localizados"),
            @ApiResponse(code = 403, message = "Você não possui permissão para visualizar este recurso"),
            @ApiResponse(code = 401, message = "Você não possui credenciais de autenticação válidas"),
            @ApiResponse(code = 200, message = "Clientes localizados")})
    public ResponseEntity<Page<ClienteResponseDTO>> busqueClientePeloNome(@PathVariable("nome") String nome, @PageableDefault(size = 5) Pageable pageable);

    @ApiOperation(value = "Busca o Cliente especificado pela CNH informada")
    @ApiResponses(value = {@ApiResponse(code = 404, message = "Cliente não localizado"),
            @ApiResponse(code = 403, message = "Você não possui permissão para visualizar este recurso"),
            @ApiResponse(code = 401, message = "Você não possui credenciais de autenticação válidas"),
            @ApiResponse(code = 200, message = "Cliente localizado")})
    public ResponseEntity busqueClientePeloCNH(@PathVariable("cnh") String cnh);

    @ApiOperation(value = "Busca o Cliente especificado pelo CPF informado")
    @ApiResponses(value = {@ApiResponse(code = 404, message = "Cliente não localizado"),
            @ApiResponse(code = 403, message = "Você não possui permissão para visualizar este recurso"),
            @ApiResponse(code = 401, message = "Você não possui credenciais de autenticação válidas"),
            @ApiResponse(code = 200, message = "Cliente localizado")})
    public ResponseEntity busqueClientePeloCPF(@PathVariable("cpf") String cpf);

    @ApiOperation(value = "Busca o Cliente especificado pelo Email informado")
    @ApiResponses(value = {@ApiResponse(code = 404, message = "Cliente não localizado"),
            @ApiResponse(code = 403, message = "Você não possui permissão para visualizar este recurso"),
            @ApiResponse(code = 401, message = "Você não possui credenciais de autenticação válidas"),
            @ApiResponse(code = 200, message = "Cliente localizado")})
    public ResponseEntity busqueClientePeloEmail(@PathVariable("email") String email);

    @ApiOperation(value = "Atualiza o Cliente, se existir, especificado pelo Id informado")
    @ApiResponses(value = {@ApiResponse(code = 404, message = "Cliente não localizado"),
            @ApiResponse(code = 403, message = "Você não possui permissão para visualizar este recurso"),
            @ApiResponse(code = 401, message = "Você não possui credenciais de autenticação válidas"),
            @ApiResponse(code = 200, message = "Cliente atualizado com sucesso")})
    public ResponseEntity atualizeClientePeloId(@PathVariable Long id, @RequestBody @Valid ClienteDTO cliente);

    @ApiOperation(value = "Deleta o Cliente, se existir, especificado pelo Id informado")
    @ApiResponses(value = {@ApiResponse(code = 404, message = "Usuário não localizado"),
            @ApiResponse(code = 403, message = "Você não possui permissão para visualizar este recurso"),
            @ApiResponse(code = 401, message = "Você não possui credenciais de autenticação válidas")})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity deleteClientePeloId(@PathVariable Long id);

}

