package me.g1tommy.jdbctodosample.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.g1tommy.jdbctodosample.domain.Todo;
import me.g1tommy.jdbctodosample.domain.dto.TodoDTO;
import me.g1tommy.jdbctodosample.service.TodoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TodoController.class)
class TodoControllerTest {

    @MockBean
    private TodoService todoService;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Get Todo ID 1")
    void test_getting_todo() throws Exception {
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

        given(todoService.getTodo(anyLong())).willReturn(todo);

        mvc.perform(get("/todos/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.title").value(title))
                .andExpect(jsonPath("$.content").value(content));
    }

    @Test
    @DisplayName("Create Todo ID 1")
    void test_creating_todo() throws Exception {
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
        final TodoDTO dto = new TodoDTO(title, content);

        given(todoService.newTodo(any(TodoDTO.class))).willReturn(todo);

        mvc.perform(post("/todos")
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.title").value(title))
                .andExpect(jsonPath("$.content").value(content));
    }

    @Test
    @DisplayName("Update Todo ID 1")
    void test_updating_todo() throws Exception {
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
        final TodoDTO dto = new TodoDTO("updatedTitle", "updatedContent");

        todo.updateTitle(dto.title());
        todo.updateContent(dto.content());

        given(todoService.editTodo(anyLong(), any(TodoDTO.class))).willReturn(todo);

        mvc.perform(put("/todos/" + id)
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.title").value(dto.title()))
                .andExpect(jsonPath("$.content").value(dto.content()));
    }

    @Test
    @DisplayName("Delete Todo ID 1")
    void test_deleting_todo() throws Exception {
        final long id = 1;

        doNothing().when(todoService).removeTodo(anyLong());

        mvc.perform(delete("/todos/" + id))
                .andExpect(status().isNoContent());

        verify(todoService, times(1)).removeTodo(id);
    }
}