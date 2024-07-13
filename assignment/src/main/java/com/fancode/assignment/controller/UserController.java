package com.fancode.assignment.controller;
import com.fancode.assignment.model.User;
import com.fancode.assignment.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/fancode")
    public List<User> getFanCodeUsers() {
        return userService.getFanCodeUsersWithCompletedTodos();
    }
}