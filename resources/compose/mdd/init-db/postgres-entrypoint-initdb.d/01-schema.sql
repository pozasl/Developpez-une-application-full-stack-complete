-- Users table
CREATE TABLE IF NOT EXISTS users (id BIGSERIAL PRIMARY KEY, email VARCHAR(255) NOT NULL, name VARCHAR(255) NOT NULL, password VARCHAR(255) NOT NULL, created_at TIMESTAMP DEFAULT NOW(), updated_at TIMESTAMP DEFAULT NOW());
CREATE UNIQUE INDEX IF NOT EXISTS email_index ON users(email);
-- Feeds table
CREATE TABLE IF NOT EXISTS feeds(
    user_id BIGINT NOT NULL,
    post_ref VARCHAR(24) NOT NULL,
    created_at TIMESTAMP DEFAULT now(),
    CONSTRAINT PK_FEED PRIMARY KEY (user_id, post_ref)
);
CREATE INDEX IF NOT EXISTS feed_user_id_idx ON feeds(user_id);