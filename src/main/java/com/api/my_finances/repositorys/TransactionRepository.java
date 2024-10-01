package com.api.my_finances.repositorys;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.my_finances.models.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long>{   
}
