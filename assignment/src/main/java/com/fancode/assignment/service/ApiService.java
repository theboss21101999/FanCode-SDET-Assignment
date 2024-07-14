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
/**
 * Service class to handle FanCode Assignment logic.
 * Author: Bojja Srikar
 */

@Service
public class ApiService {

    private final RestTemplate restTemplate;

    public ApiService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public List<User> getUsers() {  //Fetches the list of users from the API.
        ResponseEntity<User[]> response = restTemplate.getForEntity("https://jsonplaceholder.typicode.com/users", User[].class);
        return Arrays.asList(response.getBody());
    }

    public List<Todo> getTodos() {  //Fetches the list of todos from the API.
        ResponseEntity<Todo[]> response = restTemplate.getForEntity("https://jsonplaceholder.typicode.com/todos", Todo[].class);
        return Arrays.asList(response.getBody());
    }

    public List<User> getFanCodeUsers() {
        return getUsers().stream()
                .filter(user -> {
                    double lat = Double.parseDouble(user.getAddress().getGeo().getLat());
                    double lng = Double.parseDouble(user.getAddress().getGeo().getLng());
                    // Filtering users based on latitude and longitude constraints
                    return lat > -40 && lat < 5 && lng > 5 && lng < 100;
                })
                .collect(Collectors.toList());
    }

    /**
     * Checks if a user has completed more than half of their todos.
     * @param userId ID of the user to check.
     * @return true if the user has completed more than half of their todos, false otherwise.
     */

    public boolean isUserCompletingMoreThanHalfTasks(int userId) {
        List<Todo> userTodos = getTodos().stream()
                .filter(todo -> todo.getUserId() == userId)
                .collect(Collectors.toList());

        long completedTasks = userTodos.stream()
                .filter(Todo::isCompleted)
                .count();
        // Check if completed tasks are more than half of total task
        return completedTasks > userTodos.size() / 2.0;
    }
}