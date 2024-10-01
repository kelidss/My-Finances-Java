package com.api.my_finances.repositorys;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.my_finances.models.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>{
}
