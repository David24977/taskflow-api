package com.david.taskflow_api.dto;

import com.david.taskflow_api.model.TaskStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


public record TaskRequestDto(
        @NotBlank
        @Size(min = 3, max = 40)
        String title,
        @NotBlank
        String description,
        @NotNull
        TaskStatus status
) {
}
