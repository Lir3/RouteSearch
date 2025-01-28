package com.example.demo.repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class LoginRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public String findRoleByusenameAndPassword(String usename, String password) {
        String sql = "SELECT role FROM login_info WHERE username = ? AND password = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{usename, password}, String.class);
        } catch (Exception e) {
            return null; // 該当するユーザーがいない場合
        }
    }
}
