package com.example.cinehub.repository;

import com.example.cinehub.data.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Repository for accessing and managing Room data.
 * <p>
 * This interface extends JpaRepository, providing standard methods for database operations on Room entities.
 * It includes custom queries to fetch rooms based on various criteria like cinema slug, upcoming broadcasts, and specific time ranges.
 */
public interface RoomRepository extends JpaRepository<Room, String> {

    /**
     * Retrieves a list of rooms that have upcoming broadcasts.
     *
     * @return A list of Room entities with show times starting from the current timestamp onwards.
     */
    @Query("select r from Room r inner join r.showTimes showTimes where showTimes.startTime > current_timestamp()")
    List<Room> findAllRoomsWithUpcomingBroadcasts();

    /**
     * Finds all rooms associated with a specific cinema identified by its slug.
     *
     * @param slug The slug of the cinema.
     * @return A list of Room entities associated with the given cinema slug.
     */
    @Query("select r from Room r where upper(r.cinema.slug) = upper(?1)")
    List<Room> findAllRoomsByCinemaSlug(@NonNull String slug);

    /**
     * Finds a room that matches a specific room number, cinema slug, and falls within a given time range.
     *
     * @param roomNumber The room number.
     * @param slug The slug of the cinema.
     * @param startTime The start time of the period.
     * @param endTime The end time of the period.
     * @return An Optional containing the Room entity if found, or an empty Optional otherwise.
     */
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