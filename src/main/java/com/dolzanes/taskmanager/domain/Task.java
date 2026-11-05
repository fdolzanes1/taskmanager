package com.dolzanes.taskmanager.domain;

import java.util.Optional;

import org.springframework.util.Assert;

import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter 
public class Task {
  private TaskId id;
  private String title;
  private Optional<String> description;
  private TaskStatus status;

  public Task(String title, Optional<String> description) {

    Assert.notNull(title, "Title cannot be null");

    this.id = new TaskId();
    this.title = title;
    this.description = description;
    this.status = TaskStatus.PENDING;
  }
}