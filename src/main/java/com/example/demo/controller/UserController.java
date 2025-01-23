package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.entity.LoginInfo;
import com.example.demo.service.UserService;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ModelAndView registerUser(@RequestParam String role) {
        LoginInfo user = userService.registerUser(role);

        ModelAndView modelAndView = new ModelAndView("result"); // 結果を表示するためのビュー名
        modelAndView.addObject("userId", user.getUserId());
        modelAndView.addObject("password", user.getPassword());
        modelAndView.addObject("role", user.getRole());
        return modelAndView;
    }
}