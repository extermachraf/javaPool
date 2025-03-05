package fr._42.repositpries.user;

import java.util.List;
import java.util.Optional;

public interface CrudRepository<T> {
    Optional<T> findById(long id) throws UserRepoExeption;
    List<T> findAll() throws UserRepoExeption;
    void save(T entity) throws Exception;
    void update(T entity) throws UserRepoExeption;
    void delete(long id) throws UserRepoExeption;
}
