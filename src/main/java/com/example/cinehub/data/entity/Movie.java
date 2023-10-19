package com.example.cinehub.data.entity;

import com.example.cinehub.data.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Movie extends BaseEntity {


    private String title;

    private String description;

    private LocalDateTime startTime;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;
}
