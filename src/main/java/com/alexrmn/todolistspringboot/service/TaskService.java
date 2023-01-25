package com.alexrmn.todolistspringboot.service;

import com.alexrmn.todolistspringboot.exception.BusinessException;
import com.alexrmn.todolistspringboot.model.Task;
import com.alexrmn.todolistspringboot.model.User;
import com.alexrmn.todolistspringboot.model.dto.CreateTaskDto;
import com.alexrmn.todolistspringboot.model.dto.UpdateTaskDto;
import com.alexrmn.todolistspringboot.repository.TaskRepository;
import com.alexrmn.todolistspringboot.repository.UserRepository;
import com.alexrmn.todolistspringboot.util.AuthUtils;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;


    public List<Task> findAll(){
        return taskRepository.findAll();
    }

    public Task findById(Integer id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new BusinessException(HttpStatus.BAD_REQUEST, "Task doesn't exist"));
        if (!AuthUtils.taskBelongsToUser(task, authentication) && !AuthUtils.isAdmin(authentication)) {
            throw new BusinessException(HttpStatus.FORBIDDEN, "You are not authorised to see this task");
        }
        return task;

    }

    public List<Task> findByCategoryId(Integer id) {
        return taskRepository.findByCategoryId(id);
    }

    public List<Task> findByUserId(Integer id){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new BusinessException(HttpStatus.BAD_REQUEST, "User doesn't exist"));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!user.getId().equals(AuthUtils.getLoggedInUser(authentication).getId())) {
            throw new BusinessException(HttpStatus.FORBIDDEN, "User not authorized");
        }

        return taskRepository.findByUserId(id);
    }

    public void saveTask(CreateTaskDto createTaskDto){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        createTaskDto.setUser(AuthUtils.getLoggedInUser(authentication));
        taskRepository.save(new Task(createTaskDto));
    }

    public void deleteTask(Task task) {
        taskRepository.findById(task.getId())
                .orElseThrow(() -> new BusinessException(HttpStatus.NO_CONTENT, "Task not found"));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!task.getUser().getId().equals(AuthUtils.getLoggedInUser(authentication).getId())) {
            throw new BusinessException(HttpStatus.FORBIDDEN, "User not authorized");
        }

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