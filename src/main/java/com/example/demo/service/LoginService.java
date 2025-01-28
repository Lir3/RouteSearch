package com.example.demo.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.repository.LoginRepository;

@Service
public class LoginService {

    @Autowired
    private LoginRepository loginRepository;

    public String authenticateUser(String name, String password) {
        return loginRepository.findRoleByusenameAndPassword(name, password);
    }
}
