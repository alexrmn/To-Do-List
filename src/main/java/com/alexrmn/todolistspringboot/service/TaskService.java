package com.alexrmn.todolistspringboot.service;

import com.alexrmn.todolistspringboot.model.Category;
import com.alexrmn.todolistspringboot.model.Task;
import com.alexrmn.todolistspringboot.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> findAll(){
        return taskRepository.findAll();
    }

    public Task findById(Integer id){
        return taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task with id " + id + " wasn't found."));
    }

    public List<Task> findByCategoryId(Integer id) {
        return taskRepository.findByCategoryId(id);
    }

    public void saveTask(Task task){
        taskRepository.save(task);
    }

    public void deleteTask(Integer id) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Task not found"));
        taskRepository.delete(task);
    }

    public void updateTask(Task updatedTask) {
        Task task = taskRepository.findById(updatedTask.getId())
                .orElseThrow(() -> new RuntimeException("Task with id " + updatedTask.getId() + " wasn't found."));
        task.setDescription(updatedTask.getDescription());
        task.setCompleted(updatedTask.isCompleted());
        task.setCategory(updatedTask.getCategory());
        task.setDeadline(updatedTask.getDeadline());
        taskRepository.save(updatedTask);
    }
}
