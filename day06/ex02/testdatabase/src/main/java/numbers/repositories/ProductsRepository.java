package numbers.repositories;

import java.util.List;
import java.util.Optional;

import numbers.models.Product;

public interface ProductsRepository {

    List<Product> findAll();

    Optional<Product> findById(Long id);

    void update(Product product);

    void save(Product product);

    void delete(Long id);
}
