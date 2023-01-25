package com.alexrmn.todolistspringboot.controller;

import com.alexrmn.todolistspringboot.exception.BusinessException;
import com.alexrmn.todolistspringboot.model.Task;
import com.alexrmn.todolistspringboot.model.User;
import com.alexrmn.todolistspringboot.model.dto.CreateTaskDto;
import com.alexrmn.todolistspringboot.model.dto.UpdateTaskDto;
import com.alexrmn.todolistspringboot.service.CategoryService;
import com.alexrmn.todolistspringboot.service.TaskService;
import com.alexrmn.todolistspringboot.util.AuthUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;
    private final CategoryService categoryService;

    @GetMapping("/user/{id}")
    public String getTasksByUserId(@PathVariable Integer id, Model model, Authentication authentication) {
        List<Task> usersTasks = taskService.findByUserId(id);
        model.addAttribute("tasks", usersTasks);
        model.addAttribute("user", AuthUtils.getLoggedInUser(authentication));
        return "/tasks/showTasks";
    }



    @GetMapping("{id}")
    public String showEditTaskPage(@PathVariable Integer id, Model model) {
        Task task = taskService.findById(id);
        model.addAttribute("task", new UpdateTaskDto(task));
        model.addAttribute("categories", categoryService.findByUserId(task.getUser().getId()));
        return "/tasks/task";
    }

    @PostMapping("{id}/edit")
    public String editTask(@Valid UpdateTaskDto updateTaskDto, BindingResult bindingResult, Authentication authentication) {
        if (bindingResult.hasErrors()) {
            return "/tasks/taskValidationError";
        }
        taskService.updateTask(updateTaskDto);
        return "redirect:/tasks/user/" + AuthUtils.getLoggedInUser(authentication).getId();
    }

    @DeleteMapping("/{id}/delete")
    public String deleteTask(@PathVariable Integer id, Authentication authentication) {
        taskService.deleteTask(taskService.findById(id));
        return "redirect:/tasks/user/" + AuthUtils.getLoggedInUser(authentication).getId();
    }

    @GetMapping("/new")
    public String showCreateNewTaskPage(Model model, Authentication authentication) {
        User user = AuthUtils.getLoggedInUser(authentication);
        model.addAttribute("task", new Task());
        model.addAttribute("categories", categoryService.findByUserId(user.getId()));
        return "/tasks/createNewTask";
    }

    @PostMapping("/create-new-task")
    public String createNewTask(@Valid CreateTaskDto createTaskDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/tasks/taskValidationError";
        }
        taskService.saveTask(createTaskDto);
        return "redirect:/tasks/user/" + createTaskDto.getUser().getId();
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/show-all-tasks")
    public String showTasks(Model model){
        model.addAttribute("tasks", taskService.findAll());
        return "/tasks/showAllTasks";
    }

}