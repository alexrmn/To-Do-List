package com.alexrmn.todolistspringboot.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests()
                .requestMatchers("/api/**").permitAll()
                .requestMatchers("/","/register", "/tasks/taskValidationError", "/logout").permitAll()
                .requestMatchers("/categories/show-all-categories", "/tasks/show-all-tasks").hasRole("ADMIN")
                .requestMatchers("/tasks/**", "/categories/**").hasAnyRole("USER", "ADMIN")
                .and()
                .formLogin()
                .defaultSuccessUrl("/")
                .and()
                .logout()
                .logoutUrl("/");
        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

}
