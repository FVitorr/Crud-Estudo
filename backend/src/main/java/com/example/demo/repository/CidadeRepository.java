package com.example.demo.repository;

import com.example.demo.model.Cidade;
import com.example.demo.model.dto.CidadeDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Long>{

    @Query("SELECT CASE WHEN COUNT(p) > 0 THEN TRUE ELSE FALSE END " +
            "FROM Cidade c " +
            "LEFT JOIN Pessoa p ON c.id = p.cidade.id " +
            "WHERE c.id = :cidadeId")
    boolean cidadeAssociada (@Param("cidadeId") Long cidadeId);
}
