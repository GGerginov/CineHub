package com.example.cinehub.data.entity;

import com.example.cinehub.data.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Room extends BaseEntity {

    private Integer roomNumber;

    private Integer capacity;

    @ManyToOne
    @JoinColumn(name = "cinema_id")
    private Cinema cinema;

}
