package me.g1tommy.r2dbctodosample.domain;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table
@Getter
@Builder
public class Todo {

    @Id
    private long id;

    private String title;

    private String content;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

    public void updateTodo(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
