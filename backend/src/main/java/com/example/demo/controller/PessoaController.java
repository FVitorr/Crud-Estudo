package com.example.demo.controller;

import com.example.demo.model.dto.PessoaDTO;
import com.example.demo.model.vo.FiltroPessoaVO;
import com.example.demo.services.PessoaService;
import com.lowagie.text.DocumentException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping("/api/pessoa")
@RequiredArgsConstructor
@Validated
public class PessoaController {
    private final PessoaService pessoaService;


    @PostMapping("/query")
    public ResponseEntity<Page<PessoaDTO>> obterTodasPessoas(@RequestBody final FiltroPessoaVO filtro, final Pageable pageable) {
        System.out.println(filtro.getNome());
        return new ResponseEntity<>(pessoaService.buscarTodasPessoas(pageable, filtro ), HttpStatus.OK);
    }

    @GetMapping("/cidade")
    public ResponseEntity<Boolean> verificarExisteCidade() {
        return ResponseEntity.ok(pessoaService.verificarExisteCidade());
    }

    @PostMapping("/salvar")
    public ResponseEntity<Void> salvar(@Validated @RequestBody PessoaDTO pessoaDTO) {
        pessoaService.salvar(pessoaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Boolean> atualizar(@Validated @RequestBody PessoaDTO pessoaDTO) {
        return ResponseEntity.ok(pessoaService.atualizar(pessoaDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> apagar(@PathVariable long id) {
        pessoaService.apagar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(path = "relatorio/{pessoaId}", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> visualizarPdf(@PathVariable long pessoaId) throws DocumentException, ParseException {
        HttpHeaders respHeaders = new HttpHeaders();
        PessoaDTO pessoaDTO = pessoaService.buscarPessoaPorId(pessoaId);
        respHeaders.setContentDispositionFormData("attachment", pessoaDTO.getNome() + ".pdf");
        respHeaders.setContentType(MediaType.APPLICATION_PDF);

        byte[] pdfBytes = pessoaService.gerarPdfGeral(pessoaId);
        return new ResponseEntity<>(pdfBytes, respHeaders, HttpStatus.OK);
    }
}
