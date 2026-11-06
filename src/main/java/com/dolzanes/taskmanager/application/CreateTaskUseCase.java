package com.dolzanes.taskmanager.application;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.dolzanes.taskmanager.application.dto.CreateTaskDto;
import com.dolzanes.taskmanager.application.dto.ResponseTaskDto;
import com.dolzanes.taskmanager.domain.Task;
import com.dolzanes.taskmanager.domain.TaskRepository;

@Service 
public class CreateTaskUseCase {
  private final TaskRepository taskRepository;

  public CreateTaskUseCase(TaskRepository taskRepository) {
    this.taskRepository = taskRepository;
  }

  public ResponseTaskDto execute(CreateTaskDto createTaskDto) {
    var description = Optional.ofNullable(createTaskDto.description());
    var task = new Task(createTaskDto.title(), description);
    var saved = taskRepository.save(task);
    return ResponseTaskDto.from(saved);
  }
}
