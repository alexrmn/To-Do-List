package com.alexrmn.todolistspringboot.model.dto;

import com.alexrmn.todolistspringboot.model.Category;
import com.alexrmn.todolistspringboot.model.Task;
import com.alexrmn.todolistspringboot.model.User;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
public class UpdateCategoryDto {

    private Integer id;

    @NotBlank
    private String name;

    private User user;

    private List<Task> tasks;

    public UpdateCategoryDto(Category category) {
        id = category.getId();
        name = category.getName();
        user = category.getUser();
        tasks = category.getTasks();
    }

}
