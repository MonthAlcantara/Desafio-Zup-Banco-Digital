package io.github.monthalcantara.nossobancodigital.controller.interfaces;

import io.github.monthalcantara.nossobancodigital.dto.request.ClienteDTO;
import io.github.monthalcantara.nossobancodigital.dto.request.EnderecoDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

@Api(value = "Endpoint de cadastro de clientes",
        description = "Cadastro de clientes seguindo passos Cliente > Endereço > Documento > Aceite",
        tags = {"Cadastro de clientes"})
public interface CadastroClienteControllerSwagger {


    @ApiOperation(value = "Salva dados do Cliente")
    @ApiResponses(value = {@ApiResponse(code = 404, message = "Não foi possível criar o Cliente"),
            @ApiResponse(code = 201, message = "Cliente criado com sucesso")})
    public ResponseEntity salveCliente(@RequestBody @Valid ClienteDTO client);


    @ApiOperation(value = "Salva dados de endereço do Cliente")
    @ApiResponses(value = {@ApiResponse(code = 404, message = "Não foi possível criar o Endereço"),
            @ApiResponse(code = 422, message = "Necessario Cadastrar o Cliente"),
            @ApiResponse(code = 201, message = "Endereço criado com sucesso")})
    public ResponseEntity salveEndereco(@RequestBody @Valid EnderecoDTO endereco, @PathVariable("id") Long id);


    @ApiOperation(value = "Salva documentos de identificação do cliente (frente e verso)")
    @ApiResponses(value = {@ApiResponse(code = 404, message = "Não foi possível criar o Documento"),
            @ApiResponse(code = 422, message = "Necessario Cadastrar o Cliente e Endereço"),
            @ApiResponse(code = 201, message = "Documento salvo com sucesso")})
    public ResponseEntity salveDocumento(@RequestParam("frente") MultipartFile fotoDocumentoFrente,
                                         @RequestParam("verso") MultipartFile fotoDocumentoVerso,
                                         @PathVariable("id") Long id);

    @ApiOperation(value = "Recebe aceite do cliente a proposta de abertura de conta")
    @ApiResponses(value = {@ApiResponse(code = 404, message = "Não foi possível criar o Documento"),
            @ApiResponse(code = 422, message = "Necessario Cadastrar o Cliente, Endereço e Documento de identificação"),
            @ApiResponse(code = 200, message = "Aceite recebido com sucesso")})
    public ResponseEntity aceiteContrato(@PathVariable("id") Long id, @PathParam("aceite") Boolean aceite);


}