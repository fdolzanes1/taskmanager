package com.dolzanes.taskmanager.application.dto;

import java.util.Optional;

import org.springframework.context.annotation.Description;

import com.dolzanes.taskmanager.domain.Task;

public record ResponseTaskDto(
  String id,
  String title,
  Optional<String> description,
  String status
) {

  public static ResponseTaskDto from(Task task) {
    return new ResponseTaskDto(
      task.getId().id().toString(), 
      task.getTitle(), 
      task.getDescription(), 
      task.getStatus().name()
    );
  }
}
