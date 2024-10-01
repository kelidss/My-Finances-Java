package com.api.my_finances.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.my_finances.models.Category;
import com.api.my_finances.repositorys.CategoryRepository;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> listAll(){
        return categoryRepository.findAll();
    }

    public Category save(Category category){
        return categoryRepository.save(category);
    }

    public void delete(Long id){
        categoryRepository.deleteById(id);
    }
}
