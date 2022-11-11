package me.g1tommy.r2dbctodosample.service;

import me.g1tommy.r2dbctodosample.domain.Todo;
import me.g1tommy.r2dbctodosample.domain.TodoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

@ExtendWith(SpringExtension.class)
class TodoServiceTest {

    @Mock
    TodoRepository todoRepository;

    @InjectMocks
    TodoService todoService;

    @Test
    @DisplayName("Getting Todo ID 1")
    void testGettingTodo() {
        final long id = 1;
        final String title = "title";
        final String content = "content";
        final var now = LocalDateTime.now();
        final var todo = Todo.builder()
                .id(id)
                .title(title)
                .content(content)
                .createdDate(now)
                .lastModifiedDate(now)
                .build();

        given(todoRepository.findById(anyLong())).willReturn(Mono.just(todo));

        todoService.getTodo(id)
                .as(StepVerifier::create)
                .expectNext(todo)
                .verifyComplete();
    }

    @Test
    @DisplayName("Creating Todo")
    void testCreatingTodo() {
        final long id = 1;
        final String title = "title";
        final String content = "content";
        final var now = LocalDateTime.now();
        final var todo = Todo.builder()
                .id(id)
                .title(title)
                .content(content)
                .createdDate(now)
                .lastModifiedDate(now)
                .build();

        given(todoRepository.findById(anyLong())).willReturn(Mono.just(todo));

        todoService.getTodo(id)
                .as(StepVerifier::create)
                .expectNext(todo)
                .verifyComplete();

    }

    @Test
    @DisplayName("Updating Todo ID 1")
    void testUpdatingTodo() {
        final long id = 1;
        final String title = "title";
        final String content = "content";
        final var now = LocalDateTime.now();
        final var todo = Todo.builder()
                .id(id)
                .title(title)
                .content(content)
                .createdDate(now)
                .lastModifiedDate(now)
                .build();

        given(todoRepository.findById(anyLong())).willReturn(Mono.just(todo));

        todoService.getTodo(id)
                .as(StepVerifier::create)
                .expectNext(todo)
                .verifyComplete();

    }

    @Test
    @DisplayName("Removing Todo ID 1")
    void testRemovingTodo() {
        final long id = 1;

        given(todoRepository.deleteById(anyLong())).willReturn(Mono.empty());

        todoService.removeTodo(id)
                .as(StepVerifier::create)
                .expectNextCount(0)
                .verifyComplete();

    }
}