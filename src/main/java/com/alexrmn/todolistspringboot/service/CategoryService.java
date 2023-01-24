package com.alexrmn.todolistspringboot.service;

import com.alexrmn.todolistspringboot.exception.BusinessException;
import com.alexrmn.todolistspringboot.model.Category;
import com.alexrmn.todolistspringboot.model.dto.CreateCategoryDto;
import com.alexrmn.todolistspringboot.model.dto.UpdateCategoryDto;
import com.alexrmn.todolistspringboot.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public void saveCategory(CreateCategoryDto createCategoryDto) {
        Category category = Category.builder()
                .name(createCategoryDto.getName())
                .user(createCategoryDto.getUser())
                .build();

        categoryRepository.save(category);
    }

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public Category findById(Integer id) {
       return categoryRepository.findById(id)
               .orElseThrow(() -> new BusinessException(HttpStatus.NO_CONTENT, "Category with id " + id + " wasn't found."));
    }

    public Category findByName(String name) {
        return categoryRepository.findByName(name)
                .orElseThrow(() -> new BusinessException(HttpStatus.NO_CONTENT, "Category not found"));
    }

    public List<Category> findByUserId(Integer id) {
        return categoryRepository.findByUserId(id);
    }

    public void updateCategory(UpdateCategoryDto updateCategoryDto) {
        Category category = categoryRepository.findById(updateCategoryDto.getId())
                .orElseThrow(() -> new BusinessException(HttpStatus.NO_CONTENT, "Category with id " + updateCategoryDto.getId() + " wasn't found."));
        category.setName(updateCategoryDto.getName());
        category.setTasks(updateCategoryDto.getTasks());
        categoryRepository.save(category);
    }

    public void deleteCategory(Category category) {
        categoryRepository.findById(category.getId()).orElseThrow(() -> new BusinessException(HttpStatus.NO_CONTENT, "Category not found"));
        categoryRepository.delete(category);
    }

    public void saveCategory(Category category){
        categoryRepository.save(category);
    }

}
