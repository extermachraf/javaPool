package fr._42;
import fr._42.intefaces.Printer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        Printer printer = context.getBean("printerWithPrefix", Printer.class);
        printer.displayMessage("HEllo!");
    }
}