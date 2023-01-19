package com.alexrmn.todolistspringboot.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class CreateUserDto {

    @NotBlank
    private String username;

    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$\n", message = "invalid email address")
    private String email;

    @NotBlank
    private String password;

}
