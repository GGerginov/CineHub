package com.example.cinehub.data.base;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID"
    )
    @Column(name = "id", updatable = false, nullable = false, unique = true, columnDefinition = "uuid DEFAULT gen_random_uuid()")
    private UUID id;
}
