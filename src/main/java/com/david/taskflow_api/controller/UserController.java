package com.david.taskflow_api.controller;

import com.david.taskflow_api.dto.UpdateUserRequestDto;
import com.david.taskflow_api.dto.UserRequestDto;
import com.david.taskflow_api.dto.UserResponseDto;
import com.david.taskflow_api.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {

        this.userService = userService;
    }

    @GetMapping
    public List<UserResponseDto> usersList(){
        return userService.usersList();
    }

    @PostMapping
    public UserResponseDto createUser(@Valid @RequestBody UserRequestDto userRequestDto){
        return userService.createUser(userRequestDto);
    }

    @PatchMapping("/{id}")
    public UserResponseDto updateUser(@PathVariable UUID id,
                                      @Valid @RequestBody UpdateUserRequestDto updateUserRequestDto){
        return userService.updateUser(id, updateUserRequestDto);
    }

    @DeleteMapping("/{id}")
    public UserResponseDto deleteUser(@PathVariable UUID id){
        return userService.deleteUser(id);
    }

    @GetMapping("/{id}")
    public UserResponseDto findById(@PathVariable UUID id){
        return userService.findById(id);
    }

    @GetMapping(value = "/username", params = "username")
    public UserResponseDto findByUsername(@RequestParam String username){
        return userService.findByUsername(username);

    }
}
