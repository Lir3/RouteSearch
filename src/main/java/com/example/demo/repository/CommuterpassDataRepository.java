package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.CommuterpassData;

public interface CommuterpassDataRepository extends JpaRepository<CommuterpassData, Long> {
	Optional<CommuterpassData> findByUsername(String username);
}
