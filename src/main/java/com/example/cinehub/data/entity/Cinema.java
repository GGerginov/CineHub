package com.example.cinehub.data.entity;

import com.example.cinehub.data.base.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class Cinema extends BaseEntity {

    @Column(nullable = false,length = 50)
    private String name;

    @Pattern(regexp = "^[a-zA-Z]+(-[a-zA-Z]+)+-\\d+$")
    @Column(nullable = false,unique = true)
    private String slug;

    @OneToOne
    @JoinColumn(name = "address_id",nullable = false)
    private Address address;
}

