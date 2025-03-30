package fr._42.config;


import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.jdbc.core.JdbcTemplate;


import javax.sql.DataSource;
import javax.xml.crypto.Data;

@Configuration
@ComponentScan(basePackages = "fr._42")
@PropertySource("classpath:db.properties")
public class SocketsApplicationConfig {

    @Bean
    public String returnString(){
        return "Hello World";
    }

//    @Bean
//    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
//        return new PropertySourcesPlaceholderConfigurer();
//    }
//
//    @Bean
//    public JdbcTemplate dataSource() {
//        HikariConfig config = new HikariConfig();
//        config.setDriverClassName("${db.driverClassName}");
//        config.setJdbcUrl("${db.url}");
//        config.setUsername("${db.username}");
//        config.setPassword("${db.password}");
//        return new JdbcTemplate(new HikariDataSource(config));
//    }
}
