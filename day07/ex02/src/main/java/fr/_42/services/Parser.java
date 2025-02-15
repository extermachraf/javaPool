package fr._42.services;

import fr._42.annotations.OrmColumn;
import fr._42.annotations.OrmColumnId;
import fr._42.annotations.OrmEntity;
import fr._42.exeptions.TablesExeptions;

import java.lang.reflect.Field;

public class Parser {
    public static void parseTables(Class<?>... tables) throws TablesExeptions {
        for (Class<?> table : tables) {
            parseTable(table);  // Call static helper method for each table
        }
    }
    private static void parseTable(Class<?> table) throws TablesExeptions {
        if (!table.isAnnotationPresent(OrmEntity.class)) {
            throw new TablesExeptions(table.getSimpleName() + " is not an @OrmEntity");
        }

        Field[] fields = table.getDeclaredFields();
        int ormColumnIdCount = 0;
        boolean ormColumnExists = false;

        for (Field field : fields) {
            Class<?> fieldType = field.getType();

            // Validate the field types
            if (!(fieldType.equals(String.class) || fieldType.equals(Integer.class) ||
                    fieldType.equals(Double.class) || fieldType.equals(Boolean.class) || fieldType.equals(Long.class))) {
                throw new TablesExeptions("Field " + field.getName() + " in " + table.getSimpleName() +
                        " has an invalid type: " + fieldType.getSimpleName());
            }

            // Validate annotations
            if (field.isAnnotationPresent(OrmColumn.class) && !ormColumnExists) {
                ormColumnExists = true;
            } else if (field.isAnnotationPresent(OrmColumnId.class)) {
                ormColumnIdCount++;
            }
        }

        // Ensure the table has at least one field with @OrmColumn
        if (!ormColumnExists) {
            throw new TablesExeptions(table.getSimpleName() +
                    " needs to have at least one field annotated with @OrmColumn.");
        }

        // Ensure the table has exactly one field with @OrmColumnId
        if (ormColumnIdCount != 1) {
            throw new TablesExeptions(table.getSimpleName() + " must have exactly one field annotated with @OrmColumnId.");
        }
    }
}
