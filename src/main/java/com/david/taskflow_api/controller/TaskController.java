package com.david.taskflow_api.controller;

import com.david.taskflow_api.dto.TaskRequestDto;
import com.david.taskflow_api.dto.TaskResponseDto;
import com.david.taskflow_api.dto.UpdateTaskRequestDto;
import com.david.taskflow_api.dto.UserResponseDto;
import com.david.taskflow_api.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public List<TaskResponseDto> tasksList(){
        return taskService.tasksList();
    }

    @PostMapping("/{idProject}")
    public TaskResponseDto createTask(@PathVariable UUID idProject,
                                      @Valid @RequestBody TaskRequestDto taskRequestDto){
        return taskService.createTask(idProject, taskRequestDto);
    }

    @PatchMapping("/{id}")
    public TaskResponseDto updateTask(@PathVariable UUID id,
                                      @Valid @RequestBody UpdateTaskRequestDto updateTaskRequestDto){
        return taskService.updateTask(id, updateTaskRequestDto);

    }

    @DeleteMapping("/{id}")
    public TaskResponseDto delete(@PathVariable UUID id){
        return taskService.delete(id);
    }

    @GetMapping("/{id}")
    public TaskResponseDto findById(@PathVariable UUID id){
        return taskService.findById(id);
    }

    @GetMapping(value = "/title", params = "title")
    public TaskResponseDto findByTitle(@RequestParam String title){
        return taskService.findByTitle(title);
    }

    @GetMapping(value = "/project", params = "projectId")
    public List<TaskResponseDto> findByProjectId(@RequestParam UUID projectId){
        return taskService.findByProjectId(projectId);
    }

    //Devuelve los usuarios que tienen tareas en un proyecto
    @GetMapping(value = "/users", params = "projectId")
    public List<UserResponseDto> findUsersByProjectId(@RequestParam UUID projectId){
        return taskService.findUsersByProjectId(projectId);
    }


}
