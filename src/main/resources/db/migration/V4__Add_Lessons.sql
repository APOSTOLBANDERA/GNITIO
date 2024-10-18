CREATE TABLE IF NOT EXISTS lessons (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    type VARCHAR(255) NOT NULL,
    content TEXT NOT NULL,
    file_data BYTEA,  -- Для хранения файла в виде бинарных данных
    file_name VARCHAR(255),  -- Имя файла
    module_id BIGINT NOT NULL,
    FOREIGN KEY (module_id) REFERENCES modules (id) ON DELETE CASCADE
);