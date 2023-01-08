package com.alexrmn.todolistspringboot.repository;

import com.alexrmn.todolistspringboot.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Integer> {
    @Query("SELECT c FROM Task c WHERE c.category.id = :id")
    List<Task> findByCategoryId(@Param("id") Integer id);
}
