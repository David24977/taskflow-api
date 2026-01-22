package com.david.taskflow_api.dto;


import jakarta.validation.constraints.Size;

public record UpdateUserRequestDto(
        @Size(min = 3, max = 50)
        String username,
        @Size(min = 8, max = 50)
        String password) {
}
