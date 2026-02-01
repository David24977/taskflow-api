package com.david.taskflow_api.controller;

import com.david.taskflow_api.dto.ProjectAdminResponseDto;
import com.david.taskflow_api.dto.ProjectRequestDto;
import com.david.taskflow_api.dto.UpdateProjectRequestDto;
import com.david.taskflow_api.security.auth.UserDetailsImpl;
import com.david.taskflow_api.service.ProjectService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public ProjectAdminResponseDto createProject(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                 @Valid @RequestBody ProjectRequestDto projectRequestDto){
        return projectService.createProject(userDetails.getUser(), projectRequestDto);
    }

    @PatchMapping("/{id}")
    public ProjectAdminResponseDto updateProject(@PathVariable UUID id,
                                                 @Valid @RequestBody UpdateProjectRequestDto updateProjectRequestDto,
                                                 @AuthenticationPrincipal UserDetailsImpl userDetails){
        return projectService.updateProject(id, updateProjectRequestDto, userDetails.getUser());
    }

    @PreAuthorize("hasRole('ADMIN')")//Poniendo esto nos ahorramos la lógica del ServiceImpl, para que mire su role
    @DeleteMapping("/{id}")
    public ProjectAdminResponseDto deleteProject(@PathVariable UUID id,
                                                 @AuthenticationPrincipal UserDetailsImpl userDetails){

        return projectService.deleteProject(id, userDetails.getUser());
    }

    @GetMapping("/{id}")
    public ProjectAdminResponseDto findById(@PathVariable UUID id){
        return projectService.findById(id);
    }

    @GetMapping("/user")
    public List<ProjectAdminResponseDto> findProjectsByCurrentUser(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return projectService.findProjectsByCurrentUser(userDetails.getUser());
    }

    @GetMapping("/user/{userId}")
    public List<ProjectAdminResponseDto> findProjectsForUser(@PathVariable UUID userId){
        return projectService.findProjectsForUser(userId);
    }

}
