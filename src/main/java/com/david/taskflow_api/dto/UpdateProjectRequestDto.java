package com.david.taskflow_api.dto;

import jakarta.validation.constraints.Size;

public record UpdateProjectRequestDto(
        @Size(max = 50)
        String nombre
) {
}
