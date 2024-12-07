INSERT INTO
    users (login, password)
VALUES
    (
        'alice_smith',
        'alicepassword'
    ),
    (
        'bob_jones',
        'bobpassword'
    ),
    (
        'charlie_brown',
        'charliepassword'
    ),
    (
        'david_clark',
        'davidpassword'
    ),
    (
        'eve_adams',
        'evepassword'
    );


INSERT INTO
    chatrooms(name, owner_id)
    VALUES
        (
            'General',
            1
        ),
        (
            'Tech Talk',
            2
        ),
        (
            'Gaming',
            3
        ),
        (
            'Music',
            4
        ),
        (
            'Movies',
            5
        );

INSERT INTO
    messages(author_id, room_id, text, created_at)
VALUES
    (
        1,
        1,
        'Hello everyone!',
        '2023-10-01 10:00:00'
    ),
    (
        2,
        2,
        'Anyone here interested in Java?',
        '2023-10-01 10:05:00'
    ),
    (
        3,
        3,
        'What games are you playing?',
        '2023-10-01 10:10:00'
    ),
    (
        4,
        4,
        'Check out this new song!',
        '2023-10-01 10:15:00'
    ),
    (
        5,
        5,
        'What movies have you watched recently?',
        '2023-10-01 10:20:00'
    )