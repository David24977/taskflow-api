package com.david.taskflow_api.config;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "Task Flow API",
                version = "1.0.0",
                description = "Spring Boot REST API for managing projects and " +
                        "tasks with role-based access and JWT authentication"
        )
)

public class OpenApiConfig {
}
