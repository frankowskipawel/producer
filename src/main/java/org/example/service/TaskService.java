package org.example.service;

import org.example.model.dto.TaskDTO;
import org.example.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TaskService  {
    List<TaskDTO> findAll();

}
