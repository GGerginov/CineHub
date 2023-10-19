package com.example.cinehub.repository;

import com.example.cinehub.data.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, String> {
    @Query("select r from Room r where upper(r.cinema.slug) = upper(?1)")
    List<Room> findAllRoomsByCinemaSlug(@NonNull String slug);
}