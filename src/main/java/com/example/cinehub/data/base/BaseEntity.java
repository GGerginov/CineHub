package com.example.cinehub.data.base;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

/**
 * Base class for all entity models in the application.
 * <p>
 * This class provides a common ID field for all entities. It uses UUID as the data type for the ID,
 * ensuring a unique identifier for each entity instance across the entire application.
 * <p>
 * The ID is generated using a UUID generator and is not updatable once set, ensuring the immutability
 * and uniqueness of the identifier.
 */
@Getter
@Setter
@MappedSuperclass
public abstract class BaseEntity {

    /**
     * The unique identifier for the entity.
     * <p>
     * This field is automatically generated using a UUID generator and is not updatable.
     * It ensures that each entity instance has a unique identifier.
     */
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID"
    )
    @Column(name = "id", updatable = false, nullable = false, unique = true, columnDefinition = "uuid DEFAULT gen_random_uuid()")
    private UUID id;
}
