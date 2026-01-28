package com.david.taskflow_api.repository;
import com.david.taskflow_api.model.Task;
import com.david.taskflow_api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TaskRepository extends JpaRepository<Task, UUID> {

    Optional<Task> findByTitle(String title);

    List<Task> findByProjectId(UUID projectId);
//Esta query le dice a Data: No intentes generar la query por el nombre del método.
//Usa esta query explícita.
    //Lo utilizo porque quiero consultar Task, pero que me devuelva User
    //Mostrar los usuarios con tareas en un determinado proyecto
    @Query("""
    select distinct t.createdBy
    from Task t
    where t.project.id = :projectId
""")
    List<User> findUsersByProjectId(UUID projectId);

}
