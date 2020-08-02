package org.example;


import org.example.model.Task;
import org.example.model.User;
import org.example.model.dto.UserDTO;
import org.example.repository.TaskRepository;
import org.example.repository.UserRepository;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class ProducerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProducerApplication.class, args);
    }


    @Component
    @Order(0)
    class MyApplicationListener implements ApplicationListener<ApplicationReadyEvent> {
        @Autowired
        TaskRepository repository;
        @Autowired
        UserService userService;

        @Override
        public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
            repository.save(new Task("test", true, null, null));
            repository.save(new Task("test2", false, 20, new User("Jan", "Kowalski", "jk@wp.pl")));
            UserDTO usr1 = new UserDTO(1, "Pawel", "frankowski", "frankowski.pawel@gmail.com");
            UserDTO usr2 = new UserDTO(2, "Jan", "Kowalski", "kowalski.jan@gmail.com");
            UserDTO usr3 = new UserDTO(3, "Zenon", "Martyniuk", "martyniuk.zenon@gmail.com");

            userService.create(usr1);
            userService.create(usr2);
            userService.create(usr3);
        }
    }
}
