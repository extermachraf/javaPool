package fr._42.services;

import fr._42.exeptions.ServException;
import fr._42.models.User;
import fr._42.repositories.UserRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsersServiceImpl implements UsersService {
    private final UserRepositoryImpl userRepositoryImpl;
    PasswordEncoder passwordEncoder;

    @Autowired
    public UsersServiceImpl(PasswordEncoder passwordEncoder, UserRepositoryImpl userRepositoryImpl) {
        this.passwordEncoder = passwordEncoder;
        this.userRepositoryImpl = userRepositoryImpl;
    }

    @Override
    public User SignIn(String username, String password) throws ServException {
        Optional<User> user = userRepositoryImpl.findUserByUsername(username);
        if (user.isEmpty())
            throw new ServException("User not found");
        if(!passwordEncoder.matches(password, user.get().getPassword()))
            throw new ServException("Wrong password");
        System.out.println("welcome " + user.get().getUsername());
        return user.get();
    }

    @Override
    public User SignUp(String username, String password) throws ServException {
        if(userRepositoryImpl.findUserByUsername(username).isPresent())
            throw new ServException("Username is already in use");
        User user = userRepositoryImpl.createUser(new User(null, username, this.passwordEncoder.encode(password)));
        System.out.println("User created successfully");
        return user;
    }
}
