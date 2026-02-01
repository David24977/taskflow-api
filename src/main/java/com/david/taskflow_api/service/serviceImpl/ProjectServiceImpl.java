package com.david.taskflow_api.service.serviceImpl;

import com.david.taskflow_api.dto.ProjectAdminResponseDto;
import com.david.taskflow_api.dto.ProjectRequestDto;
import com.david.taskflow_api.dto.UpdateProjectRequestDto;
import com.david.taskflow_api.exception.AccessDeniedException;
import com.david.taskflow_api.exception.ResourceNotFoundException;
import com.david.taskflow_api.mapper.ProjectMapper;
import com.david.taskflow_api.model.Project;
import com.david.taskflow_api.model.Role;
import com.david.taskflow_api.model.User;
import com.david.taskflow_api.repository.ProjectRepository;
import com.david.taskflow_api.service.ProjectService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;

    public ProjectServiceImpl(ProjectRepository projectRepository) {

        this.projectRepository = projectRepository;
    }

    @Override
    public ProjectAdminResponseDto createProject(User currentUser, ProjectRequestDto projectRequestDto) {
        if (currentUser.getRole() == Role.GUEST) {
            throw new AccessDeniedException("Not allowed");
        }

        Project project = ProjectMapper.toProjectEntity(projectRequestDto, currentUser);
        Project created = projectRepository.save(project);

        return ProjectMapper.toProjectAdminResponseDto(created);
    }

    @Override
    public ProjectAdminResponseDto updateProject(
            UUID id,
            UpdateProjectRequestDto dto,
            User currentUser
    ) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("project not found"));

        boolean isOwner = project.getOwner().getId().equals(currentUser.getId());
        boolean isAdmin = currentUser.getRole() == Role.ADMIN;

        if (!isOwner && !isAdmin) {
            throw new AccessDeniedException("Not allowed");
        }

        if (dto.nombre() != null) {
            project.setNombre(dto.nombre());
        }

        Project update = projectRepository.save(project);

        return ProjectMapper.toProjectAdminResponseDto(update);
    }


    @Override
    public List<ProjectAdminResponseDto> listProject() {
        return projectRepository.findAll()
                .stream()
                .map(ProjectMapper::toProjectAdminResponseDto)
                .toList();
    }

    @Override
    public ProjectAdminResponseDto deleteProject(UUID id, User currentUser) {
        boolean isAdmin = currentUser.getRole() == Role.ADMIN;
        if (!isAdmin) {
            throw new AccessDeniedException("Not allowed");
        }

        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("project not found"));
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
    public List<ProjectAdminResponseDto> findProjectsByCurrentUser(User currentUser) {
        UUID ownerId = currentUser.getId();

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

