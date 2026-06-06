package com.example.iotplatform.controller;

import com.example.iotplatform.dto.ApiResponse;
import com.example.iotplatform.dto.LoginRequest;
import com.example.iotplatform.entity.User;
import com.example.iotplatform.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Users")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 用户登录（同时返回AccessToken）
     * POST /Users/Login
     */
    @PostMapping("/Login")
    public ApiResponse<User> login(@RequestBody LoginRequest request) {
        return userService.login(request);
    }

    /**
     * 查询用户APIKey
     * GET /Users/ApiKey
     */
    @GetMapping("/ApiKey")
    public ApiResponse<User> getApiKey() {
        return userService.getApiKey();
    }

    /**
     * 更新用户APIKey
     * PUT /Users/ApiKey
     */
    @PutMapping("/ApiKey")
    public ApiResponse<Object> updateApiKey(@RequestBody User user) {
        return userService.updateApiKey(user);
    }

}