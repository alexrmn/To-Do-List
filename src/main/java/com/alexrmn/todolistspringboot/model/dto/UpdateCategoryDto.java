package com.alexrmn.todolistspringboot.model.dto;

import com.alexrmn.todolistspringboot.model.Category;
import com.alexrmn.todolistspringboot.model.User;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateCategoryDto {

    private Integer id;

    @NotBlank
    private String name;

    private User user;

    public UpdateCategoryDto(Category category) {
        id = category.getId();
        name = category.getName();
        user = category.getUser();
    }
}
