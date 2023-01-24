package com.alexrmn.todolistspringboot.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests()
                .requestMatchers("/","/register", "/tasks/taskValidationError", "/logout").permitAll()
                .requestMatchers("/categories/show-all-categories", "/tasks/show-all-tasks").hasRole("ADMIN")
                .requestMatchers("/tasks/**", "/categories/**").hasAnyRole("USER", "ADMIN")
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
