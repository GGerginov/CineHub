package com.example.cinehub.data.entity;

import com.example.cinehub.data.base.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.*;

/**
 * Represents a cinema in the system.
 * <p>
 * This entity class is used to store information about individual cinemas. It extends {@link BaseEntity}
 * to inherit common identifier properties.
 * </p>
 *
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class Cinema extends BaseEntity {

    /**
     * The name of the cinema.
     * This field is not nullable and has a maximum length of 50 characters.
     */
    @Column(nullable = false,length = 50)
    private String name;

    /**
     * A unique slug identifier for the cinema.
     * This field is not nullable, must be unique, and follows a specific pattern defined by the regular expression.
     * For example sofia-cinema-1
     */
    @Pattern(regexp = "^[a-zA-Z]+(-[a-zA-Z]+)+-\\d+$")
    @Column(nullable = false,unique = true)
    private String slug;

    /**
     * The address associated with the cinema.
     * This is a relationship with the {@link Address} entity. The join column is 'address_id'.
     */
    @OneToOne
    @JoinColumn(name = "address_id",nullable = false)
    private Address address;
}

