package numbers;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import numbers.models.Product;
import numbers.repositories.ProductsRepository;
import numbers.repositories.ProductsRepositoryJdbcImpl;

public class ProductsRepositoryJdbcImplTest {

    private ProductsRepository productsRepository;

    final List<Product> EXPECTED_FIND_ALL_PRODUCTS = List.of(
            new Product(1L, "Product A", new BigDecimal("19.99")),
            new Product(2L, "Product B", new BigDecimal("29.99")),
            new Product(3L, "Product C", new BigDecimal("39.99")),
            new Product(4L, "Product D", new BigDecimal("49.99")),
            new Product(5L, "Product E", new BigDecimal("59.99"))
    );
    final Product EXPECTED_FIND_BY_ID_PRODUCT = new Product(1L, "Product A", new BigDecimal("19.99"));
    final Product EXPECTED_UPDATED_PRODUCT = new Product(1L, "Updated Product", new BigDecimal("25.99"));

    @BeforeEach
    public void setUp() {
        EmbeddedDatabase db = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.HSQL)
                .addScript("schema.sql")
                .addScript("data.sql")
                .build();

        productsRepository = new ProductsRepositoryJdbcImpl(db);
    }

    @Test
    public void testFindAll() {
        List<Product> products = productsRepository.findAll();
        assertEquals(EXPECTED_FIND_ALL_PRODUCTS, products, "Should return all products");
    }

    @Test
    public void testFindById() {
        Optional<Product> product = productsRepository.findById(1L);
        assertTrue(product.isPresent(), "Product with ID 1 should exist");
        assertEquals(EXPECTED_FIND_BY_ID_PRODUCT, product.get(), "Product should match expected");
    }

    @Test
    public void testUpdate() {
        productsRepository.update(EXPECTED_UPDATED_PRODUCT);
        Optional<Product> updatedProduct = productsRepository.findById(1L);
        assertTrue(updatedProduct.isPresent(), "Updated product should exist");
        assertEquals(EXPECTED_UPDATED_PRODUCT, updatedProduct.get(), "Updated product should match expected");
    }

    @Test
    public void testSave() {
        Product newProduct = new Product(0L, "New Product", new BigDecimal("39.99"));
        productsRepository.save(newProduct);

        // After saving, check if a product with the expected name and price is in the database
        Optional<Product> savedProduct = productsRepository.findById(newProduct.getIdentifier());
        assertTrue(savedProduct.isPresent(), "New product should be saved");
        assertEquals(newProduct.getName(), savedProduct.get().getName(), "Saved product should match expected name");
        assertEquals(newProduct.getPrice(), savedProduct.get().getPrice(), "Saved product should match expected price");
    }

    @Test
    public void testDelete() {
        productsRepository.delete(1L);
        Optional<Product> product = productsRepository.findById(1L);
        assertTrue(product.isEmpty(), "Product with ID 1 should be deleted");
    }
}
