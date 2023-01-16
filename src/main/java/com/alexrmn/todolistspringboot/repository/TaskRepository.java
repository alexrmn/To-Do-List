package com.alexrmn.todolistspringboot.repository;

import com.alexrmn.todolistspringboot.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Integer> {

    List<Task> findByCategoryId(Integer id);

    List<Task> findByUserId(Integer id);
}
