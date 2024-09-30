DROP INDEX IF EXISTS feed_user_id_idx;
DROP TABLE IF EXISTS feeds;
---
CREATE TABLE feeds (id BIGSERIAL PRIMARY KEY, user_id BIGINT NOT NULL, topic_id VARCHAR(24) NOT NULL),
CREATE INDEX IF NOT EXISTS feed_user_id_idx ON users(email);