package com.alexrmn.todolistspringboot.model;

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
    @NotBlank
    private String description;

    @Column(name="deadline")
    private LocalDate deadline;

    @Column(name="completed")
    private boolean completed;

    @ManyToOne
    private Category category;

    @ManyToOne
    private User user;

}


