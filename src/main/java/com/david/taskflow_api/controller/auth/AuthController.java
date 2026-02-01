package com.david.taskflow_api.controller.auth;

import com.david.taskflow_api.dto.AuthLoginRequest;
import com.david.taskflow_api.dto.AuthLoginResponse;
import com.david.taskflow_api.dto.RegisterRequest;
import com.david.taskflow_api.service.auth.AuthService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public void register(@RequestBody RegisterRequest request) {
        authService.register(request);
    }

    @PostMapping("/login")
    public AuthLoginResponse login(
            @RequestBody AuthLoginRequest request
    ) {
        String token = authService.login(
                request.username(),
                request.password()
        );

        return new AuthLoginResponse(token);
    }
}

