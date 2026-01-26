package com.david.taskflow_api.service.serviceImpl;

import com.david.taskflow_api.dto.TaskRequestDto;
import com.david.taskflow_api.dto.TaskResponseDto;
import com.david.taskflow_api.dto.UpdateTaskRequestDto;
import com.david.taskflow_api.dto.UserResponseDto;
import com.david.taskflow_api.mapper.TaskMapper;
import com.david.taskflow_api.mapper.UserMapper;
import com.david.taskflow_api.model.Project;
import com.david.taskflow_api.model.Task;
import com.david.taskflow_api.model.User;
import com.david.taskflow_api.repository.ProjectRepository;
import com.david.taskflow_api.repository.TaskRepository;
import com.david.taskflow_api.service.TaskService;
import com.david.taskflow_api.service.auth.AuthService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final AuthService authService;
    private final ProjectRepository projectRepository;


    public TaskServiceImpl(TaskRepository taskRepository, AuthService authService, ProjectRepository projectRepository){
        this.authService = authService;
        this.projectRepository = projectRepository;
        this.taskRepository = taskRepository;
    }

    @Override
    public List<TaskResponseDto> tasksList() {
        if (!authService.isCurrentUserAdmin()) {
            throw new IllegalStateException("Not allowed");
        }
        return taskRepository.findAll()
                .stream()
                .map(TaskMapper::toMapTaskResponseDto)
                .toList();
    }

    @Override
    public TaskResponseDto createTask(UUID idProject, TaskRequestDto taskRequestDto) {
        User user = authService.getCurrentUser();
        Project project = projectRepository.findById(idProject)
                .orElseThrow(()-> new IllegalStateException("Project not found"));
    //Para ver si tiene permisos de userPropietario o admin para poder crear
        boolean isAdmin = authService.isCurrentUserAdmin();
        boolean isOwner = project.getOwner().getId().equals(user.getId());
        boolean isGuest = authService.isCurrentUserGuest();

        if (!isAdmin && !isOwner && !isGuest) {
            throw new IllegalStateException("Not allowed");
        }


        Task task = TaskMapper.toTaskEntity(taskRequestDto, project);

        task.setCreatedById(user);

        Task created = taskRepository.save(task);

        return TaskMapper.toMapTaskResponseDto(created);


    }

    @Override
    public TaskResponseDto updateTask(UUID id, UpdateTaskRequestDto updateTaskRequestDto) {

        User user = authService.getCurrentUser();
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Task not found"));

        //Admin y owner puede actualizar
        boolean isAdmin = authService.isCurrentUserAdmin();
        boolean isOwner = task.getProject().getOwner().getId().equals(user.getId());


        if(!isOwner && !isAdmin){
            throw new IllegalStateException("Not allowed");
        }

        if(updateTaskRequestDto.title() != null){
            task.setTitle(updateTaskRequestDto.title());
        }

        if(updateTaskRequestDto.description() != null){
            task.setDescription(updateTaskRequestDto.description());
        }

        if(updateTaskRequestDto.status() != null){
            task.setStatus(updateTaskRequestDto.status());
        }

        Task updated = taskRepository.save(task);

        return TaskMapper.toMapTaskResponseDto(updated);
    }

    @Override
    public TaskResponseDto delete(UUID id) {
        User user = authService.getCurrentUser();

        //Solo admin puede actualizar
        boolean isAdmin = authService.isCurrentUserAdmin();

        if (!isAdmin) {
            throw new IllegalStateException("Not allowed");
        }

        Task task = taskRepository.findById(id)
                .orElseThrow(()-> new IllegalStateException("Task not found"));

        taskRepository.delete(task);

        return TaskMapper.toMapTaskResponseDto(task);
    }

    @Override
    public TaskResponseDto findByTitle(String title) {
        Task task = taskRepository.findByTitle(title)
                .orElseThrow(()-> new IllegalStateException("Task not found"));

        return TaskMapper.toMapTaskResponseDto(task);
    }

    @Override
    public List<TaskResponseDto> findByProjectId(UUID projectId) {
        return taskRepository.findByProjectId(projectId)
                .stream()
                .map(TaskMapper::toMapTaskResponseDto)
                .toList();
    }

    @Override
    public List<UserResponseDto> findUsersByProjectId(UUID projectId) {
        if (!authService.isCurrentUserAdmin()) {
            throw new IllegalStateException("Not allowed");
        }
        return taskRepository.findUsersByProjectId(projectId)
                .stream()
                .map(UserMapper::toMapUserResponse)
                .toList();

    }
}
