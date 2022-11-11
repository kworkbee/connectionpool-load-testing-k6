package me.g1tommy.r2dbctodosample.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.g1tommy.r2dbctodosample.domain.dto.TodoDTO;
import me.g1tommy.r2dbctodosample.service.TodoService;
import me.g1tommy.r2dbctodosample.domain.Todo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@WebFluxTest(TodoController.class)
class TodoControllerTest {

    @Autowired
    WebTestClient webTestClient;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
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

        given(todoService.getTodo(anyLong())).willReturn(Mono.just(todo));

        webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path("/todos/{id}").build(id))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo(id)
                .jsonPath("$.title").isEqualTo(title)
                .jsonPath("$.content").isEqualTo(content);
    }

    @Test
    @DisplayName("Creating Todo")
    void testCreatingTodo() throws JsonProcessingException {
        final long id = 1;
        final String title = "title";
        final String content = "content";
        final var now = LocalDateTime.now();
        final var todoDTO = new TodoDTO(title, content);
        final var todo = Todo.builder()
                                .id(id)
                                .title(title)
                                .content(content)
                                .createdDate(now)
                                .lastModifiedDate(now)
                                .build();

        given(todoService.newTodo(any(TodoDTO.class))).willReturn(Mono.just(todo));

        webTestClient.post()
                .uri("/todos")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(objectMapper.writeValueAsString(todoDTO)))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo(id)
                .jsonPath("$.title").isEqualTo(title)
                .jsonPath("$.content").isEqualTo(content);
    }

    @Test
    @DisplayName("Updating Todo ID 1")
    void testUpdatingTodo() {
        final long id = 1;
        final String title = "title";
        final String content = "content";
        final var now = LocalDateTime.now();
        final var todoDTO = new TodoDTO(title, content);
        final var updatedTodo = Todo.builder()
                .id(id)
                .title(title)
                .content(content)
                .createdDate(now)
                .lastModifiedDate(now)
                .build();

        given(todoService.updateTodo(anyLong(), any(TodoDTO.class))).willReturn(Mono.just(updatedTodo));

        webTestClient.put()
                .uri(uriBuilder -> uriBuilder.path("/todos/{id}").build(id))
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(todoDTO))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo(id)
                .jsonPath("$.title").isEqualTo(title)
                .jsonPath("$.content").isEqualTo(content);
    }

    @Test
    @DisplayName("Removing Todo ID 1")
    void testRemovingTodo() {
        final long id = 1;

        given(todoService.removeTodo(anyLong())).willReturn(Mono.empty());

        webTestClient.delete()
                .uri(uriBuilder -> uriBuilder.path("/todos/{id}").build(id))
                .exchange()
                .expectStatus().isOk();

        verify(todoService, times(1)).removeTodo(id);
    }

}