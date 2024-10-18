CREATE TABLE IF NOT EXISTS events (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    title VARCHAR(255) NOT NULL,                    -- Заголовок события
    description TEXT NOT NULL,                      -- Описание события
    start_time TIMESTAMP NOT NULL,                  -- Время начала события
    end_time TIMESTAMP NOT NULL,                    -- Время окончания события
    user_id BIGINT NOT NULL,                        -- Внешний ключ на пользователя, создавшего событие
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE -- При удалении пользователя, связанные события удаляются
);