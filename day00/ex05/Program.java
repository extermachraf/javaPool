package day00.ex05;

import java.util.Scanner;

public class Program {
    static Scanner scanner = new Scanner(System.in);

    // display errore messages
    static void print_error(String message) {
        System.out.println(message);
        scanner.close();
        System.exit(-1);
    }

    // handle the students list
    static void nameIsValid(String name, String[] students_list) {
        // check if name is valide
        if (name.contains(" "))
            print_error("Please enter a valid name. Spaces are not allowed in the name.");

        // check duplicated name
        for (String student : students_list) {
            if (student != null)
                if (student.equals(name))
                    print_error("Student already exist in the list.");
        }
    }

    static String[] listOfStudents() {
        // fill student names
        String[] input = new String[10];
        int counter = 0;
        for (int i = 0; i < 10; i++) {
            System.out.print("-->");
            String student_name = scanner.nextLine();
            if (student_name.equals("."))
                break;
            // check the name of student
            nameIsValid(student_name, input);
            input[i] = student_name;
            counter++;
        }
        String[] students_list = new String[counter];
        System.arraycopy(input, 0, students_list, 0, counter);
        return students_list;
    }

    // handle the timetable

    public static void HourDayisValid(int houre, String day) {
        if (!(houre >= 1 && houre <= 6))
            print_error("the sessions houre should be betweeb 1 and 6");
        if (!(day.equals("MO") || day.equals("TU") || day.equals("WE") || day.equals("TH") || day.equals("FR")
                || day.equals("SA") || day.equals("SU")))
            print_error(
                    "Error: The day should be in the specific format (e.g., 'MO' for Monday, 'TU' for Tuesday, etc.).\nValid day codes are:\n'MO' - Monday\n'TU' - Tuesday\n'WE' - Wednesday\n'TH' - Thursday\n'FR' - Friday\n'SA' - Saturday\n'SU' - Sunday");

    }

    public static String[][] timeTable() {
        String[][] input = new String[10][2];
        int counter = 0;
        for (int i = 0; i < 10; i++) {
            System.out.print("-->");
            String info = scanner.nextLine();
            if (info.equals("."))
                break;
            // check if the info entred by the user is valid
            String[] splitInfo = info.split(" ");
            if (splitInfo.length != 2)
                print_error("Please enter the data in the following format: <Hour of the session> <Day>");
            int houre = Integer.parseInt(splitInfo[0]);
            String day = splitInfo[1];
            HourDayisValid(houre, day);

            // store info
            input[i][0] = splitInfo[0];
            input[i][1] = day;
            counter++;
        }
        String[][] houreOfday = new String[counter][2];
        System.arraycopy(input, 0, houreOfday, 0, counter);
        return houreOfday;
    }

    public static void main(String args[]) {
        String[] students_list = listOfStudents(); // get the student lists
        String[][] time_tables = timeTable();

        for (String[] time_table : time_tables) {
            System.out.println("houre :" + time_table[0]);
            System.out.println("day :" + time_table[1]);
        }
    }
}
