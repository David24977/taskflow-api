package com.david.taskflow_api.service.serviceImpl;

import com.david.taskflow_api.dto.UpdateUserRequestDto;
import com.david.taskflow_api.dto.UserRequestDto;
import com.david.taskflow_api.dto.UserResponseDto;
import com.david.taskflow_api.mapper.UserMapper;
import com.david.taskflow_api.model.User;
import com.david.taskflow_api.repository.UserRepository;
import com.david.taskflow_api.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;


    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<UserResponseDto> usersList() {
        return userRepository.findAll()
                .stream()
                .map(UserMapper::toMapUserResponse)
                .toList();
    }

    @Override
    public UserResponseDto createUser(UserRequestDto userRequestDto) {
        User user = UserMapper.toUserEntity(userRequestDto);
        User saved = userRepository.save(user);
        return UserMapper.toMapUserResponse(saved);
    }

    @Override
    public UserResponseDto updateUser(UUID id, UpdateUserRequestDto updateUserRequestDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        if(updateUserRequestDto.username() != null){
            user.setUsername(updateUserRequestDto.username());
        }
        if(updateUserRequestDto.password() != null){
            user.setPassword(updateUserRequestDto.password());
        }
        User updated = userRepository.save(user);

        return UserMapper.toMapUserResponse(updated);
    }

    @Override
    public UserResponseDto deleteUser(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("user not found"));

        userRepository.delete(user);
        return UserMapper.toMapUserResponse(user);
    }

    @Override
    public UserResponseDto findById(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(()-> new IllegalStateException("user not found"));
        return UserMapper.toMapUserResponse(user);
    }

    @Override
    public UserResponseDto findByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(()-> new IllegalStateException("user not found"));

        return UserMapper.toMapUserResponse(user);
    }
}
