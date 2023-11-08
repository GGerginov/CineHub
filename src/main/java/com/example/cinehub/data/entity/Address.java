package com.example.cinehub.data.entity;

import com.example.cinehub.data.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class Address extends BaseEntity {

    @Column(name = "city_name",nullable = false)
    private String cityName;

    @Column(nullable = false,length = 100)
    private String street;

}
