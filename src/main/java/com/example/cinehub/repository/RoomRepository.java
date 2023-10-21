package com.example.cinehub.repository;

import com.example.cinehub.data.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, String> {
    @Query("select r from Room r inner join r.showTimes showTimes where showTimes.startTime > current_timestamp()")
    List<Room> findAllRoomsWithUpcomingBroadcasts();
    @Query("select r from Room r where upper(r.cinema.slug) = upper(?1)")
    List<Room> findAllRoomsByCinemaSlug(@NonNull String slug);

    @Query("""
            select r from Room r inner join r.showTimes showTimes
            where r.roomNumber = ?1 and
            upper(r.cinema.slug) = upper(?2) and
            showTimes.startTime > ?3 and
            showTimes.endTime < ?4""")
    Optional<Room> findRoomWithMoviesInRange(@NonNull Integer roomNumber,
                                             @NonNull String slug,
                                             @NonNull LocalDateTime startTime,
                                             @NonNull LocalDateTime endTime);


}