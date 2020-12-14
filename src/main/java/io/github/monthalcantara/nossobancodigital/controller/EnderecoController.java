package io.github.monthalcantara.nossobancodigital.controller;

import io.github.monthalcantara.nossobancodigital.dto.request.EnderecoDTO;
import io.github.monthalcantara.nossobancodigital.dto.response.EnderecoResponseDTO;
import io.github.monthalcantara.nossobancodigital.model.Endereco;
import io.github.monthalcantara.nossobancodigital.service.interfaces.EnderecoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(value = "Endpoint de controle de endereço dos clientes", description = "Controle interno de endereço dos clientes", tags = {"Controle de Endereços"})
@RestController
@RequestMapping("v1/admin/endereco")
public class EnderecoController {

    EnderecoService enderecoService;

    public EnderecoController(EnderecoService enderecoService) {
        this.enderecoService = enderecoService;
    }

    @GetMapping
    @ApiOperation(value = "Busca todos os Endereços cadastrados na base de dados")
    @ApiResponses(value = {@ApiResponse(code = 404, message = "Endereços não localizados"),
            @ApiResponse(code = 403, message = "Você não possui permissão para visualizar este recurso"),
            @ApiResponse(code = 401, message = "Você não possui credenciais de autenticação válidas"),
            @ApiResponse(code = 200, message = "Endereços localizados")})
    public Page<EnderecoResponseDTO> busqueTodosEnderecos(@PageableDefault(size = 5) Pageable pageable) {
        return enderecoService.busqueTodosClientes(pageable);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Busca o Endereço especificado pelo Id informado")
    @ApiResponses(value = {@ApiResponse(code = 404, message = "Endereço não localizado"),
            @ApiResponse(code = 403, message = "Você não possui permissão para visualizar este recurso"),
            @ApiResponse(code = 401, message = "Você não possui credenciais de autenticação válidas"),
            @ApiResponse(code = 200, message = "Endereço localizado")})
    public ResponseEntity<EnderecoResponseDTO> busqueEnderecoPeloId(@PathVariable Long id) {
        Endereco enderecoEncontradoPeloId = enderecoService.busqueEnderecoPeloId(id);
        return ResponseEntity.ok(enderecoEncontradoPeloId.converteParaResponse());
    }

    @GetMapping("/cep/{cep}")
    @ApiOperation(value = "Busca o Endereço especificado pelo CEP informado")
    @ApiResponses(value = {@ApiResponse(code = 404, message = "Endereço não localizado"),
            @ApiResponse(code = 403, message = "Você não possui permissão para visualizar este recurso"),
            @ApiResponse(code = 401, message = "Você não possui credenciais de autenticação válidas"),
            @ApiResponse(code = 200, message = "Endereço localizado")})
    public ResponseEntity<EnderecoResponseDTO> busqueEnderecoPeloCep(@PathVariable String cep) {
        Endereco enderecoEncontradoPeloCep = enderecoService.busqueEnderecoPeloCep(cep);
        return ResponseEntity.ok(enderecoEncontradoPeloCep.converteParaResponse());
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Atualiza o Endereço, se existir, especificado pelo Id informado")
    @ApiResponses(value = {@ApiResponse(code = 404, message = "Endereço não localizado"),
            @ApiResponse(code = 403, message = "Você não possui permissão para visualizar este recurso"),
            @ApiResponse(code = 401, message = "Você não possui credenciais de autenticação válidas"),
            @ApiResponse(code = 200, message = "Endereço atualizado com sucesso")})
    public ResponseEntity<EnderecoResponseDTO> atualizeEnderecoPeloId(@PathVariable Long id, @RequestBody @Valid EnderecoDTO endereco) {
        Endereco enderecoAtualizado = enderecoService.atualizeEnderecoSeExistir(id, endereco);
        return ResponseEntity.ok(enderecoAtualizado.converteParaResponse());
    }

}
