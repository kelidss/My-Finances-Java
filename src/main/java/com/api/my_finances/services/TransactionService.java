package com.api.my_finances.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.my_finances.models.Category;
import com.api.my_finances.models.Transaction;
import com.api.my_finances.repositorys.CategoryRepository;
import com.api.my_finances.repositorys.TransactionRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionService extends BaseService<Transaction, Long> {

    private TransactionRepository transactionRepository;
    private CategoryRepository categoryRepository;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository, CategoryRepository categoryRepository) {
        super(transactionRepository, "transação");
        this.transactionRepository = transactionRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Transaction save(Transaction transaction) {
        if (transaction.getCategory() != null && transaction.getCategory().getId() != null) {
            Category category = categoryRepository.findById(transaction.getCategory().getId())
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));

            transaction.setCategory(category);
        } else {
            throw new RuntimeException("Categoria inválida ou não fornecida");
        }

        transaction.setCreatedAt(LocalDateTime.now());
        return transactionRepository.save(transaction);
    }

    public List<Transaction> getAll() {
        return transactionRepository.findAll();  
    }
}
