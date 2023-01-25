package com.alexrmn.todolistspringboot.service;

import com.alexrmn.todolistspringboot.model.Category;
import com.alexrmn.todolistspringboot.model.Task;
import com.alexrmn.todolistspringboot.model.User;
import com.alexrmn.todolistspringboot.model.dto.CreateCategoryDto;
import com.alexrmn.todolistspringboot.model.dto.UpdateCategoryDto;
import com.alexrmn.todolistspringboot.repository.CategoryRepository;
import com.alexrmn.todolistspringboot.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithUserDetails;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private UserRepository userRepository;

    private CategoryService underTest;

    @BeforeEach
    void setUp() {
        underTest = new CategoryService(categoryRepository, userRepository);
    }

    @Test
    void saveCategory() {
        Category category = Category.builder()
                .name("Test Category")
                .user(new User())
                .tasks(new ArrayList<>())
                .build();
        underTest.saveCategory(category);

        ArgumentCaptor<Category> categoryArgumentCaptor = ArgumentCaptor.forClass(Category.class);
        verify(categoryRepository).save(categoryArgumentCaptor.capture());
        Category capturedCategory = categoryArgumentCaptor.getValue();
        assertEquals(category, capturedCategory);
    }

    @Test
    public void saveCategoryTest_withAuthentication() {
        // Create a mock Authentication object
        Authentication authentication = mock(Authentication.class);

        // Create a mock User object
        User user = new User();
        user.setId(1);
        user.setUsername("testuser");

        // Set up the mock Authentication object to return the mock User object when getPrincipal is called
        when(authentication.getPrincipal()).thenReturn(user);

        // Set up the SecurityContext to return the mock Authentication object when getAuthentication is called
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        // Create a CreateCategoryDto instance
        CreateCategoryDto createCategoryDto = new CreateCategoryDto();
        createCategoryDto.setName("Test Category");

        // Create a Category instance
        Category category = new Category();
        category.setName(createCategoryDto.getName());
        category.setUser(user);

        // Set up the mock object to return the Category instance when the save method is called
        when(categoryRepository.save(category)).thenReturn(category);

        // Call the saveCategory method
        underTest.saveCategory(createCategoryDto);

        // Verify that the save method was called with the expected Category instance
        verify(categoryRepository, times(1)).save(category);
    }

    @Test
    void findAll() {
        underTest.findAll();
        verify(categoryRepository).findAll();
    }

    @Test
    void findById() {
        Category category = Category.builder()
                .id(1)
                .name("Test Category")
                .user(new User())
                .tasks(new ArrayList<>())
                .build();
        when(categoryRepository.findById(1)).thenReturn(Optional.of(category));
        Category result = underTest.findById(1);
        assertEquals(category, result);
    }

    @Test
    void findByName() {

        String name = "Test Category";
        Category category = Category.builder()
                .id(1)
                .name(name)
                .user(new User())
                .tasks(new ArrayList<>())
                .build();
        when(categoryRepository.findByName(name)).thenReturn(Optional.of(category));
        Category actualCategory = underTest.findByName(name);
        verify(categoryRepository).findByName(name);
        assertEquals(category, actualCategory);
    }

    @Test
    void findByUserId() {
        User user = User.builder()
                .id(1)
                .username("test_user")
                .build();
        List<Category> expected = List.of(
          new Category(),
          new Category()
        );

        when(categoryRepository.findByUserId(1)).thenReturn(expected);
        List<Category> actual = underTest.findByUserId(1);
        assertEquals(expected, actual);
    }

    @Test
    void updateCategory() {
        Category category = Category.builder()
                .id(1)
                .name("Test Category")
                .user(new User())
                .tasks(new ArrayList<>())
                .build();
        UpdateCategoryDto updateCategoryDto = new UpdateCategoryDto(category);
        when(categoryRepository.findById(1)).thenReturn(Optional.of(category));

        underTest.updateCategory(updateCategoryDto);

        ArgumentCaptor<Category> categoryArgumentCaptor = ArgumentCaptor.forClass(Category.class);
        verify(categoryRepository).save(categoryArgumentCaptor.capture());
        Category capturedCategory = categoryArgumentCaptor.getValue();
        assertEquals(category, capturedCategory);
    }

    @Test
    void deleteCategory() {
        Category category = Category.builder()
                .id(1)
                .name("Test Category")
                .user(new User())
                .tasks(new ArrayList<>())
                .build();
        when(categoryRepository.findById(1)).thenReturn(Optional.of(category));
        underTest.deleteCategory(category);

        ArgumentCaptor<Category> categoryArgumentCaptor = ArgumentCaptor.forClass(Category.class);
        verify(categoryRepository).delete(categoryArgumentCaptor.capture());
        Category capturedCategory = categoryArgumentCaptor.getValue();
        assertEquals(category, capturedCategory);
    }

}