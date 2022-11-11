package me.g1tommy.jdbctodosample.controller;

import lombok.RequiredArgsConstructor;
import me.g1tommy.jdbctodosample.domain.Todo;
import me.g1tommy.jdbctodosample.domain.dto.TodoDTO;
import me.g1tommy.jdbctodosample.service.TodoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/todos")
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;

    @GetMapping("/{id}")
    public Todo getTodo(@PathVariable long id) {
        return todoService.getTodo(id);
    }

    @PostMapping
    public Todo newTodo(@RequestBody TodoDTO todo) {
        return todoService.newTodo(todo);
    }

    @PutMapping("/{id}")
    public Todo editTodo(@PathVariable long id, @RequestBody TodoDTO todo) {
        return todoService.editTodo(id, todo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeTodo(@PathVariable long id) {
        todoService.removeTodo(id);
        return ResponseEntity.noContent().build();
    }
}
