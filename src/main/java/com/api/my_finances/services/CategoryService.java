package com.api.my_finances.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.my_finances.models.Category;
import com.api.my_finances.repositorys.CategoryRepository;

@Service
public class CategoryService extends BaseService<Category, Long>{

    private CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository){
        super(categoryRepository, "categoria");
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category save(Category category){
        
        if(category == null || category.getName() == null || category.getName().trim().isEmpty()){
            throw new IllegalArgumentException("O nome da categoria não pode estar vazio");
        }

        if("Categoria".equals(category.getName())) {
            throw new IllegalArgumentException("Preencha uma categoria válida");
        }

        if(categoryRepository.existsByName(category.getName())) {
            throw new IllegalArgumentException("Essa categoria já existe.");
        }

        return categoryRepository.save(category); 
    }
}
