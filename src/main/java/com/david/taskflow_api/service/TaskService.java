package com.david.taskflow_api.service;

import com.david.taskflow_api.dto.TaskRequestDto;
import com.david.taskflow_api.dto.TaskResponseDto;
import com.david.taskflow_api.dto.UpdateTaskRequestDto;
import com.david.taskflow_api.dto.UserResponseDto;


import java.util.List;
import java.util.UUID;

public interface TaskService {

    List<TaskResponseDto> tasksList();
    TaskResponseDto createTask(UUID projectId, TaskRequestDto taskRequestDto);
    TaskResponseDto updateTask(UUID id, UpdateTaskRequestDto updateTaskRequestDto);
    TaskResponseDto delete(UUID id);
    TaskResponseDto findByTitle(String title);
    List<TaskResponseDto> findByProjectId(UUID id);
    List<UserResponseDto> findUsersByProjectId(UUID projectId);
}
