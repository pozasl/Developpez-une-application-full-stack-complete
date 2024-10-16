DROP INDEX IF EXISTS feed_user_id_idx;
DROP TABLE IF EXISTS feeds;

CREATE TABLE IF NOT EXISTS feeds(
    user_id BIGINT NOT NULL,
    post_ref VARCHAR(24) NOT NULL,
    created_at TIMESTAMP DEFAULT now(),
    CONSTRAINT PK_FEED PRIMARY KEY (user_id, post_ref)
);

CREATE INDEX IF NOT EXISTS feed_user_id_idx ON feeds(user_id);