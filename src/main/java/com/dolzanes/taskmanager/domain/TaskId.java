package com.dolzanes.taskmanager.domain;

import java.util.UUID;

import org.springframework.util.Assert;

public record TaskId(UUID id) {
  public TaskId {
    Assert.notNull(id, "Task ID cannot be null");
  }

  public TaskId() {
    this(UUID.randomUUID());
  }
  
}
