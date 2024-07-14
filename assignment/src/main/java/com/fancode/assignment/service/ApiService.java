package com.fancode.assignment.service;

import com.fancode.assignment.model.Todo;
import com.fancode.assignment.model.User;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ApiService {

    private final RestTemplate restTemplate;

    public ApiService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public List<User> getUsers() {
        ResponseEntity<User[]> response = restTemplate.getForEntity("https://jsonplaceholder.typicode.com/users", User[].class);
        return Arrays.asList(response.getBody());
    }

    public List<Todo> getTodos() {
        ResponseEntity<Todo[]> response = restTemplate.getForEntity("https://jsonplaceholder.typicode.com/todos", Todo[].class);
        return Arrays.asList(response.getBody());
    }

    public List<User> getFanCodeUsers() {
        return getUsers().stream()
                .filter(user -> {
                    double lat = Double.parseDouble(user.getAddress().getGeo().getLat());
                    double lng = Double.parseDouble(user.getAddress().getGeo().getLng());
                    return lat > -40 && lat < 5 && lng > 5 && lng < 100;
                })
                .collect(Collectors.toList());
    }

    public boolean isUserCompletingMoreThanHalfTasks(int userId) {
        List<Todo> userTodos = getTodos().stream()
                .filter(todo -> todo.getUserId() == userId)
                .collect(Collectors.toList());

        long completedTasks = userTodos.stream()
                .filter(Todo::isCompleted)
                .count();

        return completedTasks > userTodos.size() / 2.0;
    }
}