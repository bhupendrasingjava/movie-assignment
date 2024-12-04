package com.example.theaterservice.repository;

import com.example.theaterservice.entity.Shows;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShowRepository extends JpaRepository<Shows, Long> {
}