package com.david.taskflow_api.service;

import com.david.taskflow_api.dto.UpdateUserRequestDto;
import com.david.taskflow_api.dto.UserRequestDto;
import com.david.taskflow_api.dto.UserResponseDto;

import java.util.List;
import java.util.UUID;

public interface UserService {
    List<UserResponseDto> usersList();
    UserResponseDto createUser(UserRequestDto userRequestDto);
    UserResponseDto updateUser(UUID id, UpdateUserRequestDto updateUserRequestDto);
    UserResponseDto deleteUser(UUID id);
    UserResponseDto findById(UUID id);
    UserResponseDto findByUsername(String username);
}
