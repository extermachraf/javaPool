package fr._42;
import fr._42.createFormService.CreateFormService;
import fr._42.exeptions.CreateHTMLExeption;
import fr._42.html_forms_classes.UserForm;
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        try {
            UserForm userForm = new UserForm();
            CreateFormService form = new CreateFormService(userForm);
            form.create();
        } catch (CreateHTMLExeption e){
            System.err.println(e.getMessage());
        }
    }
}