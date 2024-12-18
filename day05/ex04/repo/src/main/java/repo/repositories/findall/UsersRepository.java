package repo.repositories.findall;

import java.util.List;

import repo.repositories.User;
import repo.repositories.exeptions.NotSavedSubEntityException;

public interface UsersRepository {
    List<User> findAll(int page, int size) throws NotSavedSubEntityException;
}
