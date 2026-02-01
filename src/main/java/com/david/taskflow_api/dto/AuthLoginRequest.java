package com.david.taskflow_api.dto;

public record AuthLoginRequest(
        String username,
        String password
) { }
