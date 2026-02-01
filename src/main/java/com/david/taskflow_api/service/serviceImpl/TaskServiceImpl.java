package com.david.taskflow_api.service.serviceImpl;

import com.david.taskflow_api.dto.TaskRequestDto;
import com.david.taskflow_api.dto.TaskResponseDto;
import com.david.taskflow_api.dto.UpdateTaskRequestDto;
import com.david.taskflow_api.dto.UserResponseDto;
import com.david.taskflow_api.exception.AccessDeniedException;
import com.david.taskflow_api.exception.ResourceNotFoundException;
import com.david.taskflow_api.mapper.TaskMapper;
import com.david.taskflow_api.mapper.UserMapper;
import com.david.taskflow_api.model.Project;
import com.david.taskflow_api.model.Role;
import com.david.taskflow_api.model.Task;
import com.david.taskflow_api.model.User;
import com.david.taskflow_api.repository.ProjectRepository;
import com.david.taskflow_api.repository.TaskRepository;
import com.david.taskflow_api.service.TaskService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;


    public TaskServiceImpl(TaskRepository taskRepository, ProjectRepository projectRepository){
        this.projectRepository = projectRepository;
        this.taskRepository = taskRepository;
    }

    @Override
    public List<TaskResponseDto> tasksList() {
        return taskRepository.findAll()
                .stream()
                .map(TaskMapper::toMapTaskResponseDto)
                .toList();
    }

    @Override
    public TaskResponseDto createTask(UUID idProject, TaskRequestDto taskRequestDto, User currentUser) {

        Project project = projectRepository.findById(idProject)
                .orElseThrow(()-> new ResourceNotFoundException("Project not found"));


        Task task = TaskMapper.toTaskEntity(project, taskRequestDto);

        task.setCreatedById(currentUser);

        Task created = taskRepository.save(task);

        return TaskMapper.toMapTaskResponseDto(created);


    }

    @Override
    public TaskResponseDto updateTask(UUID id, UpdateTaskRequestDto updateTaskRequestDto, User currentUser){

        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));

        boolean isAdmin = currentUser.getRole() == Role.ADMIN;

        boolean isOwner = task.getCreatedById().getId().equals(currentUser.getId());

        if(!isAdmin && !isOwner){
            throw new AccessDeniedException("Not allowed");
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

        Task task = taskRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Task not found"));

        taskRepository.delete(task);

        return TaskMapper.toMapTaskResponseDto(task);
    }

    @Override
    public TaskResponseDto findById(UUID id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Task not found"));
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

    //Hecha por medio de query HQL, devuelve usuarios con tareas de un proyecto
    @Override
    public List<UserResponseDto> findUsersByProjectId(UUID projectId) {
        return taskRepository.findUsersByProjectId(projectId)
                .stream()
                .map(UserMapper::toMapUserResponse)
                .toList();

    }
}
