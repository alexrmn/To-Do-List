package com.alexrmn.todolistspringboot.service;

import com.alexrmn.todolistspringboot.exception.BusinessException;
import com.alexrmn.todolistspringboot.model.Category;
import com.alexrmn.todolistspringboot.model.User;
import com.alexrmn.todolistspringboot.model.dto.CreateCategoryDto;
import com.alexrmn.todolistspringboot.model.dto.UpdateCategoryDto;
import com.alexrmn.todolistspringboot.repository.CategoryRepository;
import com.alexrmn.todolistspringboot.repository.UserRepository;
import com.alexrmn.todolistspringboot.util.AuthUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    public void saveCategory(CreateCategoryDto createCategoryDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Category category = Category.builder()
                .name(createCategoryDto.getName())
                .user(AuthUtils.getLoggedInUser(authentication))
                .build();
        categoryRepository.save(category);
    }

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public Category findById(Integer id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new BusinessException(HttpStatus.NO_CONTENT, "Category with id " + id + " wasn't found."));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!AuthUtils.categoryBelongsToUser(category, authentication)) {
            throw new BusinessException(HttpStatus.FORBIDDEN, "You are not authorised to see this category");
        }
        return category;
    }

    public Category findByName(String name) {
        return categoryRepository.findByName(name)
                .orElseThrow(() -> new BusinessException(HttpStatus.NO_CONTENT, "Category not found"));
    }

    public List<Category> findByUserId(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new BusinessException(HttpStatus.BAD_REQUEST, "User not found"));
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!user.getId().equals(AuthUtils.getLoggedInUser(auth).getId())) {
            throw new BusinessException(HttpStatus.FORBIDDEN, "User not authorised");
        }
        return categoryRepository.findByUserId(id);
    }

    public void updateCategory(UpdateCategoryDto updateCategoryDto) {
        Category category = categoryRepository.findById(updateCategoryDto.getId())
                .orElseThrow(() -> new BusinessException(HttpStatus.NO_CONTENT, "Category with id " + updateCategoryDto.getId() + " wasn't found."));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!category.getUser().getId().equals(AuthUtils.getLoggedInUser(authentication).getId())) {
            throw new BusinessException(HttpStatus.FORBIDDEN, "Not authorised");
        }

        category.setName(updateCategoryDto.getName());
        category.setTasks(updateCategoryDto.getTasks());
        categoryRepository.save(category);
    }

    public void deleteCategory(Category category) {
        categoryRepository.findById(category.getId())
                .orElseThrow(() -> new BusinessException(HttpStatus.NO_CONTENT, "Category not found"));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!category.getUser().getId().equals(AuthUtils.getLoggedInUser(authentication).getId())) {
            throw new BusinessException(HttpStatus.FORBIDDEN, "User not authorised");
        }
        categoryRepository.delete(category);
    }

    public void saveCategory(Category category){
        categoryRepository.save(category);
    }

}
