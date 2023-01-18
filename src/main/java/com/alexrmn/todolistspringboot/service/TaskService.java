package com.alexrmn.todolistspringboot.service;

import com.alexrmn.todolistspringboot.model.Task;
import com.alexrmn.todolistspringboot.model.dto.CreateTaskDto;
import com.alexrmn.todolistspringboot.model.dto.UpdateTaskDto;
import com.alexrmn.todolistspringboot.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final CategoryService categoryService;

    public TaskService(TaskRepository taskRepository, CategoryService categoryService) {
        this.taskRepository = taskRepository;
        this.categoryService = categoryService;
    }

    public List<Task> findAll(){
        List<Task> tasks = taskRepository.findAll();
        for (Task task: tasks) {
            if (task.getCategory() == null) {
                task.setCategory(categoryService.findByName("None"));
            }
        }
        return tasks;
    }

    public Task findById(Integer id){
        return taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task with id " + id + " wasn't found."));

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
        Task task = taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Task not found"));
        taskRepository.delete(task);
    }

    public void updateTask(UpdateTaskDto updateTaskDto) {
        Task task = taskRepository.findById(updateTaskDto.getId())
                .orElseThrow(() -> new RuntimeException("Task not found"));

        task.setCategory(updateTaskDto.getCategory());
        task.setDeadline(updateTaskDto.getDeadline());
        task.setCompleted(updateTaskDto.isCompleted());
        taskRepository.save(task);
    }
}
