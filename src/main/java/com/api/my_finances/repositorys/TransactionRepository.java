package com.api.my_finances.repositorys;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.api.my_finances.models.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long>{

    @Query("SELECT tr FROM Transaction tr WHERE tr.createdAt >= :inicio AND tr.createdAt < :fim ORDER BY tr.createdAt DESC")
    List<Transaction> findByAtualDate(
        @Param("inicio") LocalDateTime inicio, 
        @Param("fim") LocalDateTime fim
    );
}
