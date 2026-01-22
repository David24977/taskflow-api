package com.david.taskflow_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ProjectRequestDto(
        @NotBlank
        @Size(min = 3, max = 40)
        String nombre
) {
}
