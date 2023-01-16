package com.alexrmn.todolistspringboot.service;

import com.alexrmn.todolistspringboot.model.Category;
import com.alexrmn.todolistspringboot.model.User;
import com.alexrmn.todolistspringboot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final CategoryService categoryService;

    public void saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles("ROLE_USER");
        userRepository.save(user);
//        creating default categories

        Category work = Category.builder().name("Work").build();
        work.setUser(user);
        Category shopping = Category.builder().name("Shopping").build();
        shopping.setUser(user);
        Category study = Category.builder().name("Study").build();
        study.setUser(user);
        Category chores = Category.builder().name("Chores").build();
        chores.setUser(user);
        Category fitness = Category.builder().name("Fitness").build();
        fitness.setUser(user);

        List<Category> defaultCategories = List.of(work, shopping, study, chores, fitness);
        user.setCategories(defaultCategories);

        categoryService.saveCategory(work);
        categoryService.saveCategory(shopping);
        categoryService.saveCategory(study);
        categoryService.saveCategory(chores);
        categoryService.saveCategory(fitness);

    }
}
