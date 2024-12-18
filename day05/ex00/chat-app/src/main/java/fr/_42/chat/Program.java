package fr._42.chat;

import java.util.ArrayList;
import java.util.Objects;

import fr._42.chat.classes.User;

public class Program {
    public static void main(String[] args) {
        User achraf = new User(0, "achraf", "123", new ArrayList<>(), new ArrayList<>());
        User bob = new User(0, "achraf", "123", new ArrayList<>(), new ArrayList<>());

        System.out.println(achraf.hashCode());
        System.out.println(bob.hashCode());
        System.out.println(achraf);

        System.err.println("equaliti = " + Objects.equals(achraf, bob));
    }
}
