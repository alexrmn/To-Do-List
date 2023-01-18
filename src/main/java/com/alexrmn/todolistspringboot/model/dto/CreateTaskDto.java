package com.alexrmn.todolistspringboot.model.dto;

import com.alexrmn.todolistspringboot.model.Category;
import com.alexrmn.todolistspringboot.model.User;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.aspectj.lang.annotation.After;

import java.time.LocalDate;

@Data
public class CreateTaskDto {

    @NotBlank
    private String description;

    @FutureOrPresent
    private LocalDate deadline;

    private boolean completed;

    private Category category;

    private User user;

}
