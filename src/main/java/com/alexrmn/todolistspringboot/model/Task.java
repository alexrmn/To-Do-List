package com.alexrmn.todolistspringboot.model;

import com.alexrmn.todolistspringboot.model.dto.CreateTaskDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;


@Entity
@Table(name="tasks")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @Column(name="description")
    private String description;

    @Column(name="deadline")
    private LocalDate deadline;

    @Column(name="completed")
    private boolean completed;

    @ManyToOne
    private Category category;

    @ManyToOne
    private User user;

    public Task(CreateTaskDto createTaskDto) {
        description = createTaskDto.getDescription();
        deadline = createTaskDto.getDeadline();
        completed = createTaskDto.isCompleted();
        category = createTaskDto.getCategory();
        user = createTaskDto.getUser();
    }

}


