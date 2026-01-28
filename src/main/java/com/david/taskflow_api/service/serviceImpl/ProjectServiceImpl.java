package com.david.taskflow_api.service.serviceImpl;

import com.david.taskflow_api.dto.ProjectAdminResponseDto;
import com.david.taskflow_api.dto.ProjectRequestDto;
import com.david.taskflow_api.dto.UpdateProjectRequestDto;
import com.david.taskflow_api.exception.AccessDeniedException;
import com.david.taskflow_api.exception.ResourceNotFoundException;
import com.david.taskflow_api.mapper.ProjectMapper;
import com.david.taskflow_api.model.Project;
import com.david.taskflow_api.model.User;
import com.david.taskflow_api.repository.ProjectRepository;
import com.david.taskflow_api.service.ProjectService;
import com.david.taskflow_api.service.auth.AuthService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;
    private final AuthService authService;

    public ProjectServiceImpl(ProjectRepository projectRepository, AuthService authService) {
        this.projectRepository = projectRepository;
        this.authService = authService;
    }

    @Override
    public List<ProjectAdminResponseDto> listProject() {
        return projectRepository.findAll()
                .stream()
                .map(ProjectMapper::toProjectAdminResponseDto)
                .toList();
    }

    @Override
    public ProjectAdminResponseDto createProject(ProjectRequestDto projectRequestDto) {
        User owner = authService.getCurrentUser();

        Project project = ProjectMapper.toProjectEntity(projectRequestDto, owner);

        if (authService.isCurrentUserGuest()) {
            throw new AccessDeniedException("Not allowed");
        }
        Project created = projectRepository.save(project);
        return ProjectMapper.toProjectAdminResponseDto(created);
    }

    @Override
    public ProjectAdminResponseDto updateProject(UUID id, UpdateProjectRequestDto updateProjectRequestDto) {
        User owner = authService.getCurrentUser();

        Project project = projectRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("project not found"));

        boolean isAdmin = authService.isCurrentUserAdmin();
        boolean isOwner = project.getOwner().getId().equals(owner.getId());

        if(!isOwner && !isAdmin){
            throw new AccessDeniedException("Not allowed");
        }

        if(updateProjectRequestDto.nombre() != null){
            project.setNombre(updateProjectRequestDto.nombre());
        }

        Project update = projectRepository.save(project);
        return ProjectMapper.toProjectAdminResponseDto(update);
    }

    @Override
    public ProjectAdminResponseDto deleteProject(UUID id) {
        boolean isAdmin = authService.isCurrentUserAdmin();
        if(!isAdmin){
            throw new AccessDeniedException("Not allowed");
        }

        Project project = projectRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("project not found"));
        projectRepository.delete(project);
        return ProjectMapper.toProjectAdminResponseDto(project);
    }

    @Override
    public ProjectAdminResponseDto findById(UUID id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("project not found"));
        return ProjectMapper.toProjectAdminResponseDto(project);
    }

    //Este método es para el user, que entra con su username y contraseña
    @Override
    public List<ProjectAdminResponseDto> findProjectsByCurrentUser() {
        UUID ownerId = authService.getCurrentUser().getId();

        return projectRepository.findByOwnerId(ownerId)
                .stream()
                .map(ProjectMapper::toProjectAdminResponseDto)
                .toList();


    }

    //Método para el admin, para poder ver proyectos de usuarios por su id
    @Override
    public List<ProjectAdminResponseDto> findProjectsForUser(UUID userId) {
        return projectRepository.findByOwnerId(userId)
                .stream()
                .map(ProjectMapper::toProjectAdminResponseDto)
                .toList();
    }
}
