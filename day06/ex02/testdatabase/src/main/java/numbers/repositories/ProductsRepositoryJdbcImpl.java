package numbers.repositories;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;
import numbers.models.Product;

public class ProductsRepositoryJdbcImpl implements ProductsRepository {

    private final DataSource dataSource;

    public ProductsRepositoryJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Product> findAll() {
        List<Product> products = new ArrayList<>();
        String query = "SELECT * FROM product";
        try (Connection connection = dataSource.getConnection(); Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                Product product = new Product(
                        resultSet.getLong("identifier"),
                        resultSet.getString("name"),
                        resultSet.getBigDecimal("price")
                );
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    @Override
    public Optional<Product> findById(Long id) {
        String query = "SELECT * FROM product WHERE identifier = ?";
        try (Connection connection = dataSource.getConnection(); PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Product product = new Product(
                            resultSet.getLong("identifier"),
                            resultSet.getString("name"),
                            resultSet.getBigDecimal("price")
                    );
                    return Optional.of(product);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public void update(Product product) {
        String query = "UPDATE product SET name = ?, price = ? WHERE identifier = ?";
        try (Connection connection = dataSource.getConnection(); PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, product.getName());
            statement.setBigDecimal(2, product.getPrice());
            statement.setLong(3, product.getIdentifier());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void save(Product product) {
        String query = "INSERT INTO product (identifier, name, price) VALUES (?, ?, ?)";
        try (Connection connection = dataSource.getConnection(); PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setLong(1, product.getIdentifier());
            statement.setString(2, product.getName());
            statement.setBigDecimal(3, product.getPrice());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Long id) {
        String query = "DELETE FROM product WHERE identifier = ?";
        try (Connection connection = dataSource.getConnection(); PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
