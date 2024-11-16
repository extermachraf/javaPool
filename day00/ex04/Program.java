package day00.ex04;

import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        // Count the frequency of each character in the input
        int[] frequency = new int[65536];
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            frequency[c]++;
        }

        // Store characters that appear in the input
        int[] sortedChars = new int[65536];
        int uniqueCharCount = 0;

        for (int i = 0; i < 65536; i++) {
            if (frequency[i] > 0)
                sortedChars[uniqueCharCount++] = i;
        }

        // Sort the characters by frequency and lexicographically using bubble sort
        for (int i = 0; i < uniqueCharCount - 1; i++) {
            for (int j = i + 1; j < uniqueCharCount; j++) {
                int currentChar = sortedChars[i];
                int nextChar = sortedChars[j];

                if (frequency[currentChar] < frequency[nextChar]
                        || (frequency[currentChar] == frequency[nextChar] && currentChar > nextChar)) {
                    sortedChars[i] = nextChar;
                    sortedChars[j] = currentChar;
                }
            }
        }

        // Find the maximum frequency
        int maxFrequency = frequency[sortedChars[0]];

        // Scale the frequencies to fit within a height of 10
        int[] scaledHeights = new int[Math.min(uniqueCharCount, 10)];
        for (int i = 0; i < Math.min(uniqueCharCount, 10); i++) {
            int count = frequency[sortedChars[i]];
            scaledHeights[i] = (int) Math.ceil((double) count / maxFrequency * 10);
        }

        // Print the frequency numbers above the histogram bars
        for (int i = 0; i < Math.min(uniqueCharCount, 10); i++) {
            System.out.print(frequency[sortedChars[i]] + " ");
        }
        System.out.println();

        // Print the histogram vertically, scaling to fit within 10 rows
        for (int row = 10; row > 0; row--) {
            for (int i = 0; i < Math.min(uniqueCharCount, 10); i++) {
                int scaledHeight = scaledHeights[i];

                // Print the `#` if the scaled height is greater than or equal to the current
                // row
                if (scaledHeight >= row) {
                    System.out.print("# ");
                } else {
                    System.out.print("  "); // Space for alignment
                }
            }
            System.out.println(); // New line after each row
        }

        // Print the characters at the bottom
        for (int i = 0; i < Math.min(uniqueCharCount, 10); i++) {
            System.out.print((char) sortedChars[i] + " ");
        }
        System.out.println();
        scanner.close();
    }
}