package com.example.cinehub.data.entity;

import com.example.cinehub.data.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Seat extends BaseEntity {

    private Integer rowNumber;
    private Integer seatNumber;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

}
