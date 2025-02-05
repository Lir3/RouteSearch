package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.CommuterpassData;

public interface RicdataRepository extends JpaRepository<CommuterpassData, Long> {
}
