package fr._42.services;

import fr._42.models.User;
import fr._42.repositpries.user.UserRepoExeption;
import fr._42.repositpries.user.UsersRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsersServiceImpl implements UsersService {

    private final UsersRepositoryImpl usersRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UsersServiceImpl(UsersRepositoryImpl usersRepository) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public void signUp(String username, String password) throws UserRepoExeption {
        try {
            String hashedPassword = passwordEncoder.encode(password);
            User user = new User(username, hashedPassword);
            usersRepository.save(user);
            System.out.println("User signed up: "+ username);
        } catch (Exception e) {
            throw new UserRepoExeption(e.getMessage());
        }
    }
    @Override
    public Optional<User> signIn(String username, String password) throws UserRepoExeption {
        Optional<User> userOptional = usersRepository.findByUsername(username);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (usersRepository.checkPassword(password, user.getPassword())) {
                System.out.println("User signed in: " + username);
                return userOptional;
            } else {
                System.err.println("Incorrect password for user: " + username);
                return Optional.empty();
            }
        } else {
            throw new UserRepoExeption("User not found");
        }
    }

}
