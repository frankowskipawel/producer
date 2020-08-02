package org.example.service;

import org.example.exceptions.TaskNotFoundException;
import org.example.model.Task;
import org.example.model.User;
import org.example.model.dto.TaskDTO;
import org.example.repository.TaskRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService{

    @Autowired
    TaskRepository repository;
    private ModelMapper modelMapper = new ModelMapper();

    @Override
    public List<TaskDTO> findAll() {
        return repository.findAll().stream()
                .map(task -> modelMapper.map(task, TaskDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public TaskDTO findById(Integer id) {
        return modelMapper.map(repository.findById(id).orElseThrow(() -> new TaskNotFoundException()), TaskDTO.class);
    }

    @Override
    public TaskDTO create(TaskDTO task) {
        Task newTask  = modelMapper.map(task, Task.class);
        return modelMapper.map(repository.saveAndFlush(newTask), TaskDTO.class);

    }

    @Override
    public TaskDTO findLast() {
        return modelMapper.map(repository.findFirstByOrderByIdDesc().orElseThrow(() -> new TaskNotFoundException()), TaskDTO.class);
    }

    @Override
    @Transactional //nie trzeba robic save
    public TaskDTO update(Integer id, TaskDTO task) {
        Task entity = repository.findById(id).orElseThrow(TaskNotFoundException::new);
        entity.setDescription(task.getDescription());
        entity.setCompleted(task.isCompleted());
        entity.setCompletionPercentage(task.getCompletionPercentage());
        if (task.getUser()!= null){
        entity.setUser(modelMapper.map(task.getUser(), User.class));}
        else {entity.setUser(null);}

        return modelMapper.map(entity, TaskDTO.class);
    }

    @Override
    public void delete(Integer id) {
        repository.delete(repository.findById(id).orElseThrow(TaskNotFoundException::new));
    }

    private Task getOneSafe(Integer id){
        if(repository.existsById(id)){
            return repository.getOne(id);
        } else {
            throw new TaskNotFoundException();
        }
    }
}
