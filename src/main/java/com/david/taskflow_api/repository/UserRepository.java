package com.david.taskflow_api.repository;

import com.david.taskflow_api.model.Role;
import com.david.taskflow_api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByUsername(String username);


    boolean existsByUsername(String username);

    //Método para adminInitializer
    boolean existsByRole(Role role);




}
