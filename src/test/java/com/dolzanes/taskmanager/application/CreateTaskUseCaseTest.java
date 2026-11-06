package com.dolzanes.taskmanager.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.dolzanes.taskmanager.application.dto.CreateTaskDto;
import com.dolzanes.taskmanager.domain.TaskStatus;
import com.dolzanes.taskmanager.infrastructure.repository.InMemoryTaskRepository;

class CreateTaskUseCaseTest {

  private InMemoryTaskRepository repository;
  private CreateTaskUseCase useCase;

  @BeforeEach
  void setUp() {
    repository = new InMemoryTaskRepository();
    useCase = new CreateTaskUseCase(repository);
  }

  @Test
  void executeSavesTaskAndReturnsPendingResponse() {
    var response = useCase.execute(new CreateTaskDto("Buy milk", "2 liters"));

    assertThat(response.id()).isNotBlank();
    assertThat(response.title()).isEqualTo("Buy milk");
    assertThat(response.description()).contains("2 liters");
    assertThat(response.status()).isEqualTo(TaskStatus.PENDING.name());
    assertThat(repository.findAll()).singleElement().satisfies(task -> {
      assertThat(task.getId().id().toString()).isEqualTo(response.id());
      assertThat(task.getTitle()).isEqualTo("Buy milk");
      assertThat(task.getDescription()).contains("2 liters");
      assertThat(task.getStatus()).isEqualTo(TaskStatus.PENDING);
    });
  }

  @Test
  void executeTreatsNullDescriptionAsEmpty() {
    var response = useCase.execute(new CreateTaskDto("Buy milk", null));

    assertThat(response.description()).isEmpty();
    assertThat(repository.findAll()).singleElement()
        .satisfies(task -> assertThat(task.getDescription()).isEmpty());
  }

  @Test
  void executeRejectsNullTitle() {
    assertThatThrownBy(() -> useCase.execute(new CreateTaskDto(null, "details")))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Title cannot be null");
  }
}
