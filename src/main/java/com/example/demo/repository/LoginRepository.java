package com.example.demo.repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class LoginRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public String findRoleByUserIdAndPassword(String userId, String password) {
        String sql = "SELECT role FROM login_info WHERE user_id = ? AND password = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{userId, password}, String.class);
        } catch (Exception e) {
            return null; // 該当するユーザーがいない場合
        }
    }
}
