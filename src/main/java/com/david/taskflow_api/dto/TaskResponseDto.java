package com.david.taskflow_api.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record TaskResponseDto(
        UUID id,
        String title,
        String description,
        UUID projectId,
        UUID createdById,
        LocalDateTime createdAt
) {
}
