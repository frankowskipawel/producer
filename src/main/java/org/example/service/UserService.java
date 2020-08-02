package org.example.service;

import org.example.model.dto.TaskDTO;
import org.example.model.dto.UserDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    List<UserDTO> findAll();
    UserDTO findById(Integer id);
    UserDTO create(UserDTO user);
    UserDTO findLast();
    UserDTO update(Integer id, UserDTO user);
    void delete(Integer id);
}
