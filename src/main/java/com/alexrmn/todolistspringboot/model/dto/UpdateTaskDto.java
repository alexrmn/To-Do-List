package com.alexrmn.todolistspringboot.model.dto;

import com.alexrmn.todolistspringboot.model.Category;
import com.alexrmn.todolistspringboot.model.Task;
import com.alexrmn.todolistspringboot.model.User;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Data
@RequiredArgsConstructor
public class UpdateTaskDto {

    private Integer id;

    @NotBlank
    private String description;

    @FutureOrPresent
    private LocalDate deadline;

    private boolean completed;

    private Category category;

    private User user;


    public UpdateTaskDto (Task task) {
        id = task.getId();
        description = task.getDescription();
        deadline = task.getDeadline();
        completed = task.isCompleted();
        category = task.getCategory();
        user = task.getUser();
    }
}
