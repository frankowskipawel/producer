package org.example.contoller;

import io.swagger.annotations.Api;
import org.example.model.dto.TaskDTO;
import org.example.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@Api(description = "Api related to Tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @RequestMapping(method = RequestMethod.GET)
    public List<TaskDTO> findAll() {return taskService.findAll();}


    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public TaskDTO findById(@PathVariable Integer id) {
        return taskService.findById(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    public TaskDTO create(@RequestBody TaskDTO task){
        return taskService.create(task);
    }
}
