package com.david.taskflow_api.service.auth;

import com.david.taskflow_api.model.Role;
import com.david.taskflow_api.model.User;
import com.david.taskflow_api.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    // Puedes cambiar este nombre por el que vayas a usar como admin
    private static final String DEFAULT_ADMIN_USERNAME = "admin";

    public AuthServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getCurrentUser() {
        return userRepository.findByUsername(DEFAULT_ADMIN_USERNAME)
                .orElseThrow(() -> new IllegalStateException(
                        "Usuario admin por defecto no encontrado. " +
                                "Asegúrate de crearlo al arrancar la aplicación."
                ));
    }

    //Evita que en todos lados tengas que hacer: authService.getCurrentUser().getUsername(); Es un helper
    @Override
    public String getCurrentUsername() {

        return getCurrentUser().getUsername();
    }
    //Azúcar sintáctico + seguridad semántica
    //Evita cosas como:
    //if (authService.getCurrentUser().getRole() == Role.ADMIN) { ... }
    //Y te permite escribir:if (authService.isCurrentUserAdmin()) { ... }
    @Override
    public boolean isCurrentUserAdmin() {

        return getCurrentUser().getRole() == Role.ADMIN;
    }

    @Override
    public boolean isCurrentUserGuest() {
        return getCurrentUser().getRole() == Role.GUEST;
    }
}