-- Existing users table (assuming you have this)
CREATE TABLE IF NOT EXISTS users (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
);

-- New chatrooms table
CREATE TABLE IF NOT EXISTS chatrooms (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    created_by BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Join table for users and chatrooms (many-to-many relationship)
CREATE TABLE IF NOT EXISTS user_chatrooms (
    user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    chatroom_id BIGINT NOT NULL REFERENCES chatrooms(id) ON DELETE CASCADE,
    last_accessed TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (user_id, chatroom_id)
);

-- Modified messages table (add chatroom reference)
CREATE TABLE IF NOT EXISTS messages (
    id BIGSERIAL PRIMARY KEY,
    sender_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    chatroom_id BIGINT NOT NULL REFERENCES chatrooms(id) ON DELETE CASCADE,
    content TEXT NOT NULL,
    sent_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Indexes for performance optimization
CREATE INDEX IF NOT EXISTS idx_messages_chatroom ON messages(chatroom_id);
CREATE INDEX IF NOT EXISTS idx_messages_sent_time ON messages(sent_time);
CREATE INDEX IF NOT EXISTS idx_user_chatrooms_access ON user_chatrooms(last_accessed);