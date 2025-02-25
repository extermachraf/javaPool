package fr._42.services;

import fr._42.models.User;
import fr._42.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UsersServiceImpl implements UsersService {

    private final UsersRepository usersRepository;

    @Autowired
    public UsersServiceImpl(@Qualifier("jdbc") UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public String signUp(String email) {
        String tempPassword = generateTempPassword();
        usersRepository.save(new User(email, tempPassword));
        return tempPassword;
    }

    // Implement other methods from CrudRepository interface
    @Override public User findById(Long id) { return usersRepository.findById(id); }
    @Override public List<User> findAll() { return usersRepository.findAll(); }
    @Override public void save(User entity) { usersRepository.save(entity); }
    @Override public void update(User entity) { usersRepository.update(entity); }
    @Override public void delete(Long id) { usersRepository.delete(id); }
    @Override public Optional<User> findByEmail(String email) { return usersRepository.findByEmail(email); }

    private String generateTempPassword() {
        return UUID.randomUUID().toString().substring(0, 8);
    }
}