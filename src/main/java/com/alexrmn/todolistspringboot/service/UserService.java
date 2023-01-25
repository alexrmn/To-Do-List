package com.alexrmn.todolistspringboot.service;

import com.alexrmn.todolistspringboot.exception.BusinessException;
import com.alexrmn.todolistspringboot.model.Category;
import com.alexrmn.todolistspringboot.model.User;
import com.alexrmn.todolistspringboot.model.dto.CreateUserDto;
import com.alexrmn.todolistspringboot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final CategoryService categoryService;

    public void saveUser(CreateUserDto userDto) {

        if (userRepository.findByUsername(userDto.getUsername()).isPresent() || userRepository.findByEmail(userDto.getEmail()).isPresent()) {
            throw  new BusinessException(HttpStatus.CONFLICT, "User with that email or username already exists");
        }

        User user = User.builder()
                .username(userDto.getUsername())
                .email(userDto.getEmail())
                .roles("ROLE_USER")
                .password(passwordEncoder.encode(userDto.getPassword()))
                .build();

        userRepository.save(user);

        addDefaultCategories(user);

    }

    private void addDefaultCategories(User user) {
        //creating default categories
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

    public User findById(Integer id) {
        return userRepository.findById(id).orElseThrow(() -> new BusinessException(HttpStatus.NO_CONTENT, "User not found"));
    }

}
