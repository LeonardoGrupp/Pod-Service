INSERT INTO pod_artists (id, name)
VALUES
    (1, 'Joe Rogan'),
    (2, 'Elon Musk'),
    (3, 'Quentin Tarantino'),
    (4, 'Kevin Hart'),
    (5, 'Neil deGrasse Tyson'),

    (6, 'Lex Fridman'),
    (7, 'Tucker Carlsson'),
    (8, 'Kanye West'),
    (9, 'Paul Rosolie'),

    (10, 'They Von'),
    (11, 'Bobby Lee'),
    (12, 'KSI'),
    (13, 'Druski'),
    (14, 'Post Malone')
;

INSERT INTO pod_albums (id, album_name, release_date)
VALUES
    (1, 'The Joe Rogan Experience', '2000'),
    (2, 'Lex Fridman Podcast', '2005'),
    (3, 'This Past Weekend', '2010')
;

-- INSERT Genres
INSERT INTO pod_genres (id, genre, total_likes, total_plays)
VALUES
    (1, 'Comedy', 0, 0),
    (2, 'Movies', 0, 0),
    (3, 'Crime', 0, 0),
    (4, 'Politics', 0, 0),
    (5, 'Business', 0, 0),
    (6, 'Economy', 0, 0),
    (7, 'Sports', 0, 0),
    (8, 'Fashion', 0, 0),
    (9, 'Space', 0, 0)
;

INSERT INTO pods (id, type, title, url, release_date, play_counter, likes, dis_likes)
VALUES
    -- POD
    (1, 'pod', '#1169', 'url66', '2006-02-07', 0, 0, 0),
    (2, 'pod','#1278', 'url67', '2006-02-07', 0, 0, 0),
    (3, 'pod', '#1675', 'url68', '2006-02-07', 0, 0, 0),
    (4, 'pod', '#919', 'url69', '2006-02-07', 0, 0, 0),

    (5, 'pod', 'Neuralink and the Guture of Humanity', 'url70', '2006-02-07', 0, 0, 0),
    (6, 'pod', 'Putin, Navalny, Trump, CIA, NSA, War, Politics & Freedom', 'url71', '2006-02-07', 0, 0, 0),
    (7, 'pod', 'Kanye West Interview', 'url72', '2006-02-07', 0, 0, 0),
    (8, 'pod', 'Jungle, Apex Predators, Aliens, Uncontacted Tribes and God', 'url73', '2006-02-07', 0, 0, 0),

    (9, 'pod', '#530', 'url74', '2006-02-07', 0, 0, 0),
    (10, 'pod', '#514', 'url75', '2006-02-07', 0, 0, 0),
    (11, 'pod', '#489', 'url76', '2006-02-07', 0, 0, 0),
    (12, 'pod', '#529', 'url77', '2006-02-07', 0, 0, 0)
;

INSERT INTO pods_genres (pods_id, genre_id)
VALUES
    -- MUSIC
    -- joe rogan episodes to genre
    (1, 5), (2, 1), (3, 2), (4, 9),

    -- lex fridman episodes to genre
    (5, 9), (6, 4), (7, 4), (7, 5), (8, 4),

    -- theo von episodes to genre
    (9, 1), (10, 1), (11, 1), (12, 1)
;

-- INSERT Artists
INSERT INTO pods_artists (pods_id, artist_id)
VALUES
    -- POD
    --The Joe Rogan Experience
    (1, 1), -- Joe Rogan
    (2, 1), -- Joe Rogan
    (3, 1), -- Joe Rogan
    (4, 1), -- Joe Rogan
    -- Features
    (1, 2), -- Elon Musk
    (2, 4), -- Kevin Hart
    (3, 3), -- Quentin Tarantino
    (4, 5), -- Neil deGrasse Tyson

    --Lex Fridman
    (5, 6), -- Lex Fridman
    (6, 6), -- Lex Fridman
    (7, 6), -- Lex Fridman
    (8, 6), -- Lex Fridman
    -- Features
    (5, 2), -- Elon Musk
    (6, 7), -- Tucker Carlsson
    (7, 8), -- Kanye West
    (8, 9), -- Paul Rosolie

    --Theo Von
    (9, 10), -- Theo Von
    (10, 10), -- Theo Von
    (11, 10), -- Theo Von
    (12, 10), -- Theo Von
    -- Features
    (9, 11), -- Bobby Lee
    (10, 12), -- KSI
    (11, 13), -- Druski
    (12, 14) -- Post Malone
;

-- INSERT ALbums
INSERT INTO pods_albums (pods_id, album_id)
VALUES
    -- MUSIC
    -- The Joe Rogan Experience
    (1, 1), -- The Joe Rogan Experience
    (2, 1), -- The Joe Rogan Experience
    (3, 1), -- The Joe Rogan Experience
    (4, 1), -- The Joe Rogan Experience

    -- Lex Fridman Podcast FIXAR SEN
    (5, 2), -- Lex Fridman Podcast
    (6, 2), -- Lex Fridman Podcast
    (7, 2), -- Lex Fridman Podcast
    (8, 2), -- Lex Fridman Podcast

    -- This Past Weekend (Theo Von's)
    (9, 3), -- This Past Weekend
    (10, 3), -- This Past Weekend
    (11, 3), -- This Past Weekend
    (12, 3) -- This Past Weekend
;