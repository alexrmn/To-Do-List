package com.alexrmn.todolistspringboot.repository;

import com.alexrmn.todolistspringboot.model.Task;
import org.springframework.data.repository.CrudRepository;

public interface TaskRepository extends CrudRepository<Task, Integer> {
}
