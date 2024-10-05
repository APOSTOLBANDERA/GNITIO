CREATE TABLE IF NOT EXISTS roles (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(100) NOT NULL,
    password VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS todo (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255),
    completed BOOLEAN,
    description TEXT,
    user_id BIGINT,
    CONSTRAINT fk_user
        FOREIGN KEY(user_id)
        REFERENCES users(id)
);