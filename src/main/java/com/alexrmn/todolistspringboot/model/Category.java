package com.alexrmn.todolistspringboot.model;

import com.alexrmn.todolistspringboot.model.dto.CreateCategoryDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.lang.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name="categories")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @Column(name="name")
    private String name;

    @OneToMany(mappedBy = "category", cascade = CascadeType.REMOVE)
    private List<Task> tasks;

    @ManyToOne
    private User user;

    public Category(CreateCategoryDto createCategoryDto){
        this.name = createCategoryDto.getName();
        this.user = createCategoryDto.getUser();
    }

}
