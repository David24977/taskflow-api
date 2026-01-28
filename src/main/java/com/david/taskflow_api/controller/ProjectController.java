package com.david.taskflow_api.controller;

import com.david.taskflow_api.dto.ProjectAdminResponseDto;
import com.david.taskflow_api.dto.ProjectRequestDto;
import com.david.taskflow_api.dto.UpdateProjectRequestDto;
import com.david.taskflow_api.service.ProjectService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/projects")
public class ProjectController {
    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping
    public List<ProjectAdminResponseDto> listProject(){
        return projectService.listProject();
    }

    @PostMapping
    public ProjectAdminResponseDto createProject(@Valid @RequestBody ProjectRequestDto projectRequestDto){
        return projectService.createProject(projectRequestDto);
    }

    @PatchMapping("/{id}")
    public ProjectAdminResponseDto updateProject(@PathVariable UUID id,
                                                 @Valid @RequestBody UpdateProjectRequestDto updateProjectRequestDto){
        return projectService.updateProject(id, updateProjectRequestDto);
    }

    @DeleteMapping("/{id}")
    public ProjectAdminResponseDto deleteProject(@PathVariable UUID id){
        return projectService.deleteProject(id);
    }

    @GetMapping("/{id}")
    public ProjectAdminResponseDto findById(@PathVariable UUID id){
        return projectService.findById(id);
    }

    @GetMapping("/user")
    public List<ProjectAdminResponseDto> findProjectsByCurrentUser(){
        return projectService.findProjectsByCurrentUser();
    }

    @GetMapping("/user/{id}")
    public List<ProjectAdminResponseDto> findProjectsForUser(@PathVariable UUID userId){
        return projectService.findProjectsForUser(userId);
    }

}
