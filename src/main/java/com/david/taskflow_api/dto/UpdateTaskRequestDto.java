package com.david.taskflow_api.dto;

import com.david.taskflow_api.model.TaskStatus;
import jakarta.validation.constraints.Size;

public record UpdateTaskRequestDto(
        @Size(max = 40)
        String title,
        String description,
        TaskStatus status
) {
}
