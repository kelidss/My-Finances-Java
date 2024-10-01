package com.api.my_finances.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.my_finances.models.Transaction;
import com.api.my_finances.services.TransactionService;

import java.util.List;

@RestController
@RequestMapping("transactions")
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @GetMapping
    public List<Transaction> listTransactions(){
        return transactionService.listAll();
    }

    @PostMapping
    public Transaction saveTransaction(@RequestBody Transaction transaction) {
        return transactionService.save(transaction);
    }

    @DeleteMapping("/{id}")
    public void deleteTransaction(@PathVariable Long id){
        transactionService.delete(id);
    }
}
