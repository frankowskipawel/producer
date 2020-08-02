package org.example.service;

import org.example.exceptions.TaskNotFoundException;
import org.example.model.Task;
import org.example.model.User;
import org.example.model.dto.TaskDTO;
import org.example.model.dto.UserDTO;
import org.example.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserRepository repository;

    private ModelMapper modelMapper = new ModelMapper();

    @Override
    public List<UserDTO> findAll() {
        return repository.findAll().stream()
                .map(user -> modelMapper.map(user, UserDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO findById(Integer id) {
        return modelMapper.map(repository.findById(id).orElseThrow(() -> new TaskNotFoundException()), UserDTO.class);

    }

    @Override
    public UserDTO create(UserDTO task) {
        User newUser  = modelMapper.map(task, User.class);
        return modelMapper.map(repository.saveAndFlush(newUser), UserDTO.class);
    }



    @Override
    public UserDTO findLast() {
        return modelMapper.map(repository.findFirstByOrderByIdDesc().orElseThrow(() -> new TaskNotFoundException()), UserDTO.class);

    }

    @Override
    @Transactional //nie trzeba robic save
    public UserDTO update(Integer id, UserDTO user) {
        User entity = repository.findById(id).orElseThrow(TaskNotFoundException::new);
        entity.setFirstName(user.getFirstName());
        entity.setLastName(user.getLastName());
        entity.setEmail(user.getEmail());

        return modelMapper.map(entity, UserDTO.class);
    }

    @Override
    public void delete(Integer id) {
        repository.delete(repository.findById(id).orElseThrow(TaskNotFoundException::new));

    }
}
