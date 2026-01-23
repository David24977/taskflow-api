package com.david.taskflow_api.mapper;

import com.david.taskflow_api.dto.UserRequestDto;
import com.david.taskflow_api.dto.UserResponseDto;
import com.david.taskflow_api.model.User;

public class UserMapper {

    private UserMapper(){} //Evita que se instancie

    //DTO->ENTITY

    public static User toUserEntity(UserRequestDto userRequestDto){

        User user = new User();

        user.setUsername(userRequestDto.username());
        user.setPassword(userRequestDto.password());
        return user;
    }

    public static UserResponseDto toMapUserResponse(User user){
        return new UserResponseDto(
                user.getId(),
                user.getUsername(),
                user.getRole());
    }
}
