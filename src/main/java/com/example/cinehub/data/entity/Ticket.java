package com.example.cinehub.data.entity;

import com.example.cinehub.data.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@Table(name = "ticket")
public class Ticket extends BaseEntity {

    @Enumerated(value = EnumType.STRING)
    @Column(name = "ticket_type")
    private TicketCategory ticketType;

    @Column(name = "price",nullable = false)
    private Double price;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "show_time_id")
    private ShowTime showTime;

    @Column(nullable = false,name = "is_reserved")
    private Boolean isReserved;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "seat_id")
    private Seat seat;

}