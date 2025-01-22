package com.example.demo.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable() // CSRF対策を無効化（開発環境のみ）
            .authorizeRequests()
                .anyRequest().permitAll() // すべてのリクエストを許可
            .and()
            .formLogin()
                .loginPage("/login") // ログインページの指定
                .loginProcessingUrl("/login") // フォーム送信先のURL
                .defaultSuccessUrl("/home", true) // ログイン成功時の遷移先
                .failureUrl("/login?error=true"); // ログイン失敗時の遷移先
        return http.build();
    }
}

