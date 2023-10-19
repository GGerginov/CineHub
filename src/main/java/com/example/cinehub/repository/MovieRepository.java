package com.example.cinehub.repository;

import com.example.cinehub.data.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface MovieRepository extends JpaRepository<Movie, UUID> {
    @Query("select m from Movie m where m.startTime > current_timestamp()")
    List<Movie> findAllActualMovies();

}