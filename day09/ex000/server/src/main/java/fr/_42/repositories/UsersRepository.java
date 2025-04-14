package fr._42.repositories;

import fr._42.models.User;

import java.util.Optional;

public interface UsersRepository {
    User createUser(User user);
    Optional<User> findUserByUsername(String username);
}
