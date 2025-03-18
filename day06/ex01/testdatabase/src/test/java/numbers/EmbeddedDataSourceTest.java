package numbers;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

public class EmbeddedDataSourceTest {

    private DataSource dataSource;

    // Initialize the DataSource using EmbeddedDatabaseBuilder
    @BeforeEach
    public void init() {
        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        EmbeddedDatabase db = builder
                .setType(EmbeddedDatabaseType.HSQL)
                .addScript("schema.sql")
                .addScript("data.sql")
                .build();
        this.dataSource = db;
    }

    @Test
    public void testGetConnection() throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            assertNotNull(connection, "Connection should not be null");
        }
    }

    @Test
    public void testSchemaCreation() throws SQLException {
        try (Connection connection = dataSource.getConnection(); Statement statement = connection.createStatement()) {
            String query = "SELECT * FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = 'PRODUCT'";
            try (ResultSet rs = statement.executeQuery(query)) {
                assertTrue(rs.next(), "The schema should have created the table 'product'");
            }
        }
    }

    @Test
    public void testProductTableData() throws SQLException {
        try (Connection connection = dataSource.getConnection(); Statement statement = connection.createStatement()) {

            // Query the "product" table to check if any data was inserted
            String query = "SELECT * FROM product";

            try (ResultSet rs = statement.executeQuery(query)) {
                assertTrue(rs.next(), "There should be data in the 'product' table");
            }
        }
    }
}
