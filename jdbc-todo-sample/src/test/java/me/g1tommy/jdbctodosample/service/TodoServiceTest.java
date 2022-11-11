package me.g1tommy.jdbctodosample.service;

import me.g1tommy.jdbctodosample.domain.Todo;
import me.g1tommy.jdbctodosample.domain.TodoRepository;
import me.g1tommy.jdbctodosample.domain.dto.TodoDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Optional;

import static java.time.LocalDateTime.now;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@SpringBootTest
class TodoServiceTest {

    @Mock
    private TodoRepository todoRepository;

    @InjectMocks
    private TodoService todoService;

    @Test
    @DisplayName("Get Todo Entity")
    void test_getTodo() {
        final long id = 1;
        final String title = "title";
        final String content = "content";
        final LocalDateTime now = now();
        final Todo todo = Todo.builder()
                .id(id)
                .title(title)
                .content(content)
                .createdDate(now)
                .lastModifiedDate(now)
                .build();

        given(todoRepository.findById(anyLong())).willReturn(Optional.of(todo));

        assertThat(todoService.getTodo(id)).isEqualTo(todo);
    }

    @Test
    @DisplayName("Create Todo Entity")
    void test_newTodo() {
        final long id = 1;
        final String title = "title";
        final String content = "content";
        final var now = now();
        final var todoDTO = new TodoDTO(title, content);
        final var todo = Todo.builder()
                .id(id)
                .title(title)
                .content(content)
                .createdDate(now)
                .lastModifiedDate(now)
                .build();

        given(todoRepository.save(any(Todo.class))).willReturn(todo);

        assertThat(todoService.newTodo(todoDTO)).isEqualTo(todo);
    }

    @Test
    @DisplayName("Update Todo Entity")
    void test_updateTodo() {
        final long id = 1;
        final String title = "title";
        final String content = "content";
        final var now = now();
        final var todoDTO = new TodoDTO("updatedTitle", "updatedContent");
        final var oldTodo = Todo.builder()
                .id(id)
                .title(title)
                .content(content)
                .createdDate(now)
                .lastModifiedDate(now)
                .build();
        final var newTodo = Todo.builder()
                .id(id)
                .title(todoDTO.title())
                .content(todoDTO.content())
                .createdDate(now)
                .lastModifiedDate(now)
                .build();


        given(todoRepository.findById(anyLong())).willReturn(Optional.of(oldTodo));
        given(todoRepository.save(any(Todo.class))).willReturn(newTodo);

        assertThat(todoService.editTodo(id, todoDTO)).isEqualTo(newTodo);
    }

    @Test
    @DisplayName("")
    void test_removeTodo() {
        final long id = 1;

        doNothing().when(todoRepository).deleteById(anyLong());

        todoService.removeTodo(id);

        verify(todoRepository, times(1)).deleteById(id);
    }
}