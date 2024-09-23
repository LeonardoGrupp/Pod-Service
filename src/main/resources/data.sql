CREATE TABLE IF NOT EXISTS pods (
    id INT AUTO_INCREMENT PRIMARY KEY,
    type VARCHAR(255) NOT NULL,
    title VARCHAR(250) NOT NULL,
    url VARCHAR(250) NOT NULL,
    release_date DATE NOT NULL,
    play_counter INT DEFAULT 0,
    likes INT DEFAULT 0,
    dis_likes INT DEFAULT 0
    )
;

CREATE TABLE IF NOT EXISTS genres (
    id INT AUTO_INCREMENT PRIMARY KEY,
    genre VARCHAR(255) NOT NULL,
    total_likes INT DEFAULT 0,
    total_plays INT DEFAULT 0
    )
;

CREATE TABLE IF NOT EXISTS pods_genres (
    pods_id INT,
    genre_id INT,
    FOREIGN KEY(pods_id) REFERENCES pods(id),
    FOREIGN KEY(genre_id) REFERENCES genres(id)
    )
;

CREATE TABLE IF NOT EXISTS pods_artists (
    pods_id INT,
    artist_id INT,
    FOREIGN KEY(pods_id) REFERENCES pods(id),
    FOREIGN KEY(artist_id) REFERENCES artist(id)
    )
;

CREATE TABLE IF NOT EXISTS pods_albums (
    pods_id INT,
    album_id INT,
    FOREIGN KEY(pods_id) REFERENCES pods(id),
    FOREIGN KEY(album_id) REFERENCES album(id)
    )
;

-- INSERT Genres
INSERT INTO genres (id, genre, total_likes, total_plays)
VALUES
    (10, 'Comedy', 0, 0),
    (11, 'Movies', 0, 0),
    (12, 'Crime', 0, 0),
    (13, 'Politics', 0, 0),
    (14, 'Business', 0, 0),
    (15, 'Economy', 0, 0),
    (16, 'Sports', 0, 0),
    (17, 'Fashion', 0, 0),
    (18, 'Space', 0, 0)
;

INSERT INTO pods (id, type, title, url, release_date, play_counter, likes, dis_likes)
VALUES
    -- POD
    -- Joe Rogan
    (1, 'Pod', '#1169', 'url66', '2006-02-07', 0, 0, 0), (2, 'Pod','#1278', 'url67', '2006-02-07', 0, 0, 0), (3, 'Pod', '#1675', 'url68', '2006-02-07', 0, 0, 0), (4, 'Pod', '#919', 'url69', '2006-02-07', 0, 0, 0),

    -- Lex Fridman
    (5, 'Pod', 'Neuralink and the Guture of Humanity', 'url70', '2006-02-07', 0, 0, 0), (6, 'Pod', 'Putin, Navalny, Trump, CIA, NSA, War, Politics & Freedom', 'url71', '2006-02-07', 0, 0, 0), (7, 'Pod', 'Kanye West Interview', 'url72', '2006-02-07', 0, 0, 0), (8, 'Pod', 'Jungle, Apex Predators, Aliens, Uncontacted Tribes and God', 'url73', '2006-02-07', 0, 0, 0),

    -- Theo Von
    (9, 'Pod', '#530', 'url74', '2006-02-07', 0, 0, 0), (10, 'Pod', '#514', 'url75', '2006-02-07', 0, 0, 0), (11, 'Pod', '#489', 'url76', '2006-02-07', 0, 0, 0), (12, 'Pod', '#529', 'url77', '2006-02-07', 0, 0, 0)
;

INSERT INTO pods_genres (pods_id, genre_id)
VALUES
    -- joe rogan episodes to genre
    (1, 14), (2, 10), (3, 11), (4, 18),

    -- lex fridman episodes to genre
    (5, 18), (6, 13), (7, 13), (7, 14), (8, 13),

    -- theo von episodes to genre
    (9, 10), (10, 10), (11, 10), (12, 10)
;

-- INSERT Artists
INSERT INTO pods_artists (pods_id, artist_id)
VALUES
    -- POD
    --The Joe Rogan Experience
    (1, 11), (2, 11), (3, 11), (4, 11), -- Joe Rogan
    -- Features
    (1, 12), -- Elon Musk
    (2, 14), -- Kevin Hart
    (3, 13), -- Quentin Tarantino
    (4, 15), -- Neil deGrasse Tyson

    --Lex Fridman
    (5, 16), (6, 16), (7, 16), (8, 16), -- Lex Fridman
    -- Features
    (5, 12), -- Elon Musk
    (6, 17), -- Tucker Carlson
    (7, 18), -- Kanye West
    (8, 19), -- Paul Rosolie

    --Theo Von
    (9, 20), (10, 20), (11, 20), (12, 20), -- Theo Von
    -- Features
    (9, 21), -- Bobby Lee
    (10, 22), -- KSI
    (11, 23), -- Druski
    (12, 24) -- Post Malone
;

-- INSERT ALbums
INSERT INTO pods_albums (pods_id, album_id)
VALUES
    -- The Joe Rogan Experience
    (1, 6), (2, 6), (3, 6), (4, 6), -- The Joe Rogan Experience

    -- Lex Fridman Podcast
    (5, 7), (6, 7), (7, 7), (8, 7), -- Lex Fridman Podcast

    -- This Past Weekend (Theo Von's)
    (9, 8), (10, 8), (11, 8), (12, 8) -- This Past Weekend
;