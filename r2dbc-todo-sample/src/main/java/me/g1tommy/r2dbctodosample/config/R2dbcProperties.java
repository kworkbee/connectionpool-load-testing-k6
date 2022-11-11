package me.g1tommy.r2dbctodosample.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConstructorBinding
@ConfigurationProperties("r2dbc")
public record R2dbcProperties(String url, String username, String password) {
}
