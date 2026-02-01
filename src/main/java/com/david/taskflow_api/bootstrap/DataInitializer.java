package com.david.taskflow_api.bootstrap;

import com.david.taskflow_api.model.Role;
import com.david.taskflow_api.model.User;
import com.david.taskflow_api.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

//Creo esta clase para crear un admin que administre app
@Component
public class DataInitializer {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void init() {
        if (!userRepository.existsByRole(Role.ADMIN)) {

            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("pasword que quieras")); // DEV ONLY
            admin.setRole(Role.ADMIN);
            admin.setEnabled(true);
            admin.setAccountNonLocked(true);

            userRepository.save(admin);

            System.out.println("✔ Admin user created");
        }
    }
}



