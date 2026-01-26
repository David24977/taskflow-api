package com.david.taskflow_api.service.auth;

import com.david.taskflow_api.model.User;

public interface AuthService {
    /**
     * Devuelve el usuario actualmente autenticado.
     * En la versión DEV, será siempre el admin.
     */
    User getCurrentUser();

    /**
     * Atajo para obtener solo el username actual.
     */
    String getCurrentUsername();

    /**
     * Atajo para saber si el usuario actual es ADMIN.
     */
    boolean isCurrentUserAdmin();
    boolean isCurrentUserGuest();
}
