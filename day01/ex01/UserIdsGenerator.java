
/**
 * The UserIdsGenerator class is a singleton that generates unique user IDs.
 * It ensures that each user ID is unique by incrementing an internal counter.
 * 
 * <p>This class follows the singleton design pattern, meaning only one instance
 * of this class can exist at any given time. The instance can be accessed using
 * the {@link #getInstance()} method.</p>
 * 
 * <p>Usage example:</p>
 * <pre>
 * {@code
 * UserIdsGenerator generator = UserIdsGenerator.getInstance();
 * int newId = generator.generateId();
 * }
 * </pre>
 */
package day01.ex01;

public class UserIdsGenerator {

    private static UserIdsGenerator instance;
    private int Id;

    private UserIdsGenerator() {
        this.Id = 0;
    }

    public static UserIdsGenerator getInstance() {
        if (instance == null)
            instance = new UserIdsGenerator();

        return instance;
    }

    public int generateId() {
        return ++this.Id;
    }
}
