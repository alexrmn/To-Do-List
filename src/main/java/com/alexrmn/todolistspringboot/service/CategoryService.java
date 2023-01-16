package com.alexrmn.todolistspringboot.service;

import com.alexrmn.todolistspringboot.model.Category;
import com.alexrmn.todolistspringboot.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public void createCategory(Category category) {
        categoryRepository.save(category);
    }

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public Category findById(Integer id) {
       return categoryRepository.findById(id)
               .orElseThrow(() -> new RuntimeException("Category with id " + id + " wasn't found."));
    }

    public Category findByName(String name) {
        return categoryRepository.findByName(name);
    }

    public List<Category> findByUserId(Integer id) {
        return categoryRepository.findByUserId(id);
    }

    public void updateCategory(Category updatedCategory) {
        Category category = categoryRepository.findById(updatedCategory.getId())
                .orElseThrow(() -> new RuntimeException("Category with id " + updatedCategory.getId() + " wasn't found."));
        category.setName(updatedCategory.getName());
        category.setTasks(updatedCategory.getTasks());
        categoryRepository.save(category);
    }

    public void deleteCategory(Integer id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category not found"));
        categoryRepository.delete(category);
    }

    public void saveCategory(Category category){
        categoryRepository.save(category);
    }

}
