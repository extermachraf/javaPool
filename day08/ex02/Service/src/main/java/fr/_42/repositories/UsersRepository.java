package fr._42.repositories;

import fr._42.models.User;

import javax.management.BadAttributeValueExpException;
import java.sql.SQLException;
import java.util.Optional;

public interface UsersRepository extends CrudRepository<User> {
    Optional <User> findByEmail(String email);
}
