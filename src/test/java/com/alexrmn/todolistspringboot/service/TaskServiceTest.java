package com.alexrmn.todolistspringboot.service;

import com.alexrmn.todolistspringboot.model.Category;
import com.alexrmn.todolistspringboot.model.Task;
import com.alexrmn.todolistspringboot.model.User;
import com.alexrmn.todolistspringboot.model.dto.CreateTaskDto;
import com.alexrmn.todolistspringboot.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService underTest;

    @BeforeEach
    void setUp() {
        underTest = new TaskService(taskRepository);
    }

    @Test
    void findAll() {
        //when
        underTest.findAll();
        //then
        verify(taskRepository, times(1)).findAll();
    }

    @Test
    void findById() {
        //given
        Task task = Task.builder()
                .id(1)
                .description("Test task")
                .deadline(LocalDate.now())
                .completed(false)
                .build();
        when(taskRepository.findById(1)).thenReturn((Optional.of(task)));
        //when
        Task result = underTest.findById(1);
        //then
        assertEquals(task, result);
    }

    @Test
    void findByCategoryId() {
        Category category= Category.builder()
                .id(1)
                .name("test category")
                .build();
        List<Task> expected = List.of(
                Task.builder()
                .description("Test task")
                .category(category)
                .deadline(LocalDate.now())
                .completed(false)
                .build()
        );
        when(taskRepository.findByCategoryId(1)).thenReturn(expected);
        List<Task> actual = underTest.findByCategoryId(1);
        assertEquals(expected,actual);
    }

    @Test
    void findByUserId() {
        User user = User.builder()
                .id(1)
                .username("test_user")
                .build();
        List<Task> expected = List.of(
                Task.builder()
                        .description("Test task")
                        .user(user)
                        .deadline(LocalDate.now())
                        .completed(false)
                        .build()
        );
        when(taskRepository.findByUserId(1)).thenReturn(expected);
        List<Task> actual = underTest.findByUserId(1);
        assertEquals(expected,actual);

    }

    @Test
    void saveTask() {
        //given
        CreateTaskDto createTaskDto = CreateTaskDto.builder()
                .description("test task")
                .deadline(LocalDate.now())
                .completed(false)
                .build();
        Task task = new Task(createTaskDto);
        //when
        underTest.saveTask(createTaskDto);

        //then
        ArgumentCaptor<Task> taskArgumentCaptor =
                ArgumentCaptor.forClass(Task.class);
        verify(taskRepository).save(taskArgumentCaptor.capture());
        Task capturedTask = taskArgumentCaptor.getValue();

        assertEquals(task, capturedTask);

    }


    @Test
    void deleteTask() {
        //given
        Task task = Task.builder()
                .id(1)
                .description("Test task")
                .deadline(LocalDate.now())
                .completed(false)
                .build();
        //when
        underTest.deleteTask(task.getId());
        //then
        ArgumentCaptor<Task> taskArgumentCaptor = ArgumentCaptor.forClass(Task.class);
        verify(taskRepository).delete(taskArgumentCaptor.capture());
        Task capturedTask = taskArgumentCaptor.getValue();
        assertEquals(task, capturedTask);
    }

    @Test
    void updateTask() {
    }
}