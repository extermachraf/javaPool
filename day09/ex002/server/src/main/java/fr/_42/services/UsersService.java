package fr._42.services;

import fr._42.exeptions.ServException;
import fr._42.models.User;

import java.rmi.ServerException;

public interface UsersService {
    User SignIn(String username, String password) throws ServException;
    User SignUp(String username, String password) throws ServException;
}
