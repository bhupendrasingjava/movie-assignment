package com.example.theaterservice.repository;

import com.example.theaterservice.entity.Theater;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TheatreRepository extends JpaRepository<Theater, Long> {
}
