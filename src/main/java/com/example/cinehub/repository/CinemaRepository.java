package com.example.cinehub.repository;

import com.example.cinehub.data.entity.Cinema;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CinemaRepository extends JpaRepository<Cinema,String> {
}
