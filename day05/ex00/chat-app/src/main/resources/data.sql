INSERT INTO
    users (login, password)
VALUES
    ('alice_smith', 'alicepassword'),
    ('bob_jones', 'bobpassword'),
    ('charlie_brown', 'charliepassword'),
    ('david_clark', 'davidpassword'),
    ('eve_adams', 'evepassword'),
    ('frank_white', 'frankpassword'),
    ('grace_lee', 'gracepassword'),
    ('henry_kim', 'henrypassword'),
    ('isabella_martin', 'isabellapassword'),
    ('jack_taylor', 'jackpassword'),
    ('karen_moore', 'karenpassword'),
    ('liam_hill', 'liampassword'),
('mia_allen', 'miapassword'),
    ('noah_king', 'noahpassword'),
    ('olivia_scott', 'oliviapassword');



INSERT INTO
    chatrooms (name, owner_id)
VALUES
    ('General', 1),
    ('Tech Talk', 2),
    ('Gaming', 3),
    ('Music', 4),
    ('Movies', 5),
    ('Cooking', 6),
    ('Travel', 7),
    ('Photography', 8),
    ('Art', 9),
    ('Fitness', 10),
    ('Sports', 11),
    ('Books', 12),
    ('Fashion', 13),
    ('History', 14),
    ('Science', 15);


INSERT INTO
    messages (author_id, room_id, text, created_at)
VALUES
    -- User 1 (Alice) socializes in 3 rooms
    (1, 1, 'Hello everyone!', '2023-10-01 10:00:00'),
    (1, 3, 'What games are you playing?', '2023-10-01 11:00:00'),
    (1, 5, 'I love movies!', '2023-10-01 12:00:00'),
    
    -- User 2 (Bob) socializes in 3 rooms
    (2, 2, 'Anyone here interested in Java?', '2023-10-01 10:05:00'),
    (2, 4, 'Any music recommendations?', '2023-10-01 11:05:00'),
    (2, 6, 'I just cooked something amazing!', '2023-10-01 12:05:00'),

    -- User 3 (Charlie) socializes in 2 rooms
    (3, 3, 'What games are you playing?', '2023-10-01 10:10:00'),
    (3, 7, 'Traveling is my passion!', '2023-10-01 11:10:00'),

    -- User 4 (David) socializes in 3 rooms
    (4, 4, 'Check out this new song!', '2023-10-01 10:15:00'),
    (4, 8, 'Photography tips?', '2023-10-01 11:15:00'),
    (4, 10, 'Fitness motivation for today?', '2023-10-01 12:15:00'),

    -- User 5 (Eve) socializes in 2 rooms
    (5, 5, 'What movies have you watched recently?', '2023-10-01 10:20:00'),
    (5, 9, 'Anyone interested in art exhibitions?', '2023-10-01 11:20:00'),

    -- User 6 (Frank) socializes in 2 rooms
    (6, 6, 'Any good recipes to share?', '2023-10-01 10:25:00'),
    (6, 11, 'Did you watch the game last night?', '2023-10-01 11:25:00'),

    -- User 7 (Grace) socializes in 3 rooms
    (7, 7, 'I love traveling to new places!', '2023-10-01 10:30:00'),
    (7, 12, 'What books are you reading?', '2023-10-01 11:30:00'),
    (7, 14, 'I just finished a history documentary.', '2023-10-01 12:30:00'),

    -- User 8 (Henry) socializes in 2 rooms
    (8, 8, 'What camera do you use?', '2023-10-01 10:35:00'),
    (8, 13, 'Latest fashion trends?', '2023-10-01 11:35:00'),

    -- User 9 (Isabella) socializes in 2 rooms
    (9, 9, 'Art exhibitions coming up?', '2023-10-01 10:40:00'),
    (9, 15, 'Science news update!', '2023-10-01 11:40:00'),

    -- User 10 (Jack) socializes in 3 rooms
    (10, 10, 'Favorite fitness routine?', '2023-10-01 10:45:00'),
    (10, 11, 'Great match last night!', '2023-10-01 11:45:00'),
    (10, 15, 'Exciting science breakthrough!', '2023-10-01 12:45:00'),

    -- Additional messages to fill up rooms with interactions
    (1, 2, 'I just started learning Java!', '2023-10-02 10:00:00'),
    (2, 1, 'Good morning everyone!', '2023-10-02 10:10:00'),
    (3, 4, 'Listening to some great music right now.', '2023-10-02 10:20:00'),
    (4, 3, 'Playing an awesome game today!', '2023-10-02 10:30:00'),
    (5, 6, 'Cooking something new tonight.', '2023-10-02 10:40:00'),
    (6, 9, 'I just visited an amazing art gallery.', '2023-10-02 10:50:00');
