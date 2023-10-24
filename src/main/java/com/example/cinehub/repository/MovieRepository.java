package com.example.cinehub.repository;

import com.example.cinehub.data.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends JpaRepository<Movie,String> {
    boolean existsByTitle(String title);


}
