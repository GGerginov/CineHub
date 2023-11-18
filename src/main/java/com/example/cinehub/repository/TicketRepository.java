package com.example.cinehub.repository;

import com.example.cinehub.data.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository for accessing and managing Ticket data.
 * <p>
 * This interface extends JpaRepository, providing standard methods for database operations on Ticket entities.
 * It includes custom queries to fetch tickets based on room number, cinema slug, movie title, and for locking a specific ticket for update operations.
 */
@Repository
public interface TicketRepository extends JpaRepository<Ticket, UUID> {

    /**
     * Retrieves a list of tickets based on the room number, cinema slug, and movie title.
     *
     * @param roomNumber The room number where the movie is shown.
     * @param slug The slug of the cinema.
     * @param title The title of the movie.
     * @return A list of Ticket entities that match the given criteria.
     */
    @Query("""
            select t from Ticket t
            where t.showTime.room.roomNumber = ?1 and t.seat.room.cinema.slug = ?2 and t.showTime.movie.title = ?3""")
    List<Ticket> findAllTicketsByRoomNumberAndSlugAndMovieTitle(@NonNull Integer roomNumber,
                                                                @NonNull String slug,
                                                                @NonNull String title);

    /**
     * Finds a ticket by its ID and locks it for update operations.
     * This method is intended for use in transactions where concurrent modifications to the ticket need to be prevented.
     *
     * @param uuid The unique identifier of the ticket.
     * @return An Optional containing the Ticket entity if found, or an empty Optional otherwise.
     */
    @Query("SELECT t FROM Ticket t WHERE t.id = ?1")
    Optional<Ticket> findByIdForUpdate(UUID uuid);
}