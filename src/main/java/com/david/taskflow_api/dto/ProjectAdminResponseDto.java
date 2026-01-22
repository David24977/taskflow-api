package com.david.taskflow_api.dto;

import com.david.taskflow_api.model.Role;

import java.util.UUID;

public record ProjectAdminResponseDto(
        UUID id,
        String nombre,
        UUID userId,
        String username,
        Role userRole,
        Boolean enabled

) {
}
