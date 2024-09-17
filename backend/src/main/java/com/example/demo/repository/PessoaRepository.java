package com.example.demo.repository;

import com.example.demo.model.Pessoa;
import com.example.demo.model.vo.FiltroPessoaVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa,Long> {

    @Query(value = "SELECT p FROM Pessoa p WHERE " +
            "(:#{#filtro.nome} IS NULL OR p.nome LIKE concat('%', :#{#filtro.nome}, '%')) AND " +
            "(:#{#filtro.cpf} IS NULL OR p.cpf = :#{#filtro.cpf}) AND " +
            "(:#{#filtro.time} IS NULL OR p.time = :#{#filtro.time}) AND " +
            "(:#{#filtro.cidade} IS NULL OR p.cidade.cidade = :#{#filtro.cidade})"
    )
    Page<Pessoa> consultarPaginado(Pageable pageable, FiltroPessoaVO filtro);

    @Query(value = "SELECT COUNT(p) FROM Pessoa p WHERE p.cidade.id = :cidadeId")
    long countByIdCidade(@Param("cidadeId") Long cidadeId);
}
