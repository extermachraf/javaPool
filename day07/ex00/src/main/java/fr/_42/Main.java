package fr._42;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;

import fr._42.exeptions.ReflectExceptions;
import fr._42.reflection.ReflectionService;
public class Main {

    public static void main(String[] args) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            ReflectionService service = new ReflectionService(reader);
            service.invokeService();
        } catch (IOException | ReflectExceptions | IllegalAccessException | NoSuchFieldException |
                 InvocationTargetException e) {
            System.err.println("an errore oxxured");
        }
    }
}