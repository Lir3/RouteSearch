package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.entity.LoginInfo;
import com.example.demo.service.UserService;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    @ResponseBody
    public ResponseEntity<?> registerUser(@RequestParam String role) {
        LoginInfo user = userService.registerUser(role);

        // JSON形式でレスポンスを返す
        return ResponseEntity.ok(user);
    }
}