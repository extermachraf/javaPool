package fr._42.services;

import fr._42.exeptions.ServException;
import fr._42.models.User;
import fr._42.repositories.users.UserRepositoryImpl;
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
            return null;
        if(!passwordEncoder.matches(password, user.get().getPassword()))
            return null;
        return user.get();
    }

    @Override
    public User SignUp(String username, String password) throws ServException {
        if(userRepositoryImpl.findUserByUsername(username).isPresent())
            return null;
        User user = userRepositoryImpl.createUser(new User(null, username, this.passwordEncoder.encode(password)));
        return user;
    }
}
