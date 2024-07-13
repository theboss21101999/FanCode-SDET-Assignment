package com.fancode.assignment.repository;

import com.fancode.assignment.model.Todo;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;

@Repository
public class TodoRepository {

    private final WebClient webClient = WebClient.create("http://jsonplaceholder.typicode.com");

    public List<Todo> findByUserId(int userId) {
        return Arrays.asList(webClient.get()
                .uri("/todos?userId=" + userId)
                .retrieve()
                .bodyToMono(Todo[].class)
                .block());
    }
}