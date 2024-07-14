package com.fancode.assignment.controller;

import com.fancode.assignment.model.User;
import com.fancode.assignment.service.ApiService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class FanCodeController {

    private final ApiService apiService;

    public FanCodeController(ApiService apiService) {
        this.apiService = apiService;
    }

    @GetMapping("/check-fancode-users")
    public ResponseEntity<List<String>> checkFanCodeUsers() {
        List<User> users = apiService.getFanCodeUsers();
        List<String> result = users.stream()
                .filter(user -> apiService.isUserCompletingMoreThanHalfTasks(user.getId()))
                .map(User::getName)
                .collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }
}

