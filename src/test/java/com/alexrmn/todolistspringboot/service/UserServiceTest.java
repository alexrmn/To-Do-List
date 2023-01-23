package com.alexrmn.todolistspringboot.service;

import com.alexrmn.todolistspringboot.model.User;
import com.alexrmn.todolistspringboot.model.dto.CreateUserDto;
import com.alexrmn.todolistspringboot.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private CategoryService categoryService;

    @Mock
    private BCryptPasswordEncoder encoder;

    private UserService underTest;

    @BeforeEach
    void setUp() {
        underTest = new UserService(userRepository, encoder, categoryService);
    }

    @Test
    void saveUser() {
        CreateUserDto createUserDto = CreateUserDto.builder()
                .username("username")
                .email("email")
                .password("password")
                .build();
        when(encoder.encode(createUserDto.getPassword())).thenReturn("encodedpassword");

        User user = User.builder()
                .username(createUserDto.getUsername())
                .email(createUserDto.getEmail())
                .password(encoder.encode(createUserDto.getPassword()))
                .build();

        underTest.saveUser(createUserDto);

        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        Mockito.verify(userRepository).save(userArgumentCaptor.capture());
        User capturedUser = userArgumentCaptor.getValue();
        assertEquals(user.getUsername(), capturedUser.getUsername());
        assertEquals(user.getEmail(), capturedUser.getEmail());
        assertEquals(user.getPassword(), capturedUser.getPassword());
    }

    @Test
    void findById() {
        User user = User.builder()
                .id(1)
                .build();

        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        User result = underTest.findById(1);

        verify(userRepository).findById(1);
        assertEquals(user, result);
    }
}