package com.david.taskflow_api.service;

import com.david.taskflow_api.dto.ProjectAdminResponseDto;
import com.david.taskflow_api.dto.ProjectRequestDto;
import com.david.taskflow_api.dto.UpdateProjectRequestDto;
import com.david.taskflow_api.model.User;


import java.util.List;
import java.util.UUID;

public interface ProjectService {
    List<ProjectAdminResponseDto> listProject();
    ProjectAdminResponseDto createProject(User currentUser, ProjectRequestDto projectRequestDto);
    ProjectAdminResponseDto updateProject(UUID id, UpdateProjectRequestDto updateProjectRequestDto, User currentUser);
    ProjectAdminResponseDto deleteProject(UUID id, User currentUser);
    ProjectAdminResponseDto findById(UUID id);
    //Como entro logeado con security, no hace falta pasar id del user, ya que nos lo proporciona security
    //Y así buscamos los proyectos de cierto usuario
    List<ProjectAdminResponseDto> findProjectsByCurrentUser(User currentUser);
    //Este si paso id pq es por si el admin quiere ver los proyectos de un user en particular,
    // pongamos que sea un profesor mismo, por eso es necesario este endpoint
    List<ProjectAdminResponseDto> findProjectsForUser(UUID userId);


}
