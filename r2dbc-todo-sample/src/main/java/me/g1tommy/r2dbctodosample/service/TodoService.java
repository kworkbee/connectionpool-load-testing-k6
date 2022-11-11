package me.g1tommy.r2dbctodosample.service;

import lombok.RequiredArgsConstructor;
import me.g1tommy.r2dbctodosample.domain.TodoRepository;
import me.g1tommy.r2dbctodosample.domain.dto.TodoDTO;
import org.springframework.stereotype.Service;
import me.g1tommy.r2dbctodosample.domain.Todo;
import reactor.core.publisher.Mono;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class TodoService {

    private static final String NO_SUCH_ITEM = "No item exists.";
    private final TodoRepository todoRepository;

    public Mono<Todo> getTodo(long id) {
        return todoRepository.findById(id);
    }

    public Mono<Todo> newTodo(TodoDTO todo) {
        var newTodo = Todo.builder()
                .title(todo.title())
                .content(todo.content())
                .build();

        return todoRepository.save(newTodo);
    }

    public Mono<Todo> updateTodo(long id, TodoDTO todo) {
        return todoRepository.findById(id)
                .switchIfEmpty(Mono.defer(() -> Mono.error(new NoSuchElementException(NO_SUCH_ITEM))))
                .flatMap(oldTodo -> {
                    oldTodo.updateTodo(todo.title(), todo.content());
                    return todoRepository.save(oldTodo);
                });
    }

    public Mono<Void> removeTodo(long id) {
        return todoRepository.deleteById(id).then();
    }


}
