package com.alexrmn.todolistspringboot.bootstrap;

import com.alexrmn.todolistspringboot.model.Category;
import com.alexrmn.todolistspringboot.model.Task;
import com.alexrmn.todolistspringboot.repository.CategoryRepository;
import com.alexrmn.todolistspringboot.repository.TaskRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class Bootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private final TaskRepository taskRepository;
    private final CategoryRepository categoryRepository;

    public Bootstrap(TaskRepository taskRepository, CategoryRepository categoryRepository) {
        this.taskRepository = taskRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        Category categoryChores = Category.builder().name("Chores").id(1).build();
        Category categoryFitness = Category.builder().name("Fitness").id(2).build();
        Category categoryWork = Category.builder().name("Work").id(3).build();
        Category categoryShopping = Category.builder().name("Shopping").id(4).build();
        Category categoryStudy = Category.builder().name("Study").id(5).build();

        categoryRepository.save(categoryChores);
        categoryRepository.save(categoryFitness);
        categoryRepository.save(categoryShopping);
        categoryRepository.save(categoryWork);
        categoryRepository.save(categoryStudy);

        Task task1 = Task.builder()
                .description("Go to the gym")
                .id(1)
                .deadline(LocalDate.now())
                .completed(false)
                .category(categoryRepository.findByName("Fitness"))
                .build();

        Task task2= Task.builder()
                .description("Take out the trash")
                .id(2)
                .deadline(LocalDate.now())
                .completed(false)
                .category(categoryRepository.findByName("Chores"))
                .build();

        Task task3= Task.builder()
                .description("Write some code")
                .id(3)
                .deadline(LocalDate.now())
                .completed(true)
                .category(categoryRepository.findByName("Study"))
                .build();

        taskRepository.save(task1);
        taskRepository.save(task2);
        taskRepository.save(task3);
    }
}
