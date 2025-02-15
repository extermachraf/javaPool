package repo.repositories;

import javax.sql.DataSource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import io.github.cdimascio.dotenv.Dotenv;

public class DatabaseConfig {

    public DataSource gDataSource() {

        Dotenv dotenv = Dotenv.load();

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(dotenv.get("DB_URL"));
        config.setUsername(dotenv.get("DB_USERNAME"));
        config.setPassword(dotenv.get("DB_PASSWORD"));

        config.setConnectionTimeout(Long.parseLong(dotenv.get("DB_CONNECTION_TIMEOUT", "30000")));
        return new HikariDataSource(config);
    }
}
