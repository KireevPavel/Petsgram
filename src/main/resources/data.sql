
CREATE TABLE IF NOT EXISTS posts
(
    post_id INTEGER GENERATED BY DEFAULT AS IDENTITY,
    author VARCHAR,
    creation_date DATE NOT NULL,
    description VARCHAR(200) NOT NULL,
    photo_url VARCHAR(200) NOT NULL

);

CREATE TABLE IF NOT EXISTS users
(
    user_id INTEGER GENERATED BY DEFAULT AS IDENTITY ,
    email VARCHAR NOT NULL,
    login VARCHAR NOT NULL,
    name VARCHAR,
    birthday DATE NOT NULL

);