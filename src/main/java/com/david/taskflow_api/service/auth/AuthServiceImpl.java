package com.david.taskflow_api.service.auth;

import com.david.taskflow_api.dto.RegisterRequest;
import com.david.taskflow_api.model.Role;
import com.david.taskflow_api.model.User;
import com.david.taskflow_api.repository.UserRepository;
import com.david.taskflow_api.security.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public String login(String username, String password) {

        // 1️. Autenticar credenciales
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );

        // 2. Cargar usuario autenticado
        UserDetails userDetails =
                userDetailsService.loadUserByUsername(username);

        // 3. Generar JWT
        return jwtService.generateToken(userDetails);
    }

    @Override
    public void register(RegisterRequest request) {

        // 1️⃣ ¿Existe ya el usuario?
        if (userRepository.existsByUsername(request.username())) {
            throw new IllegalStateException("User already exists");
        }

        // 2️⃣ Crear entidad User
        User user = new User();
        user.setUsername(request.username());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setRole(Role.USER);
        user.setEnabled(true);

        // 3️⃣ Guardar en BD
        userRepository.save(user);
    }
}
