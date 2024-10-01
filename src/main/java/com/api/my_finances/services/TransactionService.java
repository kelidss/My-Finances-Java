package com.api.my_finances.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.my_finances.models.Category;
import com.api.my_finances.models.Transaction;
import com.api.my_finances.repositorys.CategoryRepository;
import com.api.my_finances.repositorys.TransactionRepository;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Transaction> listAll(){
        return transactionRepository.findAll();
    }

    public Transaction save(Transaction transaction){
        // Verifica se a transacao tem uma categoria e se o ID da categoria esta presente
        if (transaction.getCategory() != null && transaction.getCategory().getId() != null) {
            // Busca a categoria no banco de dados pelo ID
            Category category = categoryRepository.findById(transaction.getCategory().getId())
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));

            // Associa a categoria encontrada a transacao
            transaction.setCategory(category);
        } else {
            throw new RuntimeException("Categoria inválida ou não fornecida");
        }

        // Salva a transacao com a categoria associada
        return transactionRepository.save(transaction);
    }

    public void delete(Long id){
        transactionRepository.deleteById(id);
    }
}
