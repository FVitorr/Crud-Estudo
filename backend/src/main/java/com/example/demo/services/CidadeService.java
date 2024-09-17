package com.example.demo.services;

import com.example.demo.model.Cidade;
import com.example.demo.model.dto.CidadeDTO;
import com.example.demo.model.enums.EstadosEnum;
import com.example.demo.repository.CidadeRepository;
import com.example.demo.services.mapper.CidadeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CidadeService {

    private final CidadeRepository cidadeRepository;
    private final CidadeMapper cidadeMapper;

    public CidadeDTO buscarCidadePorId(Long cidadeId){
        return cidadeMapper.toCidadeDTO(this.cidadeRepository.findById(cidadeId).
                orElseThrow(() -> new RuntimeException("Tarefa não encontrada")));
    }


    public List<CidadeDTO> buscarTodasCidades(){
        return this.cidadeRepository.findAll().stream().map(cidadeMapper::toCidadeDTO).toList();
    }

    public List<String> buscarEstados(){
        return Arrays.stream(EstadosEnum.values())
                .map(Enum::name)
                .collect(Collectors.toList());
    }

    public Page<CidadeDTO> buscarTodasCidadesPage(Pageable pageable) {
        return this.cidadeRepository.findAll(pageable).map(cidadeMapper::toCidadeDTO);
    }

    @Transactional
    public boolean apagar (Long cidadeId){
        if (this.cidadeRepository.existsById(cidadeId) && !this.cidadeRepository.cidadeAssociada(cidadeId)){
            this.cidadeRepository.deleteById(cidadeId);
            return true;
        }
        return false;
    }

    @Transactional
    public Cidade salvar(CidadeDTO cidadeDTO){
        return this.cidadeRepository.save(cidadeMapper.toCidade(cidadeDTO));
    }

    @Transactional
    public Cidade atualizar(CidadeDTO cidadeDTO){
        Cidade cidade = cidadeMapper.toCidade(cidadeDTO);
        Optional<Cidade> upCidade = this.cidadeRepository.findById(cidade.getId());
        if (upCidade.isPresent()) {
            Cidade existingCidade = upCidade.get();

            existingCidade.setCidade(cidade.getCidade());
            existingCidade.setEstado(cidade.getEstado());
            existingCidade.setPopulacao(cidade.getPopulacao());

            return cidadeRepository.save(existingCidade);
        } else {
            throw new RuntimeException("Cidade com ID " + cidade.getId() + " não encontrada.");
        }
    }

    public Boolean verificarExisteCidade() {
        return cidadeRepository.count() > 0;
    }

    public Boolean verificarCidadeAssociadas(Long cidadeId) {
        return cidadeRepository.cidadeAssociada(cidadeId);
    }
}
