package com.david.taskflow_api.repository;

import com.david.taskflow_api.model.Project;
import com.david.taskflow_api.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TaskRepository extends JpaRepository<Task, UUID> {

    Optional<Task> findByTitle(String title);

    List<Task> findByProject(Project project);
}
