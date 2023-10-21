package com.example.cinehub.data.entity;

import com.example.cinehub.data.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(
        name = "seat",
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"row_number", "seat_number", "room_id"}
        )
)
public class Seat extends BaseEntity {

    @Column(nullable = false,name = "row_number")
    private Integer rowNumber;

    @Column(nullable = false,name = "seat_number")
    private Integer seatNumber;

    @ManyToOne
    @JoinColumn(name = "room_id",nullable = false)
    private Room room;

}
