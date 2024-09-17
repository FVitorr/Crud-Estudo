package com.example.demo.services.mapper;

import com.example.demo.model.Pessoa;
import com.example.demo.model.dto.PessoaDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PessoaMapper {
    PessoaDTO toPessoaDTO(Pessoa pessoa);
    Pessoa toPessoa(PessoaDTO pessoaDTO);
}
