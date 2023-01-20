package com.alexrmn.todolistspringboot.dataloader;

import com.alexrmn.todolistspringboot.model.Category;
import com.alexrmn.todolistspringboot.model.Task;
import com.alexrmn.todolistspringboot.model.User;
import com.alexrmn.todolistspringboot.model.dto.CreateUserDto;
import com.alexrmn.todolistspringboot.repository.CategoryRepository;
import com.alexrmn.todolistspringboot.repository.TaskRepository;
import com.alexrmn.todolistspringboot.repository.UserRepository;
import com.alexrmn.todolistspringboot.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class DataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        User admin = User.builder()
                .id(1)
                .username("admin")
                .email("admin@mail.com")
                .password(encoder.encode("admin"))
                .roles("ROLE_ADMIN,ROLE_USER")
                .build();
        userRepository.save(admin);
    }


}
