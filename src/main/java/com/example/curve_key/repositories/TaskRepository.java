package com.example.curve_key.repositories;

import com.example.curve_key.entities.TasksEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<TasksEntity, Integer> {
}
