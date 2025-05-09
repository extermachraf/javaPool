package fr._42.classes;

import java.util.StringJoiner;

public class User {
    private String firstName;
    private String lastName;
    private int height;

    //empty constructor
    public User(){
        this.firstName = "default";
        this.lastName = "default";
        this.height = 0;
    }

    public User(String firstName, String lastName, int height){
        this.firstName = firstName;
        this.lastName = lastName;
        this.height = height;
    }

    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public int getHeight() {
        return height;
    }
    public void setHeight(int height) {
        this.height = height;
    }


    //methods
    public int grow(int value){
        this.height += value;
        return this.height;
    }



    @Override
    public String toString() {
            return new StringJoiner(", ", User.class.getSimpleName() + "[", "]")
                    .add("firstName='" + firstName + "'")
                    .add("lastName='" + lastName + "'")
                    .add("height=" + height)
                    .toString();
    }
}
