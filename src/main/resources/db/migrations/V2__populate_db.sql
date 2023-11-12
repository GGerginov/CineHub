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



INSERT INTO ticket (price, ticket_type, show_time_id, seat_id, is_reserved,version_number)
SELECT
    10.0, -- Fixed price for all tickets
    'REGULAR', -- Fixed ticket type for all tickets
    st.id AS show_time_id,
    s.id AS seat_id,
    FALSE ,
    1
FROM
    seat s
        CROSS JOIN
    show_time st;



