package com.alexrmn.todolistspringboot.service;

import com.alexrmn.todolistspringboot.model.Category;
import com.alexrmn.todolistspringboot.model.Task;
import com.alexrmn.todolistspringboot.model.User;
import com.alexrmn.todolistspringboot.model.dto.CreateCategoryDto;
import com.alexrmn.todolistspringboot.model.dto.UpdateCategoryDto;
import com.alexrmn.todolistspringboot.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

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

    private CategoryService underTest;

    @BeforeEach
    void setUp() {
        underTest = new CategoryService(categoryRepository);
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
    void saveCategoryWithDto() {
        CreateCategoryDto createCategoryDto = CreateCategoryDto.builder()
                .name("Test Category")
                .user(new User())
                .build();
        Category category = new Category(createCategoryDto);

        underTest.saveCategory(createCategoryDto);

        ArgumentCaptor<Category> categoryArgumentCaptor = ArgumentCaptor.forClass(Category.class);
        verify(categoryRepository).save(categoryArgumentCaptor.capture());
        Category capturedCategory = categoryArgumentCaptor.getValue();
        assertEquals(category,capturedCategory);
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