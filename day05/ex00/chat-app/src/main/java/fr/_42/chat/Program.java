package fr._42.chat;

import java.util.*;

import fr._42.chat.classes.*;

public class Program {
    public static void main(String[] args) {
        User achraf = new User(0, "achraf", "123", new ArrayList<>(), new ArrayList<>());
        User bob = new User(0, "achraf", "123", new ArrayList<>(), new ArrayList<>());

        System.out.println(achraf.hashCode());
        System.out.println(bob.hashCode());

        System.err.println("equaliti = " + Objects.equals(achraf, bob));
    }
}
