CREATE EXTENSION IF NOT EXISTS pgcrypto;


create table if not exists address
(
    id        uuid default gen_random_uuid() not null
        primary key,
    city_name varchar(255)                   not null,
    street    varchar(100)                   not null
);

create table cinema
(
    id         uuid default gen_random_uuid() not null
        primary key,
    name       varchar(50)                    not null,
    slug       varchar(255)                   not null
        constraint uk_501kcmrdt92rxxmh9844qfv6d
            unique,
    address_id uuid  not null
        constraint uk_pf4v54uupbfxj9ef5lvkndkuc
            unique
        constraint fkaqp1e3cmq6hcmk0q163xrd8ss
            references address
);

CREATE TABLE movie
(
    id          UUID DEFAULT gen_random_uuid() NOT NULL,
    title       VARCHAR(255)                   NOT NULL,
    description VARCHAR(255),
    duration    INTEGER                        NOT NULL,
    CONSTRAINT pk_movie PRIMARY KEY (id)
);


CREATE TABLE room
(
    id          UUID DEFAULT gen_random_uuid() NOT NULL,
    room_number INTEGER                        NOT NULL,
    capacity    INTEGER                        NOT NULL,
    cinema_id   UUID  NOT NULL,
    CONSTRAINT pk_room PRIMARY KEY (id)
);

ALTER TABLE room
    ADD CONSTRAINT FK_ROOM_ON_CINEMA FOREIGN KEY (cinema_id) REFERENCES cinema (id);


CREATE TABLE seat
(
    id          UUID DEFAULT gen_random_uuid() NOT NULL,
    row_number  INTEGER                        NOT NULL,
    seat_number INTEGER                        NOT NULL,
    room_id     UUID NOT NULL,
    CONSTRAINT pk_seat PRIMARY KEY (id)
);

ALTER TABLE seat
    ADD CONSTRAINT uc_9d1a255e74cfcf3d206ebc446 UNIQUE (row_number, seat_number, room_id);

ALTER TABLE seat
    ADD CONSTRAINT FK_SEAT_ON_ROOM FOREIGN KEY (room_id) REFERENCES room (id);

CREATE TABLE show_time
(
    id         UUID DEFAULT gen_random_uuid() NOT NULL,
    start_time TIMESTAMP WITHOUT TIME ZONE    NOT NULL,
    end_time   TIMESTAMP WITHOUT TIME ZONE    NOT NULL,
    movie_id   UUID  NOT NULL,
    room_id    UUID NOT NULL ,
    CONSTRAINT pk_show_time PRIMARY KEY (id)
);

ALTER TABLE show_time
    ADD CONSTRAINT uc_d16807c5e34507ee873e67218 UNIQUE (room_id, start_time);

ALTER TABLE show_time
    ADD CONSTRAINT FK_SHOW_TIME_ON_MOVIE FOREIGN KEY (movie_id) REFERENCES movie (id);


ALTER TABLE show_time
    ADD CONSTRAINT FK_SHOW_TIME_ON_ROOM FOREIGN KEY (room_id) REFERENCES room (id);

CREATE TABLE ticket
(
    id           UUID DEFAULT gen_random_uuid() NOT NULL,
    ticket_type  VARCHAR(255),
    price        DOUBLE PRECISION               NOT NULL,
    show_time_id UUID  NOT NULL ,
    is_reserved  BOOLEAN DEFAULT FALSE          NOT NULL,
    seat_id      UUID NOT NULL ,
    CONSTRAINT pk_ticket PRIMARY KEY (id)
);

ALTER TABLE ticket
    ADD CONSTRAINT FK_TICKET_ON_SEAT FOREIGN KEY (seat_id) REFERENCES seat (id);

ALTER TABLE ticket
    ADD CONSTRAINT FK_TICKET_ON_SHOW_TIME FOREIGN KEY (show_time_id) REFERENCES show_time (id);

INSERT INTO address (city_name, street) VALUES
                                            ('Sofia','Vitosha Boulevard 1'),
                                            ('Sofia','Okolovrasten pat'),
                                            ('Plovdiv','Knyaz Alexander I'),
                                            ('Varna','Boulevard Knyaz Boris I'),
                                            ('Aprilci','Vasil Levski');


INSERT INTO cinema (name, slug, address_id) VALUES
                                                ('Sofia Cinema 1', 'sofia-cinema-1', (SELECT id FROM address WHERE city_name = 'Sofia' and street = 'Okolovrasten pat')),
                                                ('Sofia Cinema 2', 'sofia-cinema-2', (SELECT id FROM address WHERE city_name = 'Sofia' and street = 'Vitosha Boulevard 1')),
                                                ('Plovdiv Cinema 1', 'plovdiv-cinema-1', (SELECT id FROM address WHERE city_name = 'Plovdiv' and street = 'Knyaz Alexander I')),
                                                ('Varna Cinema 1', 'varna-cinema-1', (SELECT id FROM address WHERE city_name = 'Varna' and street = 'Boulevard Knyaz Boris I')),
                                                ('Aprilci Cinema 1', 'aprilci-cinema-1', (SELECT id FROM address WHERE city_name = 'Aprilci' and street = 'Vasil Levski'));



INSERT INTO movie (title, description, duration) VALUES
                                                     ('Adventure Land', 'A thrilling adventure movie', 120),
                                                     ('Love in Paris', 'A heartwarming romantic comedy', 90),
                                                     ('Space Warriors', 'An action-packed sci-fi film', 150),
                                                     ('The Unknown Detective', 'A mysterious detective story', 110),
                                                     ('Ocean Mysteries', 'An underwater exploration film', 95),
                                                     ('Haunted Hallows', 'A spine-chilling horror flick', 105),
                                                     ('Cyber Hack', 'A cybercrime thriller movie', 100),
                                                     ('The Grand Heist', 'A suspenseful heist drama', 115),
                                                     ('War of the Worlds', 'A story of alien invasion and survival', 130),
                                                     ('Time Twisters', 'A time travel movie with unexpected twists', 125);


INSERT INTO room (capacity, room_number, cinema_id) VALUES
                                                        (50, 1, (SELECT id FROM cinema WHERE slug = 'sofia-cinema-1')),
                                                        (50, 2, (SELECT id FROM cinema WHERE slug = 'sofia-cinema-1')),
                                                        (50, 3, (SELECT id FROM cinema WHERE slug = 'sofia-cinema-1')),
                                                        (50, 1, (SELECT id FROM cinema WHERE slug = 'sofia-cinema-2')),
                                                        (50, 1, (SELECT id FROM cinema WHERE slug = 'plovdiv-cinema-1')),
                                                        (50, 2, (SELECT id FROM cinema WHERE slug = 'plovdiv-cinema-1')),
                                                        (50, 3, (SELECT id FROM cinema WHERE slug = 'plovdiv-cinema-1')),
                                                        (50, 1, (SELECT id FROM cinema WHERE slug = 'varna-cinema-1')),
                                                        (50, 2, (SELECT id FROM cinema WHERE slug = 'varna-cinema-1')),
                                                        (50, 3, (SELECT id FROM cinema WHERE slug = 'varna-cinema-1')),
                                                        (50, 1, (SELECT id FROM cinema WHERE slug = 'aprilci-cinema-1'));


INSERT INTO seat (row_number, seat_number, room_id)
SELECT
    row_num,
    seat_num,
    r.id
FROM
    generate_series(1, 10) AS row_num,
    generate_series(1, 10) AS seat_num,
    room r
WHERE
    r.id IS NOT NULL;

INSERT INTO show_time (start_time, end_time, movie_id, room_id) VALUES
-- Room 1 in Sofia Cinema 1
('2023-11-10 12:00:00', '2023-11-10 14:00:00', (SELECT id FROM movie WHERE title = 'Adventure Land'), (SELECT id FROM room WHERE room_number = 1 AND cinema_id = (SELECT id FROM cinema WHERE slug = 'sofia-cinema-1'))),
('2023-11-10 15:00:00', '2023-11-10 17:00:00', (SELECT id FROM movie WHERE title = 'Space Warriors'), (SELECT id FROM room WHERE room_number = 1 AND cinema_id = (SELECT id FROM cinema WHERE slug = 'sofia-cinema-1'))),

-- Room 2 in Sofia Cinema 1
('2023-11-10 12:00:00', '2023-11-10 14:00:00', (SELECT id FROM movie WHERE title = 'Love in Paris'), (SELECT id FROM room WHERE room_number = 2 AND cinema_id = (SELECT id FROM cinema WHERE slug = 'sofia-cinema-1'))),
('2023-11-10 15:00:00', '2023-11-10 17:00:00', (SELECT id FROM movie WHERE title = 'The Unknown Detective'), (SELECT id FROM room WHERE room_number = 2 AND cinema_id = (SELECT id FROM cinema WHERE slug = 'sofia-cinema-1'))),

-- Room 1 in Plovdiv Cinema 1
('2023-11-10 13:00:00', '2023-11-10 15:00:00', (SELECT id FROM movie WHERE title = 'Adventure Land'), (SELECT id FROM room WHERE room_number = 1 AND cinema_id = (SELECT id FROM cinema WHERE slug = 'plovdiv-cinema-1'))),
('2023-11-10 16:00:00', '2023-11-10 18:00:00', (SELECT id FROM movie WHERE title = 'Space Warriors'), (SELECT id FROM room WHERE room_number = 1 AND cinema_id = (SELECT id FROM cinema WHERE slug = 'plovdiv-cinema-1'))),

-- Room 1 in Varna Cinema 1
('2023-11-10 12:30:00', '2023-11-10 14:30:00', (SELECT id FROM movie WHERE title = 'Love in Paris'), (SELECT id FROM room WHERE room_number = 1 AND cinema_id = (SELECT id FROM cinema WHERE slug = 'varna-cinema-1'))),
('2023-11-10 15:30:00', '2023-11-10 17:30:00', (SELECT id FROM movie WHERE title = 'The Unknown Detective'), (SELECT id FROM room WHERE room_number = 1 AND cinema_id = (SELECT id FROM cinema WHERE slug = 'varna-cinema-1'))),

-- Room 1 in Aprilci Cinema
('2023-11-10 14:00:00', '2023-11-10 16:00:00', (SELECT id FROM movie WHERE title = 'Adventure Land'), (SELECT id FROM room WHERE room_number = 1 AND cinema_id = (SELECT id FROM cinema WHERE slug = 'aprilci-cinema-1'))),
('2023-11-10 17:00:00', '2023-11-10 19:00:00', (SELECT id FROM movie WHERE title = 'Space Warriors'), (SELECT id FROM room WHERE room_number = 1 AND cinema_id = (SELECT id FROM cinema WHERE slug = 'aprilci-cinema-1'))),

-- Another Room in Sofia Cinema 1 for variety
('2023-11-10 12:00:00', '2023-11-10 13:30:00', (SELECT id FROM movie WHERE title = 'Love in Paris'), (SELECT id FROM room WHERE room_number = 3 AND cinema_id = (SELECT id FROM cinema WHERE slug = 'sofia-cinema-1'))),
('2023-11-10 14:00:00', '2023-11-10 15:30:00', (SELECT id FROM movie WHERE title = 'The Unknown Detective'), (SELECT id FROM room WHERE room_number = 3 AND cinema_id = (SELECT id FROM cinema WHERE slug = 'sofia-cinema-1')));




INSERT INTO ticket (price, ticket_type, show_time_id, seat_id, is_reserved)
SELECT
    10.0, -- Fixed price for all tickets
    'REGULAR', -- Fixed ticket type for all tickets
    st.id AS show_time_id,
    s.id AS seat_id,
    FALSE -- Assuming all seats are initially not reserved
FROM
    seat s
        CROSS JOIN
    show_time st;










