package com.alexrmn.todolistspringboot.repository;

import com.alexrmn.todolistspringboot.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Optional<Category> findByName(String name);

    List<Category> findByUserId(Integer id);
}
