package com.alexrmn.todolistspringboot.service;

import com.alexrmn.todolistspringboot.exception.BusinessException;
import com.alexrmn.todolistspringboot.model.Task;
import com.alexrmn.todolistspringboot.model.dto.CreateTaskDto;
import com.alexrmn.todolistspringboot.model.dto.UpdateTaskDto;
import com.alexrmn.todolistspringboot.repository.TaskRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
//    private final CategoryService categoryService;



    public List<Task> findAll(){
        return taskRepository.findAll();
    }

    public Task findById(Integer id){
        return taskRepository.findById(id)
                .orElseThrow(() -> new BusinessException(HttpStatus.NO_CONTENT, "Task with id " + id + " wasn't found."));

    }

    public List<Task> findByCategoryId(Integer id) {
        return taskRepository.findByCategoryId(id);
    }

    public List<Task> findByUserId(Integer id){
        return taskRepository.findByUserId(id);
    }

    public void saveTask(CreateTaskDto createTaskDto){
        taskRepository.save(new Task(createTaskDto));
    }

    public void deleteTask(Integer id) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new BusinessException(HttpStatus.NO_CONTENT, "Task not found"));
        taskRepository.delete(task);
    }

    public void updateTask(UpdateTaskDto updateTaskDto) {
        Task task = taskRepository.findById(updateTaskDto.getId())
                .orElseThrow(() -> new BusinessException(HttpStatus.NO_CONTENT, "Task not found"));

        task.setDescription(updateTaskDto.getDescription());
        task.setCategory(updateTaskDto.getCategory());
        task.setDeadline(updateTaskDto.getDeadline());
        task.setCompleted(updateTaskDto.isCompleted());
        taskRepository.save(task);
    }
}
