package day01.ex00;

public class Program {
    public static void main(String[] args) {
        User achraf = new User(1, "achraf", 1000.0);
        achraf.display();
        User abdo = new User(2, "abdo", 3000);
        abdo.display();

        System.out.println("-------------------------------------------");

        Transaction credit = new Transaction(achraf, abdo, "debit", 1100.0);
        // Transaction debit = new Transaction(achraf, abdo, "debit", 600);
        credit.display();
        // debit.display();

        achraf.display();
        abdo.display();

    }
}