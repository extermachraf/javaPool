package fr._42.services;

import fr._42.repositories.UsersRepository;

public interface UsersService extends UsersRepository {
    String signUp(String email);
}
