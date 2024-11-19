package day01.ex02;

public interface UsersList {
    void addUser(User user);

    User retrieveUserByID(int id) throws UserNotFoundException;

    User retrieveUserByIndex(int index) throws UserNotFoundException;

    int getNumberOfUsers();
}
