CREATE TABLE city
(
    id   UUID DEFAULT gen_random_uuid() NOT NULL,
    name VARCHAR(255)                   NOT NULL,
    CONSTRAINT pk_city PRIMARY KEY (id)
);

ALTER TABLE city
    ADD CONSTRAINT uc_city_name UNIQUE (name);

CREATE TABLE cinema
(
    id      UUID DEFAULT gen_random_uuid() NOT NULL,
    name    VARCHAR(255)                   NOT NULL,
    address VARCHAR(255)                   NOT NULL,
    slug    VARCHAR(255)                   NOT NULL,
    city_id UUID   NOT NULL,
    CONSTRAINT pk_cinema PRIMARY KEY (id)
);
ALTER TABLE cinema
    ADD CONSTRAINT uc_cinema_slug UNIQUE (slug);


ALTER TABLE cinema
    ADD CONSTRAINT FK_CINEMA_ON_CITY FOREIGN KEY (city_id) REFERENCES city (id);


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








