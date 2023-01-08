package com.alexrmn.todolistspringboot.controller;

import com.alexrmn.todolistspringboot.model.Task;
import com.alexrmn.todolistspringboot.service.CategoryService;
import com.alexrmn.todolistspringboot.service.TaskService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
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
        return "showTasks";
    }

    @GetMapping("{id}")
    public String showAndEditTask(@PathVariable Integer id, Model model) {
        model.addAttribute("task", taskService.findById(id));
        model.addAttribute("categories", categoryService.findAll());
        return "task";
    }

    @PostMapping("{id}/edit")
    public String editTask(@ModelAttribute Task updatedTask, @PathVariable Integer id) {
        Task task = Task.builder()
                        .id(updatedTask.getId())
                        .description(updatedTask.getDescription())
                        .deadline(updatedTask.getDeadline())
                        .completed(updatedTask.isCompleted())
                        .category(updatedTask.getCategory())
                        .build();
        taskService.updateTask(task);
        return "redirect:/";
    }

    @DeleteMapping("/{id}")
    public String deleteTask(Model model, @PathVariable Integer id) {
        model.addAttribute("task", taskService.findById(id));
        taskService.deleteTask(id);
        return "redirect:/";
    }

    @GetMapping("/new")
    public String showCreateNewTaskPage(Model model) {
        Task task = new Task();
        model.addAttribute("task", task);
        model.addAttribute("categories", categoryService.findAll());
        return "createNewTask";
    }

    @PostMapping("/new")
    public String createNewTask(Model model, @ModelAttribute Task task) {
        taskService.saveTask(task);
        return "redirect:/";
    }
}
