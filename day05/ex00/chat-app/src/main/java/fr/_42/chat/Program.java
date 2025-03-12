package fr._42.chat;

import java.util.ArrayList;
import java.util.Objects;

import fr._42.chat.classes.User;

public class Program {
    public static void main(String[] args) {
        User achraf = new User( "achraf", "123", new ArrayList<>(), new ArrayList<>());
        User bob = new User( "achraf", "123", new ArrayList<>(), new ArrayList<>());

        User jack = new User( "jack", "123", new ArrayList<>(), new ArrayList<>());
        System.out.println(achraf.hashCode());
        System.out.println(jack.hashCode());
        System.out.println(achraf);

        System.out.println("equaliti = " + Objects.equals(achraf, jack));
    }
}
