package fr._42.html_forms_classes;
import fr._42.annotations.HtmlForm;
import fr._42.annotations.HtmlInput;



@HtmlForm(fileName = "user_form.html", action = "/users", method = "get")
public class UserForm{
    @HtmlInput(type = "text", name = "first_name", placeholder = "Enter First Name")
    private String firstName;
    @HtmlInput(type = "text", name = "last_name", placeholder = "Enter Last Name")
    private String lastName;
    @HtmlInput(type = "password", name = "password", placeholder = "Enter Password")
    private String password;
}