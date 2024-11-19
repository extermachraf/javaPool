package day01.ex04;

public class UsersArrayList implements UsersList {
    private User[] users;

    public UsersArrayList() {
        users = new User[10];
    }

    private void addStorage() {
        int newStorage = this.users.length + this.users.length / 2;
        User[] tmp = new User[newStorage];
        for (int i = 0; i < this.users.length; i++) {
            tmp[i] = this.users[i];
        }
        this.users = tmp;
    }

    @Override
    public void addUser(User user) {
        for (int i = 0; i < users.length; i++) {
            if (users[i] == null) {
                users[i] = user;
                return;
            }
        }
        this.addStorage();
        this.addUser(user); // Retry adding after resizing
    }

    @Override
    public User retrieveUserByID(int id) throws UserNotFoundException {
        for (int i = 0; i < this.users.length; i++) {
            if (this.users[i] != null && this.users[i].getId() == id) {
                return this.users[i];
            }
        }
        throw new UserNotFoundException("User with ID " + id + " not found.");
    }

    @Override
    public User retrieveUserByIndex(int index) throws UserNotFoundException {
        if (index >= 0 && index < users.length && users[index] != null) {
            return users[index];
        }
        throw new UserNotFoundException("User with index " + index + " not found.");
    }

    @Override
    public int getNumberOfUsers() {
        int count = 0;
        for (User user : users) {
            if (user != null) {
                count++;
            }
        }
        return count;
    }
}
