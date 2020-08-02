package org.example.contoller;

import io.swagger.annotations.Api;
import org.example.model.dto.TaskDTO;
import org.example.model.dto.UserDTO;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@Api(description = "Api related to Users")
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    public List<UserDTO> findAll() {return userService.findAll();}

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public UserDTO findById(@PathVariable Integer id) {
        return userService.findById(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    public UserDTO create(@RequestBody UserDTO user){
        return userService.create(user);
    }

    @PutMapping(value = "/{id}")
    public UserDTO update(@PathVariable Integer id,@RequestBody UserDTO dto){
        return userService.update(id, dto);

    }

    @DeleteMapping(value = "/{id}")
    public void delete(@ PathVariable Integer id){
        userService.delete(id);
    }
}
