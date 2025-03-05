package fr._42.services;

import fr._42.models.User;
import fr._42.repositpries.user.UserRepoExeption;

import java.util.Optional;

public interface UsersService {
    void signUp(String username, String password) throws UserRepoExeption;
    Optional<User> signIn(String username, String password) throws UserRepoExeption;
}
