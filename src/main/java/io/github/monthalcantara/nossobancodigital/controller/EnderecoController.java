package io.github.monthalcantara.nossobancodigital.controller;

import io.github.monthalcantara.nossobancodigital.dto.request.EnderecoDTO;
import io.github.monthalcantara.nossobancodigital.dto.response.ClienteResponseDTO;
import io.github.monthalcantara.nossobancodigital.dto.response.EnderecoResponseDTO;
import io.github.monthalcantara.nossobancodigital.mappers.EnderecoMapper;
import io.github.monthalcantara.nossobancodigital.model.Cliente;
import io.github.monthalcantara.nossobancodigital.model.Endereco;
import io.github.monthalcantara.nossobancodigital.service.interfaces.EnderecoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("v1/endereco")
public class EnderecoController {

    @Autowired
    EnderecoService enderecoService;

    @Autowired
    EnderecoMapper enderecoMapper;

    @GetMapping
    public Page<EnderecoResponseDTO> busqueTodosEnderecos(@PageableDefault(size = 5) Pageable pageable) {
        return enderecoService.busqueTodosClientes(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EnderecoResponseDTO> busqueEnderecoPeloId(@PathVariable Long id) {
        Endereco enderecoEncontrado = enderecoService.busqueEnderecoPeloId(id);
        return ResponseEntity.ok(converteParaEnderecoResponseDTO(enderecoEncontrado));
    }

    @PutMapping("/{id}")
    public EnderecoResponseDTO atualizeEnderecoPeloId(@PathVariable Long id, @RequestBody @Valid EnderecoDTO endereco) {
        Endereco enderecoAtualizado = enderecoService.atualizeEnderecoSeExistir(id, endereco);
        return converteParaEnderecoResponseDTO(enderecoAtualizado);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEnderecoPeloId(@PathVariable Long id) {
        enderecoService.deleteEnderecoPeloId(id);
    }

    private EnderecoResponseDTO converteParaEnderecoResponseDTO(Endereco endereco) {
        return enderecoMapper.converteParaEnderecoResponseDTO(endereco);
    }

}
