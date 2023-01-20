package com.alexrmn.todolistspringboot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests()
                .requestMatchers("/","/register", "/tasks/taskValidationError", "/logout").permitAll()
                .requestMatchers("/categories/show-all-categories").hasRole("ADMIN")
                .requestMatchers("/task/show-all-tasks").hasRole("ADMIN")
                .requestMatchers("/tasks/**").hasRole("USER")
                .requestMatchers("/categories/**").hasRole("USER")
                .and()
                .formLogin()
                .defaultSuccessUrl("/");

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

}
