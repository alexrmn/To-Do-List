package com.alexrmn.todolistspringboot.controller;

import com.alexrmn.todolistspringboot.exception.BusinessException;
import com.alexrmn.todolistspringboot.model.Category;
import com.alexrmn.todolistspringboot.model.User;
import com.alexrmn.todolistspringboot.model.dto.CreateCategoryDto;
import com.alexrmn.todolistspringboot.model.dto.UpdateCategoryDto;
import com.alexrmn.todolistspringboot.service.CategoryService;
import com.alexrmn.todolistspringboot.service.TaskService;
import com.alexrmn.todolistspringboot.util.AuthUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;
    private final TaskService taskService;

    @GetMapping("/user/{id}")
    public String showCategoriesByUserId(@PathVariable Integer id, Model model, Authentication authentication) {
        model.addAttribute("categories", categoryService.findByUserId(id));
        model.addAttribute("user", AuthUtils.getLoggedInUser(authentication));
        model.addAttribute("category", new CreateCategoryDto());
        return "categories/showCategories";
    }

    @GetMapping("/{id}")
    public String showCategory(Model model, @PathVariable Integer id, Authentication authentication) {
        model.addAttribute("category",categoryService.findById(id));
        model.addAttribute("tasks",taskService.findByCategoryId(id));
        return "categories/category";
    }

    @PostMapping("/show-categories/create-new-category")
    public String createCategory(@Valid CreateCategoryDto createCategoryDto, BindingResult bindingResult, Authentication authentication){
        if (bindingResult.hasErrors()) {
            return "/categories/categoryValidationError";
        }
        categoryService.saveCategory(createCategoryDto);
        return "redirect:/categories/user/" + AuthUtils.getLoggedInUser(authentication).getId();
    }

    @DeleteMapping("/{id}/delete")
    public String deleteCategory(@PathVariable Integer id, Authentication authentication) {
        categoryService.deleteCategory(categoryService.findById(id));
        return "redirect:/categories/user/" + AuthUtils.getLoggedInUser(authentication).getId();
    }

    @GetMapping("/edit/{id}")
    public String showEditCategoryPage(@PathVariable Integer id, Model model ){
        Category category = categoryService.findById(id);
        model.addAttribute("category", new UpdateCategoryDto(category));
        return "categories/edit-category";
    }

    @PostMapping("/edit/{id}/save")
    public String updateCategory(@Valid UpdateCategoryDto updateCategoryDto, BindingResult bindingResult, Authentication authentication){
        if (bindingResult.hasErrors()) {
            return "/categories/categoryValidationError";
        }
        categoryService.updateCategory(updateCategoryDto);
        return "redirect:/categories/user/" + AuthUtils.getLoggedInUser(authentication).getId();
    }

    @GetMapping("/show-all-categories")
    @Secured("ROLE_ADMIN")
    public String showCategories(Model model, @ModelAttribute Category category, Authentication authentication){
        model.addAttribute("categories",categoryService.findAll());
        return "categories/showAllCategories";
    }
}

