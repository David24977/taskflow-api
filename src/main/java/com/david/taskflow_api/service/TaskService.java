package com.david.taskflow_api.service;

import com.david.taskflow_api.dto.TaskRequestDto;
import com.david.taskflow_api.dto.TaskResponseDto;
import com.david.taskflow_api.dto.UpdateTaskRequestDto;
import com.david.taskflow_api.dto.UserResponseDto;
import com.david.taskflow_api.model.User;


import java.util.List;
import java.util.UUID;

public interface TaskService {

    List<TaskResponseDto> tasksList();
    TaskResponseDto createTask(UUID projectId, TaskRequestDto taskRequestDto, User currentUser);
    TaskResponseDto updateTask(UUID id, UpdateTaskRequestDto updateTaskRequestDto, User currentUser);
    TaskResponseDto delete(UUID id);
    TaskResponseDto findByTitle(String title);
    TaskResponseDto findById(UUID id);
    List<TaskResponseDto> findByProjectId(UUID id);
    List<UserResponseDto> findUsersByProjectId(UUID projectId);
}
