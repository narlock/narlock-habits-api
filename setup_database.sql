# USE <your_database_name>

-- Habit table
CREATE TABLE habit (
    name VARCHAR(250),
    user_id CHAR(36),
    remind_time TIME,
    PRIMARY KEY (name, user_id)
);

-- Habit log table
CREATE TABLE habit_log (
    habit_name VARCHAR(250),
    user_id CHAR(36),
    completed_date DATE,
    PRIMARY KEY (habit_name, user_id, completed_date)
);
