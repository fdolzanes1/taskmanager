package com.dolzanes.taskmanager.infrastructure.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.dolzanes.taskmanager.domain.Task;
import com.dolzanes.taskmanager.domain.TaskId;

class InMemoryTaskRepositoryTest {

  private InMemoryTaskRepository repository;

  @BeforeEach
  void setUp() {
    repository = new InMemoryTaskRepository();
  }

  @Test
  void saveStoresTaskAndReturnsIt() {
    Task task = new Task("Write tests", Optional.of("cover the repository"));

    Task saved = repository.save(task);

    assertThat(saved).isSameAs(task);
    assertThat(repository.findById(task.getId())).contains(task);
  }

  @Test
  void findByIdReturnsEmptyWhenTaskDoesNotExist() {
    assertThat(repository.findById(new TaskId())).isEmpty();
  }

  @Test
  void findAllReturnsEverySavedTask() {
    Task first = new Task("First", Optional.empty());
    Task second = new Task("Second", Optional.of("details"));

    repository.save(first);
    repository.save(second);

    assertThat(repository.findAll()).containsExactlyInAnyOrder(first, second);
  }

  @Test
  void findAllReturnsEmptyListWhenNothingWasSaved() {
    assertThat(repository.findAll()).isEmpty();
  }

  @Test
  void saveReplacesTaskWithTheSameId() {
    Task original = new Task("Original", Optional.empty());
    repository.save(original);

    Task replacement = new Task("Replacement", Optional.of("updated"));
    replacement.setId(original.getId());
    repository.save(replacement);

    assertThat(repository.findById(original.getId())).contains(replacement);
    assertThat(repository.findAll()).containsExactly(replacement);
  }

  @Test
  void deleteRemovesTheTask() {
    Task task = new Task("To delete", Optional.empty());
    repository.save(task);

    repository.delete(task.getId());

    assertThat(repository.findById(task.getId())).isEmpty();
    assertThat(repository.findAll()).isEmpty();
  }

  @Test
  void deleteIgnoresUnknownId() {
    Task task = new Task("Keep", Optional.empty());
    repository.save(task);

    repository.delete(new TaskId());

    assertThat(repository.findAll()).containsExactly(task);
  }
}
