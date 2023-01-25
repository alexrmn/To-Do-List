package com.alexrmn.todolistspringboot.model.dto;

import com.alexrmn.todolistspringboot.model.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserDto {

    @NotBlank
    private String username;

    @NotBlank
    @Email(message = "invalid email address")
    private String email;

    @NotBlank
    private String password;


}
