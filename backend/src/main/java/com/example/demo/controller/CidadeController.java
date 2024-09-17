package com.example.demo.controller;


import com.example.demo.model.Cidade;
import com.example.demo.model.dto.CidadeDTO;
import com.example.demo.model.enums.EstadosEnum;
import com.example.demo.services.CidadeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/cidade")
@RequiredArgsConstructor
public class CidadeController {

    private final CidadeService cidadeService;


    @GetMapping
    public ResponseEntity<List<CidadeDTO>> buscarTodasCidades() {
        return new ResponseEntity<>(cidadeService.buscarTodasCidades(), HttpStatus.OK);
    }

    @GetMapping("/page")
    public ResponseEntity<Page<CidadeDTO>> obterCidades(Pageable pageable) {
        return new ResponseEntity<>(cidadeService.buscarTodasCidadesPage(pageable), HttpStatus.OK);
    }

    @GetMapping("/estados")
    public ResponseEntity<List<String>> obterEstados() {
        return ResponseEntity.ok(this.cidadeService.buscarEstados());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CidadeDTO> buscarCidadePorId(@PathVariable long id) {
        return ResponseEntity.ok(this.cidadeService.buscarCidadePorId(id));
    }

    @GetMapping("/associada/{id}")
    public ResponseEntity<Boolean> verificarCidadeAssociadas(@PathVariable long id) {
        return ResponseEntity.ok(this.cidadeService.verificarCidadeAssociadas(id));
    }

    @PostMapping
    @Validated
    public ResponseEntity<Void> salvar(@RequestBody CidadeDTO cidadeDTO){
        this.cidadeService.salvar(cidadeDTO);
        return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(cidadeDTO.getId()).toUri()).build();
    }

    @PutMapping("/{id}")
    @Validated
    public ResponseEntity<Void> atualizar(@RequestBody CidadeDTO cidadeDTO, @PathVariable Long id) {
        this.cidadeService.atualizar(cidadeDTO);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @Validated
    public ResponseEntity<Void> apagar(@PathVariable Long id) {
        try {
            this.cidadeService.apagar(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
