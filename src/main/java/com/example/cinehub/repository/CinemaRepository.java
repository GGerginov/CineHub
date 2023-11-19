package com.example.cinehub.repository;

import com.example.cinehub.data.entity.Cinema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;

import java.util.List;

/**
 * Repository interface for Cinema entities.
 * <p>
 * This interface extends JpaRepository and providing a range of methods for data access
 */
public interface CinemaRepository extends JpaRepository<Cinema,String> {

    /**
     * Checks if a cinema with the given slug exists.
     * <p>
     * This query checks the existence of a cinema by comparing the slug in a case-insensitive manner.
     *
     * @param slug The slug of the cinema to check.
     * @return true if a cinema with the given slug exists, false otherwise.
     */
    @Query("select (count(c) > 0) from Cinema c where upper(c.slug) = upper(?1)")
    boolean cinemaSlugExits(@NonNull String slug);

    /**
     * Finds cinemas by city name.
     * <p>
     * This query retrieves a list of cinemas located in a specified city. The city name comparison
     * is case-insensitive.
     *
     * @param name The name of the city.
     * @return A list of Cinema entities that are located in the specified city.
     */
    @Query("select c from Cinema c where upper(c.address.cityName) = upper(?1)")
    List<Cinema> findByCityNameIgnoreCase(@NonNull String name);

}
