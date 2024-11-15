/*
 * Program: A Little Bit of Statistics
 *
 * Description:
 * This program collects and visualizes students' test results over multiple weeks.
 * The program accepts input for each week, consisting of a week identifier and five test scores for that week.
 * It validates the input for correctness, ensuring that:
 * - The week number is in the correct order (1 to 18).
 * - The test scores are within the range of 1 to 9 and there are exactly five scores.
 *
 * The program calculates the minimum test score for each week and generates a textual graph showing the student's
 * progress over time. Each week’s graph is represented by the "Week X" label followed by a series of equal signs
 * corresponding to the minimum grade that week.
 *
 * Once the input "42" is entered, the program terminates and outputs the progress graph, which shows the minimum
 * grade achieved for each week in order. If the input is invalid at any point, the program outputs an error message
 * and terminates.
 *
 * Allowed Functions:
 * - Input/Output: System.out, System.err, Scanner(System.in)
 * - Types: Primitive types, String
 * - Methods: String::equals
 *
 * Constraints:
 * - The input for each week is guaranteed to be in the form "Week X" followed by five integers for test scores.
 * - The maximum number of weeks is 18.
 * - String concatenation inside loops is discouraged to avoid performance issues.
 * 
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
