package com.api.my_finances.controllers;

import java.util.Locale.Category;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @PersistenceContext
    private EntityManager entityManager; // EntityManager para gerenciar persistência

    @PostMapping("/create")
    @Transactional
    public String createCategory(@RequestBody Category category) {
        entityManager.persist(category);
        return "Categoria criado com sucesso!";
    }

    @GetMapping("/{id}")
    @Transactional
    public Category obterUsuarioPorId(@PathVariable Long id) {
        Category category = entityManager.find(Category.class, id);
        
        if (category == null) {
            throw new RuntimeException("Categoria não encontrada.");
        }
        
        return category;
    }
}
