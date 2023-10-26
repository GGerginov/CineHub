package com.example.cinehub.data.entity;

import com.example.cinehub.data.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "show_time",
        uniqueConstraints = @UniqueConstraint(columnNames = {"room_id", "start_time"}))
public class ShowTime extends BaseEntity {

    @Column(name = "start_time",nullable = false)
    private LocalDateTime startTime;

    @Column(name = "end_time",nullable = false)
    private LocalDateTime endTime;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "movie_id",nullable = false)
    private Movie movie;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    @OneToMany(mappedBy = "showTime", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ticket> tickets = new ArrayList<>();

}