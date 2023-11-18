package com.example.cinehub.data.entity;

import com.example.cinehub.data.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

/**
 * Entity representing a ticket in the cinema system.
 * <p>
 * This class is used to store information about individual tickets for movie screenings. It extends
 * {@link BaseEntity} to inherit common identifier properties.
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@Table(name = "ticket")
public class Ticket extends BaseEntity {

    /**
     * The category of the ticket.
     * This field is stored as an enumerated type and represents different categories of tickets,
     * such as regular, vip, student, etc.
     */
    @Enumerated(value = EnumType.STRING)
    @Column(name = "ticket_type")
    private TicketCategory ticketType;

    /**
     * The price of the ticket.
     * This field is not nullable and stores the cost of the ticket.
     */
    @Column(name = "price",nullable = false)
    private Double price;

    /**
     * The showtime associated with the ticket.
     * This field establishes a relationship with the {@link ShowTime} entity,
     * indicating the specific screening for which this ticket is valid.
     */
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "show_time_id")
    private ShowTime showTime;

    /**
     * Indicates whether the ticket is reserved.
     * This field is not nullable and is used to track the reservation status of the ticket.
     */
    @Column(nullable = false,name = "is_reserved")
    private Boolean isReserved;

    /**
     * The seat associated with the ticket.
     * This field establishes a relationship with the {@link Seat} entity,
     * indicating the specific seat assigned to this ticket.
     */
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "seat_id")
    private Seat seat;

    /**
     * The version number for optimistic locking.
     * This field is used to handle concurrent updates to the ticket entity, ensuring data integrity.
     */
    @Version
    private Long versionNumber;

}