package com.dolzanes.taskmanager.infrastructure.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.dolzanes.taskmanager.domain.Task;
import com.dolzanes.taskmanager.domain.TaskId;
import com.dolzanes.taskmanager.domain.TaskRepository;

public class InMemoryTaskRepository implements TaskRepository {

  private final Map<TaskId, Task> storage = new HashMap<>();

  @Override
  public Task save(Task task) {
    storage.put(task.getId(), task);
    return task;
  }

  @Override
  public List<Task> findAll() {
    return new ArrayList<>(storage.values());
  }

  @Override
  public Optional<Task> findById(TaskId id) {
    return Optional.ofNullable(storage.get(id));
  }

  @Override
  public void delete(TaskId id) {
    storage.remove(id);
  }
}
