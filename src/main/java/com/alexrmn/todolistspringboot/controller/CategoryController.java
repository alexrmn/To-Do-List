package com.alexrmn.todolistspringboot.controller;

import com.alexrmn.todolistspringboot.model.Category;
import com.alexrmn.todolistspringboot.service.CategoryService;
import com.alexrmn.todolistspringboot.service.TaskService;
import jakarta.validation.Valid;
import org.hibernate.query.results.ResultBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;
    private final TaskService taskService;

    public CategoryController(CategoryService categoryService, TaskService taskService) {
        this.categoryService = categoryService;
        this.taskService = taskService;
    }

    @GetMapping("/show-categories")
    public String showCategories(Model model, @ModelAttribute Category category){
        model.addAttribute("categories",categoryService.findAll());
        return "categories/showCategories";
    }

    @GetMapping("/{id}")
    public String showCategory(Model model, @PathVariable Integer id) {
        model.addAttribute("category",categoryService.findById(id));
        model.addAttribute("tasks",taskService.findByCategoryId(id));
        return "categories/category";
    }

    @PostMapping("/show-categories")
    public String createCategory(@Valid Category category, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            return "/categories/categoryValidationError";
        }
        categoryService.createCategory(category);
        return "redirect:/categories/show-categories";
    }

    @DeleteMapping("/{id}")
    public String deleteCategory(Model model, @PathVariable Integer id) {
        model.addAttribute("category", categoryService.findById(id));
        categoryService.deleteCategory(id);
        return "redirect:/categories/show-categories";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateCategoryPage(@PathVariable Integer id, Model model){
        model.addAttribute("category", categoryService.findById(id));
        return "categories/edit-category";
    }

    @PostMapping("/edit/{id}")
    public String updateCategory(@Valid Category category, BindingResult bindingResult, @PathVariable Integer id){
        if (bindingResult.hasErrors()) {
            return "/categories/categoryValidationError";
        }
        Category category1 = categoryService.findById(id);
        category1.setName(category.getName());
        categoryService.updateCategory(category1);
        return "redirect:/categories/show-categories";
    }
}

