

package com.example.demo.service;

import java.security.SecureRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.LoginInfo;
import com.example.demo.repository.LoginInfoRepository;

@Service
public class UserService {

    @Autowired
    private LoginInfoRepository loginInfoRepository;

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int LENGTH = 10;
    private static final SecureRandom random = new SecureRandom();

    public String generateRandomString(int length) {
        StringBuilder stringBuilder = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(CHARACTERS.length());
            stringBuilder.append(CHARACTERS.charAt(index));
        }
        return stringBuilder.toString();
    }

    public LoginInfo registerUser(String role) {
        String userId;
        do {
            userId = generateRandomString(LENGTH);
        } while (loginInfoRepository.findByUserId(userId).isPresent());

        String password = generateRandomString(LENGTH);

        LoginInfo user = new LoginInfo();
        user.setUserId(userId);
        user.setPassword(password);
        user.setRole(role);

        return loginInfoRepository.save(user);
    }
}