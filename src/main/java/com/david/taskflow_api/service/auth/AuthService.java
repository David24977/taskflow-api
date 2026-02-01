package com.david.taskflow_api.service.auth;

import com.david.taskflow_api.dto.RegisterRequest;

public interface AuthService {
    /**
     * Autentica al usuario y devuelve un JWT.
     */
    String login(String username, String password);

    void register(RegisterRequest request);
}
