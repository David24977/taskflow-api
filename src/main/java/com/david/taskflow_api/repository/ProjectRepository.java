package com.david.taskflow_api.repository;

import com.david.taskflow_api.model.Project;
import com.david.taskflow_api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProjectRepository extends JpaRepository<Project, UUID> {

    Optional<Project> findByNombre(String nombre);
    List<Project> findByOwner(User owner);

}
