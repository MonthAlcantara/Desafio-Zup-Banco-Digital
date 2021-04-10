package io.github.monthalcantara.nossobancodigital.controller;

import io.github.monthalcantara.nossobancodigital.controller.interfaces.EnderecoControllerSwagger;
import io.github.monthalcantara.nossobancodigital.dto.request.EnderecoDTO;
import io.github.monthalcantara.nossobancodigital.dto.response.EnderecoResponseDTO;
import io.github.monthalcantara.nossobancodigital.model.Endereco;
import io.github.monthalcantara.nossobancodigital.service.interfaces.EnderecoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("v1/admin/endereco")
public class EnderecoController implements EnderecoControllerSwagger {

    private EnderecoService enderecoService;

    public EnderecoController(EnderecoService enderecoService) {
        this.enderecoService = enderecoService;
    }

    @GetMapping
    public ResponseEntity<Page<EnderecoResponseDTO>> busqueTodosEnderecos(@PageableDefault(size = 5) Pageable pageable) {
        Page<EnderecoResponseDTO> enderecosEncontrados = enderecoService.busqueTodosClientes(pageable);
        return ResponseEntity.ok(enderecosEncontrados);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EnderecoResponseDTO> busqueEnderecoPeloId(@PathVariable Long id) {
        Endereco enderecoEncontradoPeloId = enderecoService.busqueEnderecoPeloId(id);
        return ResponseEntity.ok(enderecoEncontradoPeloId.converteParaResponse());
    }

    @GetMapping("/cep/{cep}")
    public ResponseEntity<EnderecoResponseDTO> busqueEnderecoPeloCep(@PathVariable String cep) {
        Endereco enderecoEncontradoPeloCep = enderecoService.busqueEnderecoPeloCep(cep);
        return ResponseEntity.ok(enderecoEncontradoPeloCep.converteParaResponse());
    }

    @PutMapping("/{id}")
    public ResponseEntity<EnderecoResponseDTO> atualizeEnderecoPeloId(@PathVariable Long id, @RequestBody @Valid EnderecoDTO endereco) {
        Endereco enderecoAtualizado = enderecoService.atualizeEnderecoSeExistir(id, endereco);
        return ResponseEntity.ok(enderecoAtualizado.converteParaResponse());
    }
}
