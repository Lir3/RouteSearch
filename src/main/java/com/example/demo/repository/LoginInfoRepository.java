package com.example.demo.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.LoginInfo;

@Repository
public interface LoginInfoRepository extends JpaRepository<LoginInfo, Integer> {
	Optional<LoginInfo> findByusername(String username);
}