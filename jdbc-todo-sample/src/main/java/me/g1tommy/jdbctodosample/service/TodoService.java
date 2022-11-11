package me.g1tommy.jdbctodosample.service;

import lombok.RequiredArgsConstructor;
import me.g1tommy.jdbctodosample.domain.Todo;
import me.g1tommy.jdbctodosample.domain.TodoRepository;
import me.g1tommy.jdbctodosample.domain.dto.TodoDTO;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class TodoService {

    private static final String NO_TODO_ITEM = "No todo exists.";

    private final TodoRepository todoRepository;

    public Todo getTodo(long id) {
        return todoRepository.findById(id).orElseThrow(() -> new NoSuchElementException(NO_TODO_ITEM));
    }

    public Todo newTodo(TodoDTO todo) {
        return todoRepository.save(Todo.builder()
                                        .title(todo.title())
                                        .content(todo.content())
                                        .build());
    }

    public Todo editTodo(long id, TodoDTO todo) {
        Todo oldTodo = todoRepository.findById(id).orElseThrow(() -> new NoSuchElementException(NO_TODO_ITEM));
        oldTodo.updateTitle(todo.title());
        oldTodo.updateContent(todo.content());

        return todoRepository.save(oldTodo);

    }

    public void removeTodo(long id) {
        todoRepository.deleteById(id);
    }
}
