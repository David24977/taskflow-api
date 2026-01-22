package com.david.taskflow_api.dto;

import com.david.taskflow_api.model.Role;

import java.util.UUID;

public record UserResponseDto(
        UUID id, String username, Role role
) {
}
