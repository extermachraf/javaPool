package fr._42.interfaces;

import javax.management.BadAttributeValueExpException;
import java.sql.SQLException;
import java.util.List;

public interface CrudRepository<T> {
    T findById(Long id) throws SQLException;
    List<T> findAll() throws SQLException;
    void save(T entity) throws SQLException, BadAttributeValueExpException;
    void update(T entity) throws BadAttributeValueExpException, SQLException;
    void delete(Long id) throws SQLException , BadAttributeValueExpException;
}
