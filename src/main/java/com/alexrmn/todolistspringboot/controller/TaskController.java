package com.alexrmn.todolistspringboot.controller;

import com.alexrmn.todolistspringboot.config.MyUserDetails;
import com.alexrmn.todolistspringboot.model.Task;
import com.alexrmn.todolistspringboot.model.User;
import com.alexrmn.todolistspringboot.service.CategoryService;
import com.alexrmn.todolistspringboot.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;
    private final CategoryService categoryService;

    public TaskController(TaskService taskService, CategoryService categoryService) {
        this.taskService = taskService;
        this.categoryService = categoryService;
    }

    @GetMapping("/")
    public String showTasks(Model model){
        model.addAttribute("tasks", taskService.findAll());
        return "/tasks/showTasks";
    }

    @GetMapping("/user/{id}")
    public String getTasksByUserId(@PathVariable Integer id, Model model, Authentication authentication) {
        MyUserDetails myUserDetails = (MyUserDetails) authentication.getPrincipal();
        User user = new User(myUserDetails);
        List<Task> usersTasks = taskService.findByUserId(id);
        model.addAttribute("tasks", usersTasks);
        model.addAttribute("user", user);
        return "/tasks/showTasks";
    }

    @GetMapping("{id}")
    public String showEditTaskPage(@PathVariable Integer id, Model model, Authentication authentication) {
        MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
        User user = new User(userDetails);
        model.addAttribute("user", user);
        model.addAttribute("task", taskService.findById(id));
        model.addAttribute("categories", categoryService.findAll());
        return "/tasks/task";
    }

    @PostMapping("{id}/edit")
    public String editTask(@Valid Task updatedTask, BindingResult bindingResult, @PathVariable Integer id, Authentication authentication) {
        if (bindingResult.hasErrors()) {
            return "/tasks/taskValidationError";
        }
        MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
        User user = new User(userDetails);

        Task task = Task.builder()
                        .id(updatedTask.getId())
                        .description(updatedTask.getDescription())
                        .deadline(updatedTask.getDeadline())
                        .completed(updatedTask.isCompleted())
                        .category(updatedTask.getCategory())
                        .user(user)
                        .build();
        taskService.updateTask(task);
        return "redirect:/tasks/user/" + user.getId();
    }

    @DeleteMapping("/{id}/delete")
    public String deleteTask(Model model, @PathVariable Integer id, Authentication authentication) {
        model.addAttribute("task", taskService.findById(id));
        MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
        User user = new User(userDetails);
        taskService.deleteTask(id);
        return "redirect:/tasks/user/" + user.getId();
    }

    @GetMapping("/new")
    public String showCreateNewTaskPage(Model model, Authentication authentication) {
        MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
        User user = new User(userDetails);
        Task task = new Task();
        model.addAttribute("task", task);
        model.addAttribute("categories", categoryService.findByUserId(user.getId()));
        return "/tasks/createNewTask";
    }

    @PostMapping("/create-new-task")
    public String createNewTask(@Valid Task task, BindingResult bindingResult, Authentication authentication) {
        System.out.println(bindingResult);
        if (bindingResult.hasErrors()) {
            return "/tasks/taskValidationError";
        }
        MyUserDetails myUserDetails = (MyUserDetails) authentication.getPrincipal();
        task.setUser(new User((myUserDetails)));
        taskService.saveTask(task);
        return "redirect:/tasks/user/" + task.getUser().getId();
    }
}
