package org.example.repository;

import org.example.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {

    Optional<Task> findFirstByOrderByIdDesc();

}
