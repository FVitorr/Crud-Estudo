package com.example.demo.services;

import com.example.demo.model.Pessoa;
import com.example.demo.model.dto.PessoaDTO;
import com.example.demo.model.vo.FiltroPessoaVO;
import com.example.demo.repository.PessoaRepository;
import com.example.demo.services.mapper.PessoaMapper;
import com.example.demo.util.PdfGenetarorUtil;
import com.lowagie.text.DocumentException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Service
@RequiredArgsConstructor
public class PessoaService {

    private final PdfGenetarorUtil pdfGenetarorUtil;
    private final PessoaRepository pessoaRepository;
    private final PessoaMapper pessoaMapper;
    private final CidadeService cidadeService;

    @Transactional
    public Pessoa salvar(PessoaDTO pessoaDTO) {
        return this.pessoaRepository.save(pessoaMapper.toPessoa(pessoaDTO));
    }


    public Page<PessoaDTO> buscarTodasPessoas(Pageable pageable, FiltroPessoaVO filtro) {
        return this.pessoaRepository.consultarPaginado(pageable,filtro).map(pessoaMapper::toPessoaDTO);
    }

    public PessoaDTO buscarPessoaPorId(Long pessoaId){
        return pessoaMapper.toPessoaDTO(this.pessoaRepository.findById(pessoaId).
                orElseThrow(() -> new RuntimeException("Pessoa não encontrada")));
    }

    @Transactional
    public boolean atualizar(PessoaDTO pessoaDTO){
        Pessoa pessoa = pessoaMapper.toPessoa(pessoaDTO);
        if (this.pessoaRepository.existsById(pessoa.getId())){
            this.pessoaRepository.save(pessoa);
            return true;
        }
        return false;
    }

    @Transactional
    public boolean apagar(Long pessoaId){
        if (this.pessoaRepository.existsById(pessoaId)){
            this.pessoaRepository.deleteById(pessoaId);
            return true;
        }
        return false;
    }
    public boolean verificarExisteCidade(){
        return cidadeService.verificarExisteCidade();
    }


    public byte[] gerarPdfGeral(final long pessoaId) throws DocumentException, ParseException {
        Map<String, Object> data = new ConcurrentHashMap<>();
        montarDadosRelatorio(data,pessoaId);
        return pdfGenetarorUtil.createPdf("relatorio", data);
    }

    private void montarDadosRelatorio(Map<String, Object> data, final long pessoaId) throws ParseException {
        Pessoa pessoa = this.pessoaRepository.findById(pessoaId).orElseThrow(() -> new RuntimeException("Pessoa não encontrada"));
        pessoa.setCpf(PdfGenetarorUtil.formatarCpf(pessoa.getCpf()));
        data.put("pessoa", pessoa);
        data.put("acessoData", LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
    }
}
