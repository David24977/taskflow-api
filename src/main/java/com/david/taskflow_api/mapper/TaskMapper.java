package com.david.taskflow_api.mapper;

import com.david.taskflow_api.dto.TaskRequestDto;
import com.david.taskflow_api.dto.TaskResponseDto;
import com.david.taskflow_api.model.Project;
import com.david.taskflow_api.model.Task;

import java.util.UUID;

public class TaskMapper {

    private TaskMapper(){}

    //DTO->ENTITY
    public static Task toTaskEntity(TaskRequestDto taskRequestDto, Project project){

        Task task = new Task();

        task.setTitle(taskRequestDto.title());
        task.setDescription(taskRequestDto.description());
        task.setStatus(taskRequestDto.status());
        task.setProject(project);

        return task;

    }

    //Entity->DTO
    public static TaskResponseDto toMapTaskResponseDto(Task task){

        return new TaskResponseDto(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getProject().getId(),
                task.getCreatedById().getId(),
                task.getCreatedAt()
        );
    }
}
