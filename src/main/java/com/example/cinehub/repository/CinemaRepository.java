package com.example.cinehub.repository;

import com.example.cinehub.data.entity.Cinema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;

import java.util.List;

public interface CinemaRepository extends JpaRepository<Cinema,String> {

    @Query("select c from Cinema c where upper(c.city.name) = upper(?1)")
    List<Cinema> findByCityNameIgnoreCase(@NonNull String name);

}
