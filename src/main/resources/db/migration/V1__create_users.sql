CREATE TABLE users (
    username VARCHAR(64) PRIMARY KEY,
    password VARCHAR(255) NOT NULL,
    apikey VARCHAR(64) UNIQUE

);

CREATE INDEX idx_users_apikey ON users(apikey);
