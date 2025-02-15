package fr._42.tables;

import fr._42.annotations.OrmColumn;
import fr._42.annotations.OrmColumnId;
import fr._42.annotations.OrmEntity;

import java.util.List;

@OrmEntity(table = "simple_user")
public class User {
    @OrmColumnId
    private Long id;
    @OrmColumn(name = "first_name", length = 10)
    private String firstName;
    @OrmColumn(name = "last_name", length = 10)
    private String lastName;
    @OrmColumn(name = "age")
    private Integer age;


    //constructors
    public User() {}
    public User(String firstName, String lastName, Integer age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    public User(Long id, String firstName, String lastName, Integer age) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    //getters and setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
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
    public Integer getAge() {
        return age;
    }
    public void setAge(Integer age) {
        this.age = age;
    }
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("User [id=");
        builder.append(id);
        builder.append(", firstName=");
        builder.append(firstName);
        builder.append(", lastName=");
        builder.append(lastName);
        builder.append(", age=");
        builder.append(age);
        builder.append("]");
        return builder.toString();
    }
}
