package repo.repositories;

import javax.sql.DataSource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import io.github.cdimascio.dotenv.Dotenv;
public class DatabaseConfig {

    public DataSource gDataSource() {

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:postgresql://localhost:5432/chat");
        config.setUsername("achraf");
        config.setPassword("123");

        config.setConnectionTimeout(30000);
        return new HikariDataSource(config);
    }
}
