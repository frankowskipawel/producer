package org.example

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import org.example.contoller.TaskController
import org.example.contoller.UserController
import org.example.exceptions.TaskNotFoundException;
import org.example.model.dto.TaskDTO;
import org.example.model.dto.UserDTO
import org.example.service.TaskService
import org.example.service.UserService
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification
import spock.lang.Unroll;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerSpec extends Specification {

    private static UserDTO usr1 = new UserDTO(1, "Pawel", "frankowski", "frankowski.pawel@gmail.com")
    private static UserDTO usr2 = new UserDTO(2, "Jan", "Kowalski", "kowalski.jan@gmail.com")
    private static UserDTO usr3 = new UserDTO(3, "Zenon", "Martyniuk", "martyniuk.zenon@gmail.com")
    private static List<UserDTO> users = [usr1, usr2]

    @Autowired
    MockMvc mvc
    @Autowired
    UserController userController;
    @Autowired
    UserService userService;

    private ObjectMapper objectMapper = new ObjectMapper()

    def "should return list of Users"() {
        when: "getting values from database by using endpoind /api/users"

        def result = mvc.perform(get("/api/users")).andReturn()
        def values = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<UserDTO>>() {
        })
        then:
        values.containsAll(users)
    }

    @Unroll
    def "should return specific user for task with id: #id"() {

        when:
        def result = mvc.perform(get("/api/users/" + id)).andReturn()
        def value = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<UserDTO>() {})
        then:
        value == user

        when:
        userController.findById(4);
        then:
        def exception = thrown(TaskNotFoundException)
        exception.getMessage() == "Could not find searched Task"

        where:
        id | user
        1  | usr1
        2  | usr2
    }

    def "schould add new User to the database"() {
        given:
        def request = objectMapper.writeValueAsString(usr3)
        when:

        expect:
        mvc.perform(post('/api/users')
                .content(request)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())

        when:
        def userFromDb = userService.findLast()
        usr3.setId(userFromDb.getId())
        then:
        usr3 == userService.findLast();

    }

    def "schould update User in the database"() {
        given:
        def request = objectMapper.writeValueAsString(user)
        when:
        def result = mvc.perform(put('/api/users/' + id)
                .content(request)
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn()

        then:
        result.getResponse().status == 200;
        userController.findById(id) == userFromDatabase

        where:
        id | user                                           | userFromDatabase
        1  | new UserDTO(1, "Olga", "Nowak", "o@wp.pl")     | new UserDTO(1, "Olga", "Nowak", "o@wp.pl")
        2  | new UserDTO(2, "Adam", "Zawada", "ooop@wp.pl") | new UserDTO(2, "Adam", "Zawada", "ooop@wp.pl")
    }

    def "schould delete Task with id id=3"() {
        when:
        def results = mvc.perform(delete("/api/users/3")).andReturn()
        then:
        results.getResponse().status == 200
        when:
        userController.findById(3)
        then:
        def exception = thrown(TaskNotFoundException)
        exception.getMessage() == "Could not find searched Task"

    }
}
