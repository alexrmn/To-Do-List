package com.alexrmn.todolistspringboot.model;

import com.alexrmn.todolistspringboot.config.MyUserDetails;
import com.alexrmn.todolistspringboot.model.dto.CreateUserDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private String username;

    @NotNull
    private  String email;

    @NotNull
    private String password;

    private String roles;

    @OneToMany(mappedBy = "user")
    private List<Task> tasks;

    @OneToMany(mappedBy = "user")
    private List<Category> categories;

    public User(MyUserDetails myUserDetails) {
        this.setId(myUserDetails.getId());
        this.setUsername(myUserDetails.getUsername());
        this.setPassword(myUserDetails.getPassword());
        this.setEmail(myUserDetails.getEmail());
        this.setCategories(myUserDetails.getCategories());
        this.setRoles(myUserDetails.getRoles());
        this.setTasks(myUserDetails.getTasks());
    }


    public User(CreateUserDto createUserDto) {
        this.setUsername(createUserDto.getUsername());
        this.setPassword(createUserDto.getPassword());
        this.setEmail(createUserDto.getEmail());
    }
}
