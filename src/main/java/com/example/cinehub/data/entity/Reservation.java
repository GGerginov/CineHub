package com.example.cinehub.data.entity;

import com.example.cinehub.data.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Reservation extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "seat_id")
    private Seat seat;

    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;

    private Boolean isReserved;

}
