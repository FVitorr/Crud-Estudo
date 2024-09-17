package com.example.demo.services.mapper;

import com.example.demo.model.Cidade;
import com.example.demo.model.dto.CidadeDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CidadeMapper {
    CidadeDTO toCidadeDTO(Cidade cidade);
    Cidade toCidade(CidadeDTO cidadeDTO);
}
