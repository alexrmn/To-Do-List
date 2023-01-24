package com.alexrmn.todolistspringboot.controller;


import com.alexrmn.todolistspringboot.model.dto.CreateUserDto;
import com.alexrmn.todolistspringboot.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest()
@ExtendWith(MockitoExtension.class)
class HomepageControllerTest {


    @MockBean
    private UserService userService;

    @Autowired
    private WebApplicationContext context;


    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    @DisplayName("Calling / endpoint with unauthenticated user")
    void showHomepageUnauthenticatedUser() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("homepage"));

    }

    @Test
    @WithUserDetails("user1")
    void showHomepageAuthenticatedUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("homepage"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("user"));
    }

    @Test
    @WithUserDetails("admin")
    void showHomepageAuthenticatedAdmin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("adminHomepage"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("user"));
    }

    @Test
    void showRegisterPage() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("registrationPage"));

    }


    @Test
    @Disabled("not working properly")
    void registerUserWithValidInput() throws Exception {
        // Arrange
        CreateUserDto createUserDto = new CreateUserDto("username", "email@example.com", "password");

        // Act
        mockMvc.perform(MockMvcRequestBuilders.post("/register")
                        .param("username", createUserDto.getUsername())
                        .param("email", createUserDto.getEmail())
                        .param("password", createUserDto.getPassword()))


                // Assert
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("homepage"))
                .andExpect(redirectedUrl("/"));
        verify(userService).saveUser(createUserDto);
    }
}

