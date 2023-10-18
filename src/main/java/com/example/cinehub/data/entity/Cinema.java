package com.example.cinehub.data.entity;

import com.example.cinehub.data.base.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Cinema extends BaseEntity {

    private String name;
    private String address;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

}

