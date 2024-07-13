package com.fancode.assignment.service;
import com.fancode.assignment.model.User;
import com.fancode.assignment.model.Todo;
import com.fancode.assignment.repository.UserRepository;
import com.fancode.assignment.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RestTemplate restTemplate;

    @Autowired
    public UserService(UserRepository userRepository, RestTemplate restTemplate) {
        this.userRepository = userRepository;
        this.restTemplate = restTemplate;
    }

    public List<User> getFanCodeUsersWithCompletedTodos() {
        // Fetch all users
        List<User> users = userRepository.findAll();

        return users.stream()
                .filter(this::isFanCodeCity) // Filter users from FanCode city
                .map(this::fetchTodos) // Fetch todos for each user
                .filter(this::isCompletedTasksMoreThanHalf) // Filter users based on completed tasks
                .collect(Collectors.toList()); // Collect results
    }

    private boolean isFanCodeCity(User user) {
        double lat = user.getAddress().getGeo().getLat();
        double lng = user.getAddress().getGeo().getLng();
        return lat > -40 && lat < 5 && lng > 5 && lng < 100;
    }

    private User fetchTodos(User user) {
        String url = String.format("http://jsonplaceholder.typicode.com/users/%d/todos", user.getId());
        Todo[] todos = restTemplate.getForObject(url, Todo[].class);
        user.setTodos(Arrays.asList(todos));
        return user;
    }

    private boolean isCompletedTasksMoreThanHalf(User user) {
        List<Todo> todos = user.getTodos();
        if (todos == null || todos.isEmpty()) {
            return false; // Skip users with no todos
        }
        long completedTasks = todos.stream().filter(Todo::isCompleted).count();
        double completedPercentage = (double) completedTasks / todos.size() * 100;
        return completedPercentage > 50; // Check if completed tasks percentage is more than 50%
    }
}