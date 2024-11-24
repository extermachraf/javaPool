package day02.ex00;

import java.io.IOException;
import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        while (true) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("-->");
            String path = scanner.nextLine().trim();
            if (path.equals("42")) {
                scanner.close();
                System.exit(0);
            } else {
                try {
                    FileSignature sig = new FileSignature(
                            "/home/achraf/42-projects/javaPool/day02/ex00/utils/Signatures.txt");
                    try {
                        sig.store_data("/home/achraf/42-projects/javaPool/day02/ex00/utils/result.txt",
                                path);
                    } catch (IOException exception) {
                        System.out.println("Error : " + exception.getMessage());
                        scanner.close();
                        System.exit(0);
                    }
                } catch (IOException exception) {
                    System.err.println("Error : " + exception.getMessage());
                    System.exit(1);
                }
            }
        }
    }
}
