package com.alexrmn.todolistspringboot.controller;

import com.alexrmn.todolistspringboot.config.MyUserDetails;
import com.alexrmn.todolistspringboot.model.Category;
import com.alexrmn.todolistspringboot.model.User;
import com.alexrmn.todolistspringboot.model.dto.CreateCategoryDto;
import com.alexrmn.todolistspringboot.model.dto.UpdateCategoryDto;
import com.alexrmn.todolistspringboot.service.CategoryService;
import com.alexrmn.todolistspringboot.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;
    private final TaskService taskService;

    public CategoryController(CategoryService categoryService, TaskService taskService) {
        this.categoryService = categoryService;
        this.taskService = taskService;
    }

    @GetMapping("/user/{id}")
    public String showCategoriesByUserId(@PathVariable Integer id, Model model, Authentication authentication) {
        MyUserDetails myUserDetails = (MyUserDetails) authentication.getPrincipal();
        User user = new User(myUserDetails);
        model.addAttribute("categories", categoryService.findByUserId(id));
        model.addAttribute("user", user);
        model.addAttribute("category", new CreateCategoryDto());
        return "categories/showCategories";
    }

    @GetMapping("/{id}")
    public String showCategory(Model model, @PathVariable Integer id, Authentication authentication) {
        MyUserDetails myUserDetails = (MyUserDetails) authentication.getPrincipal();
        User user = new User(myUserDetails);
        if (user.getId() != categoryService.findById(id).getUser().getId()) {
            throw new AuthorizationServiceException("You are not authorized to see that category.");
        }
        model.addAttribute("category",categoryService.findById(id));
        model.addAttribute("tasks",taskService.findByCategoryId(id));
        return "categories/category";
    }

    @PostMapping("/show-categories/create-new-category")
    public String createCategory(@Valid CreateCategoryDto createCategoryDto, BindingResult bindingResult, Authentication authentication){
        if (bindingResult.hasErrors()) {
            return "/categories/categoryValidationError";
        }
        MyUserDetails myUserDetails = (MyUserDetails) authentication.getPrincipal();
        User user = new User(myUserDetails);
        createCategoryDto.setUser(user);
        categoryService.createCategory(createCategoryDto);
        return "redirect:/categories/user/" + user.getId();
    }

    @DeleteMapping("/{id}/delete")
    public String deleteCategory(Model model, @PathVariable Integer id, Authentication authentication) {
        model.addAttribute("category", categoryService.findById(id));
        MyUserDetails myUserDetails = (MyUserDetails) authentication.getPrincipal();
        User user = new User(myUserDetails);
        categoryService.deleteCategory(id);
        return "redirect:/categories/user/" + user.getId();
    }

    @GetMapping("/edit/{id}")
    public String showEditCategoryPage(@PathVariable Integer id, Model model){
        model.addAttribute("category", new UpdateCategoryDto(categoryService.findById(id)));
        return "categories/edit-category";
    }

    @PostMapping("/edit/{id}/save")
    public String updateCategory(@Valid Category category, BindingResult bindingResult, @PathVariable Integer id, Authentication authentication){
        if (bindingResult.hasErrors()) {
            return "/categories/categoryValidationError";
        }
        MyUserDetails myUserDetails = (MyUserDetails) authentication.getPrincipal();
        User user = new User(myUserDetails);
        Category category1 = categoryService.findById(id);
        category1.setName(category.getName());
        categoryService.updateCategory(category1);
        return "redirect:/categories/user/" + user.getId();
    }

    @GetMapping("/show-all-categories")
    public String showCategories(Model model, @ModelAttribute Category category){
        model.addAttribute("categories",categoryService.findAll());
        return "categories/showAllCategories";
    }
}

