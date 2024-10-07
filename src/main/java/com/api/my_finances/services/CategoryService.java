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
        try{
            return categoryRepository.findAll();
        } catch (Exception e){
            throw new RuntimeException("Não possivel carregar as caregorias no momento");
        }
    }

    public Category save(Category category){
        
        // verifica se nome nao e vazio
        if(category == null || category.getName() == null || category.getName().trim().isEmpty()){
            throw new IllegalArgumentException("O nome da categoria não pode estar vazio");
        }

        // validacao para verificar se a categoria tem um nome especifico
        if("Categoria".equals(category.getName())) {
            throw new IllegalArgumentException("Preencha uma categoria válida");
        }

        // verifica se o nome ja existe
        if(categoryRepository.existsByName(category.getName())) {
            throw new IllegalArgumentException("Essa categoria já existe.");
        }

        return categoryRepository.save(category); // retorna o ID
    }

    public String delete(Long id){
        if(!categoryRepository.existsById(id)){
            throw new IllegalArgumentException("ID não existe.");
        }
        categoryRepository.deleteById(id);
        return "Deletado com sucesso";
    }
}
