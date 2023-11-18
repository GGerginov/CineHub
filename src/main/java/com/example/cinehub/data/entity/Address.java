package com.example.cinehub.data.entity;

import com.example.cinehub.data.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;


/**
 * Represents an address in the system.
 * <p>
 * This entity class is used to store address details associated with various entities like cinemas, users, etc.
 * It extends {@link BaseEntity} to include common identifier properties.
 * </p>
 *
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class Address extends BaseEntity {

    /**
     * The city name of the address.
     * This field is not nullable and is stored in the 'city_name' column of the address table.
     */
    @Column(name = "city_name",nullable = false)
    private String cityName;

    /**
     * The street part of the address.
     * This field is not nullable and has a maximum length of 100 characters.
     */
    @Column(nullable = false,length = 100)
    private String street;

}
