package com.fancode.assignment.repository;

import com.fancode.assignment.model.User;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.List;

@Repository
public class UserRepository {
    private final RestTemplate restTemplate;

    public UserRepository(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<User> findAll() {
        String url = "http://jsonplaceholder.typicode.com/users";
        User[] users = restTemplate.getForObject(url, User[].class);
        return Arrays.asList(users);
    }
}