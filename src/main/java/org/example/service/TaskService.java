package org.example.service;

import org.example.model.dto.TaskDTO;
import org.example.repository.TaskRepository;
import org.springframework.scheduling.config.Task;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TaskService  {
    List<TaskDTO> findAll();
    TaskDTO findById(Integer id);
    TaskDTO create(TaskDTO task);
    TaskDTO findLast();
    TaskDTO update(Integer id, TaskDTO task);
    void delete(Integer id);


}
