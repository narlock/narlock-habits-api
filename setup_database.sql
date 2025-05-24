# USE <your_database_name>

-- Habit table
CREATE TABLE habit (
    name VARCHAR(250),
    user_id CHAR(36),
    PRIMARY KEY (name, user_id),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Habit log table
CREATE TABLE habit_log (
    habit_name VARCHAR(250),
    user_id CHAR(36),
    completed_date DATE,
    PRIMARY KEY (habit_name, user_id, completed_date),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (habit_name, user_id) REFERENCES habit(name, user_id) ON DELETE CASCADE
);
