package fr._42.services;

import com.zaxxer.hikari.HikariDataSource;
import fr._42.annotations.OrmColumn;
import fr._42.annotations.OrmColumnId;
import fr._42.annotations.OrmEntity;
import fr._42.exeptions.TablesExeptions;
import org.jetbrains.annotations.NotNull;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OrmManager {

    private List<Class<?>> tables = new ArrayList<>();
    private final DataSource dataSource = ConnectionService.createConnection();

    public OrmManager(Class<?>... tables) throws TablesExeptions, SQLException, ClassNotFoundException {

        Parser.parseTables(tables);
        removeAllTablesFromDatabase();
        this.tables = Arrays.asList(tables);
    }

    private void logStatement(String sql, boolean success, String errorMessage) {
        if (success) {
            System.out.println("LOG:  statement: " + sql);
            System.out.println("LOG:  statement executed successfully.");
        } else {
            System.err.println("ERROR: statement: " + sql);
            System.err.println("ERROR: statement execution failed: " + errorMessage);
        }
    }

    private void removeAllTablesFromDatabase() throws SQLException {
        try (Connection connection = dataSource.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT table_name FROM information_schema.tables WHERE table_schema = 'public' AND table_type = 'BASE TABLE'")) {
            while (rs.next()) {
                String tableName = rs.getString("table_name");
                try (Statement stmt2 = connection.createStatement()) {
                    String sql = "DROP TABLE IF EXISTS " + '"' + tableName + '"' + " CASCADE";
                    stmt2.executeUpdate(sql);
                    logStatement(sql, true, null); // Log table removal success
                }
            }
        }
    }

    private void executeUpdate(String sql) throws SQLException {
        try (Connection connection = this.dataSource.getConnection();
             Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(sql);
            logStatement(sql, true, null); // Log success
        } catch (SQLException e) {
            logStatement(sql, false, e.getMessage()); // Log error
            throw e;
        }
    }

    // Function to execute a query statement (e.g., SELECT) and return the ResultSet
    private ResultSet executeQuery(String sql) throws SQLException {
        try (Connection connection = this.dataSource.getConnection();
             Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            logStatement(sql, true, null); // Log success
            return rs;
        } catch (SQLException e) {
            logStatement(sql, false, e.getMessage()); // Log error
            throw e;
        }
    }

    public void save(Object entity) throws SQLException, IllegalAccessException, TablesExeptions {
        Class<?> clazz = entity.getClass();
        OrmEntity ormEntity = clazz.getAnnotation(OrmEntity.class);
        if (ormEntity == null) {
            throw new TablesExeptions("Class " + clazz.getSimpleName() + " is not an entity.");
        }
        StringBuilder sql = new StringBuilder("INSERT INTO ");
        sql.append('"').append(ormEntity.table()).append('"').append(" (");

        Field[] fields = clazz.getDeclaredFields();
        StringBuilder fieldNames = new StringBuilder();
        StringBuilder fieldValues = new StringBuilder();
        boolean firstField = true;

        for (Field field : fields) {
            field.setAccessible(true);
            if (field.isAnnotationPresent(OrmColumn.class)) {
                OrmColumn column = field.getAnnotation(OrmColumn.class);
                if (!firstField) {
                    fieldNames.append(", ");
                    fieldValues.append(", ");
                }
                fieldNames.append(column.name());
                fieldValues.append("'").append(field.get(entity)).append("'");
                firstField = false;
            }
        }

        sql.append(fieldNames.toString()).append(") VALUES (").append(fieldValues.toString()).append(");");
        executeUpdate(sql.toString());
    }

    public void update(Object entity) throws SQLException, IllegalAccessException, TablesExeptions {
        Class<?> clazz = entity.getClass();
        OrmEntity ormEntity = clazz.getAnnotation(OrmEntity.class);
        if (ormEntity == null) {
            throw new TablesExeptions("Class " + clazz.getSimpleName() + " is not an entity.");
        }

        StringBuilder sql = new StringBuilder("UPDATE ");
        sql.append('"').append(ormEntity.table()).append('"').append(" SET ");

        Field[] fields = clazz.getDeclaredFields();
        StringBuilder updateFields = new StringBuilder();
        Object idValue = null;
        boolean firstField = true;

        // Collect field values and primary key value
        for (Field field : fields) {
            field.setAccessible(true);
            if (field.isAnnotationPresent(OrmColumn.class)) {
                OrmColumn column = field.getAnnotation(OrmColumn.class);
                if (!firstField) {
                    updateFields.append(", ");
                }
                updateFields.append(column.name()).append(" = '").append(field.get(entity)).append("'");
                firstField = false;
            } else if (field.isAnnotationPresent(OrmColumnId.class)) {
                idValue = field.get(entity); // Get the value of the primary key (id)
            }
        }

        if (idValue == null) {
            throw new TablesExeptions("Entity must have an ID field annotated with @OrmColumnId.");
        }

        // Prepare the SQL query for checking existence
        String checkSql = "SELECT id FROM " + '"' + ormEntity.table() + '"' + " WHERE id = " + idValue + ";";
        System.out.println("Executing SQL: " + checkSql);

        try (Connection connection = dataSource.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet resultSet = stmt.executeQuery(checkSql)) {

            if (resultSet.next()) {
                // If the entity exists, perform the update
                sql.append(updateFields.toString())
                        .append(" WHERE id = ").append(idValue).append(";");
                System.out.println("Executing SQL: " + sql);
                executeUpdate(sql.toString());
                System.out.println("Successfully updated entity with id = " + idValue);
            } else {
                System.out.println("Entity with id = " + idValue + " not found. Skipping update.");
            }
        } catch (SQLException e) {
            throw new SQLException("Error while checking if entity exists: " + e.getMessage());
        }
    }
    public <T> T findById(Long id, Class<T> aClass) throws SQLException, IllegalAccessException, InstantiationException, NoSuchFieldException {
        // Check if the class is annotated with @OrmEntity
        OrmEntity ormEntity = aClass.getAnnotation(OrmEntity.class);
        if (ormEntity == null) {
            throw new TablesExeptions("Class " + aClass.getSimpleName() + " is not an entity.");
        }

        // Prepare the SQL query to find the entity by ID
        String sql = "SELECT * FROM " + '"' + ormEntity.table() + '"' + " WHERE id = ?;";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setLong(1, id);
            try (ResultSet resultSet = stmt.executeQuery()) {

                // Check if the entity was found
                if (resultSet.next()) {
                    // Create a new instance of the entity
                    T entity = aClass.newInstance();

                    // Map the result set to the fields of the entity
                    Field[] fields = aClass.getDeclaredFields();
                    for (Field field : fields) {
                        field.setAccessible(true);

                        if (field.isAnnotationPresent(OrmColumn.class)) {
                            OrmColumn column = field.getAnnotation(OrmColumn.class);
                            Object value = resultSet.getObject(column.name());
                            field.set(entity, value);
                        } else if (field.isAnnotationPresent(OrmColumnId.class)) {
                            // Handle the ID field
                            Object value = resultSet.getObject("id");
                            field.set(entity, value);
                        }
                    }
                    return entity;
                } else {
                    // No entity found with the given ID
                    System.out.println("Entity with id = " + id + " not found.");
                    return null;
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Error finding entity by id: " + e.getMessage());
        }
    }

    public void printTablesNames() throws TablesExeptions {
        for (Class<?> table : tables) {
            System.out.println(table.getSimpleName());
        }
    }

    private String javaTypeToSQLType(Type type) {
        String typeName = type.getTypeName();
        return switch (typeName) {
            case "java.lang.String" -> "VARCHAR";
            case "java.lang.Integer" -> "INTEGER";
            case "java.lang.Long" -> "BIGINT";
            case "java.lang.Double" -> "DOUBLE PRECISION";
            case "java.lang.Boolean" -> "BOOLEAN";
            default -> throw new TablesExeptions("Unsupported type: " + type);
        };
    }

    public void createTables() throws TablesExeptions, SQLException, ClassNotFoundException {
        for (Class<?> table : tables) {
            StringBuilder sql = new StringBuilder("CREATE TABLE IF NOT EXISTS ");
            OrmEntity ormEntity = table.getAnnotation(OrmEntity.class);
            String table_name = ormEntity.table();
            if (table_name == null || table_name.isEmpty())
                throw new TablesExeptions("Table " + table_name + " has no name");
            sql.append('"').append(table_name).append('"').append(" ( ");
            System.out.println("-------------------------");
            Field[] fields = table.getDeclaredFields();
            int totalFields = fields.length;
            int fieldCount = 0;
            for (Field field : fields) {
                fieldCount++;
                if (field.isAnnotationPresent(OrmColumnId.class)) {
                    sql.append(field.getName()).append(" BIGSERIAL PRIMARY KEY ");
                } else if (field.isAnnotationPresent(OrmColumn.class)) {
                    OrmColumn column = field.getAnnotation(OrmColumn.class);
                    if (javaTypeToSQLType(field.getType()).equals("VARCHAR")) {
                        sql.append(column.name()).append(' ').append("VARCHAR(").append(column.length()).append(")");
                    } else sql.append(column.name()).append(' ').append(javaTypeToSQLType(field.getType()));
                }
                if (fieldCount < totalFields) {
                    sql.append(", ");
                }
            }
            sql.append(");");
            executeUpdate(sql.toString());
            System.out.println("Creating table " + table_name + " with " + totalFields + " fields");
            System.out.println(sql.toString());
        }
    }
}

