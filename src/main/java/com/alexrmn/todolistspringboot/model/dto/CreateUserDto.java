package com.alexrmn.todolistspringboot.model.dto;

import com.alexrmn.todolistspringboot.model.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class CreateUserDto {

    @NotBlank
    private String username;

    @NotBlank
    @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "invalid email address")
    private String email;

    @NotBlank
    private String password;


}
