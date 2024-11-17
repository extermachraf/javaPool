
/**
 * The Program class is designed to track weekly test scores and generate a 
 * graphical representation of the minimum score for each week. The program 
 * reads input from the user, validates the input, and builds a progress 
 * report in the form of a graph.
 * 
 * The class contains the following methods:
 * 
 * - stringToInt(String[] grades): Converts an array of strings to an array of integers.
 * - check_week(String week, int order): Validates the format and order of the week input.
 * - check_tests(String tests): Validates the format and range of the test scores input.
 * - build_statistics(int order, String tests): Builds a graphical representation of the minimum test score for a given week.
 * 
 * The main method runs an interactive loop, prompting the user for weekly data 
 * and generating the progress report until the user inputs "42", which terminates 
 * the program and prints the final report.
 */
package day00.ex03;

import java.util.Scanner;

public class Program {
    static StringBuilder progressData = new StringBuilder();

    static int[] stringToInt(String[] grades) {
        int[] tests_scores = new int[grades.length];
        for (int i = 0; i < grades.length; i++) {
            tests_scores[i] = Integer.parseInt(grades[i]);
        }
        return tests_scores;
    }

    static boolean check_week(String week, int order) {
        if (!week.startsWith("week"))
            return false;
        String[] split = week.split(" ");
        if (split.length != 2)
            return false;
        int week_number = Integer.parseInt(split[1]);
        if (week_number != order || week_number < 1 || week_number > 18)
            return false;
        return true;
    }

    static boolean check_tests(String tests) {
        String[] split_tests = tests.split(" ");
        if (split_tests.length != 5)
            return false;
        int[] tests_scores = new int[split_tests.length];
        for (int i = 0; i < split_tests.length; i++) {
            tests_scores[i] = Integer.parseInt(split_tests[i]);
            if (tests_scores[i] < 1 || tests_scores[i] > 9)
                return false;
        }
        return true;
    }

    static StringBuilder build_statistics(int order, String tests) {
        StringBuilder week_statistic = new StringBuilder();
        String[] grades = tests.split(" ");
        int[] tests_scores = stringToInt(grades);

        // apprnd the week number
        week_statistic.append("Week " + order + " ");

        // find the smaller number
        int min = tests_scores[0];
        for (int i = 1; i < tests_scores.length; i++) {
            if (tests_scores[i] < min)
                min = tests_scores[i];
        }

        // draw the graph
        for (int i = 0; i < min; i++)
            week_statistic.append("=");
        week_statistic.append(">\n");

        return week_statistic;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int order = 0; // using order to keep tracking the order of weeks
        while (true) {
            System.out.print("-->");
            String week = scanner.nextLine();
            if (week.equals("42")) {
                // deleat the last \n and print the graph of statistics
                if (progressData.length() > 0 && progressData.charAt(progressData.length() - 1) == '\n')
                    progressData.deleteCharAt(progressData.length() - 1);
                System.out.println(progressData);
                scanner.close();
                System.exit(0);
            }
            order++;
            // check if week + week_number is in goood shape
            if (!check_week(week, order)) {
                System.err.println("illegalArgument");
                scanner.close();
                System.exit(-1);
            }
            System.out.print("-->");
            String tests = scanner.nextLine();
            // check if scores is in goood shape
            if (!check_tests(tests)) {
                System.err.println("illegalArgument");
                scanner.close();
                System.exit(-1);
            }
            progressData.append(build_statistics(order, tests));
        }
    }
}
