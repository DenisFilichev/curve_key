package com.example.curve_key.servicies;

import com.example.curve_key.entities.TasksEntity;
import com.example.curve_key.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    private final TaskRepository repository;

    @Autowired
    public TaskService(TaskRepository repository) {
        this.repository = repository;
    }

    public TasksEntity save(TasksEntity task){
        return repository.save(task);
    }

    public List<TasksEntity> findAll(){
        return repository.findAll();
    }
}
