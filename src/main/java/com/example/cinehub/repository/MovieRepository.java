package com.example.cinehub.repository;

import com.example.cinehub.data.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for accessing and managing Movie data.
 * <p>
 * This interface provides methods for common database operations on Movie entities,
 * including a method to check for the existence of a movie by its title.
 */
@Repository
public interface MovieRepository extends JpaRepository<Movie,String> {

    /**
     * Determines if a movie with the specified title exists in the database.
     *
     * @param title The title of the movie to check.
     * @return true if a movie with the given title exists, false otherwise.
     */
    boolean existsByTitle(String title);

}
