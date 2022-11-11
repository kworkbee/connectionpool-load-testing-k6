package me.g1tommy.r2dbctodosample.controller;

import lombok.RequiredArgsConstructor;
import me.g1tommy.r2dbctodosample.domain.Todo;
import me.g1tommy.r2dbctodosample.domain.dto.TodoDTO;
import me.g1tommy.r2dbctodosample.service.TodoService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/todos")
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;

    @GetMapping("/{id}")
    public Mono<Todo> getTodo(@PathVariable long id) {
        return todoService.getTodo(id);
    }

    @PostMapping
    public Mono<Todo> newTodo(@RequestBody TodoDTO todo) {
        return todoService.newTodo(todo);
    }

    @PutMapping("/{id}")
    public Mono<Todo> updateTodo(@PathVariable long id, @RequestBody TodoDTO todo) {
        return todoService.updateTodo(id, todo);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> removeTodo(@PathVariable long id) {
        return todoService.removeTodo(id);
    }
}
