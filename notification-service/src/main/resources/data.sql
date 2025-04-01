-- Create users table
CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    fullname VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    notification_enabled BOOLEAN NOT NULL DEFAULT TRUE
);

-- Insert sample data
INSERT INTO users (fullname, email, notification_enabled) VALUES
    ('John Doe', 'john.doe@example.com', TRUE),
    ('Jane Smith', 'jane.smith@example.com', TRUE),
    ('Bob Johnson', 'bob.johnson@example.com', FALSE),
    ('Alice Williams', 'alice.williams@example.com', TRUE),
    ('Charlie Brown', 'charlie.brown@example.com', FALSE);