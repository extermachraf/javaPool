package fr._42.repositpries;

import fr._42.models.User;

import java.util.Optional;

public interface UsersRepository extends CrudRepository<User> {
    Optional<User> findByUsername(String username);
}
