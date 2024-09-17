package com.example.demo.model.dto;

import com.example.demo.model.Cidade;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class PessoaDTO {
    private long id;
    private String nome;
    private String time;
    private String cpf;
    private String hobbie;
    private CidadeDTO cidade;
}
