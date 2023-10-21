package com.example.cinehub.repository;

import com.example.cinehub.data.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;


@Repository
public interface TicketRepository extends JpaRepository<Ticket, UUID> {
    @Query("""
            select t from Ticket t
            where t.showTime.room.roomNumber = ?1 and t.seat.room.cinema.slug = ?2 and t.showTime.movie.title = ?3""")
    List<Ticket> findAllTicketsByRoomNumberAndSlugAndMovieTitle(@NonNull Integer roomNumber,
                                                                @NonNull String slug,
                                                                @NonNull String title);

}