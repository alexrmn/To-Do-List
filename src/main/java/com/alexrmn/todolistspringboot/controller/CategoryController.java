package com.alexrmn.todolistspringboot.controller;

import com.alexrmn.todolistspringboot.model.Category;
import com.alexrmn.todolistspringboot.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/show-categories")
    public String showCategories(Model model, @ModelAttribute Category category){
        model.addAttribute("categories",categoryService.findAll());

        return "categories/showCategories";
    }

    @GetMapping("/{id}")
    public String findById(Model model, @PathVariable Integer id) {
        model.addAttribute("category",categoryService.findById(id));
        return "categories/category";
    }

    @PostMapping("/show-categories")
    public String createCategory(@ModelAttribute Category category){
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
    public String updateCategory(@ModelAttribute Category category,@PathVariable Integer id){
        Category category1 = categoryService.findById(id);
        category1.setName(category.getName());
        categoryService.saveCategory(category1);
        return "redirect:/categories/show-categories";
    }
}

