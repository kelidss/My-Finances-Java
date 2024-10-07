package com.api.my_finances.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.my_finances.exceptions.Response;
import com.api.my_finances.models.Category;
import com.api.my_finances.services.CategoryService;
import java.util.List;

@RestController
@RequestMapping("categorys")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<Category>> listCategories(){
        List<Category> categories = categoryService.listAll();

        return ResponseEntity.ok(categories);
    }

    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody Category category){
        Category savedCategory = categoryService.save(category);

        return new ResponseEntity<>(savedCategory, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response> deleteCategory(@PathVariable Long id){
        String message = categoryService.delete(id);
        Response response = new Response(HttpStatus.OK.value(), message);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
