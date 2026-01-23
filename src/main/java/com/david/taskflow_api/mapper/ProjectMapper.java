package com.david.taskflow_api.mapper;

import com.david.taskflow_api.dto.ProjectAdminResponseDto;
import com.david.taskflow_api.dto.ProjectRequestDto;
import com.david.taskflow_api.model.Project;
import com.david.taskflow_api.model.User;


public class ProjectMapper {
    private ProjectMapper(){}

    //DTO->ENTITY
    public static Project toProjectEntity(ProjectRequestDto projectRequestDto, User owner){

        Project project = new Project();

        project.setNombre(projectRequestDto.nombre());
        project.setOwner(owner);

        return project;
    }

    //ENTITY->DTO

    public static ProjectAdminResponseDto toProjectAdminResponseDto(Project project){

      return   new ProjectAdminResponseDto(
                project.getId(),
                project.getNombre(),
                project.getOwner().getId(),
                project.getOwner().getUsername(),
                project.getOwner().getRole(),
                project.getOwner().getEnabled()
        );
    }




}
