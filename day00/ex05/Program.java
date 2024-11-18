
/**
 * This program manages student attendance for a specific timetable.
 * It allows users to input a list of students, a timetable of sessions, and attendance records.
 * The program validates the input data and displays the attendance in a tabular format.
 * 
 * The main functionalities include:
 * - Validating and storing student names.
 * - Validating and storing session hours and days.
 * - Validating and storing attendance records.
 * - Displaying the attendance records in a formatted table.
 * 
 * The program uses the following methods:
 * - print_error(String message): Displays an error message and exits the program.
 * - nameIsValid(String name, String[] students_list): Validates the student name.
 * - listOfStudents(): Collects and returns a list of student names.
 * - HourDayisValid(int houre, String day): Validates the session hour and day.
 * - timeTable(): Collects and returns a timetable of sessions.
 * - checkOneByOne(String[] info, String[] student_list, String[][] time_tables): Validates a single attendance record.
 * - checkAttendenceData(StringBuilder attendenceData, String[] student_list, String[][] time_tables): Validates and stores attendance records.
 * - pickAttendence(): Collects and returns attendance records.
 * 
 * The main method orchestrates the flow of the program by calling the above methods in sequence.
 * 
 * Usage:
 * 1. Run the program.
 * 2. Enter student names one by one. Enter '.' to finish.
 * 3. Enter session hours and days in the format "<Hour> <Day>". Enter '.' to finish.
 * 4. Enter attendance records in the format "<student name> <session hour> <day of the month> <attendance status>". Enter '.' to finish.
 * 5. The program will display the attendance records in a formatted table.
 */

package day00.ex05;

import java.util.Arrays;
import java.util.Scanner;
import java.time.LocalDate;

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

    static void HourDayisValid(int houre, String day) {
        if (!(houre >= 1 && houre <= 6))
            print_error("the sessions houre should be betweeb 1 and 6");
        if (!(day.equals("MO") || day.equals("TU") || day.equals("WE") || day.equals("TH") || day.equals("FR")
                || day.equals("SA") || day.equals("SU")))
            print_error(
                    "Error: The day should be in the specific format (e.g., 'MO' for Monday, 'TU' for Tuesday, etc.).\nValid day codes are:\n'MO' - Monday\n'TU' - Tuesday\n'WE' - Wednesday\n'TH' - Thursday\n'FR' - Friday\n'SA' - Saturday\n'SU' - Sunday");

    }

    static String[][] timeTable() {
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

    // handle attendance

    static void checkOneByOne(String[] info, String[] student_list, String[][] time_tables) {
        if (info.length != 4)
            print_error(
                    "Please enter the attendance information in the following format: <student name> <session hour> <day of the month> <attendance status>.");
        if (!Arrays.asList(student_list).contains(info[0]))
            print_error("The student name '" + info[0] + "' does not exist.");
        int hour = Integer.parseInt(info[1]);
        if (hour < 1 || hour > 6)
            print_error("the sessions houre should be betweeb 1 and 6");
        int date = Integer.parseInt(info[2]);
        if (date < 1 || date > 30)
            print_error("septembre 2020 containe only from 1 to 30");
        LocalDate localeday = LocalDate.of(2020, 9, date);
        String day = localeday.getDayOfWeek().toString();
        boolean check_date = false;
        for (int i = 0; i < time_tables.length; i++) {
            if (day.startsWith(time_tables[i][1]) && time_tables[i][0].equals(info[1])) {
                check_date = true;
                break;
            }
        }
        if (!check_date)
            print_error("the date you just enter donsn't match the time table");
        if (!(info[3].equals("HERE") || info[3].equals("NOT_HERE")))
            print_error("attendece of should should be provided like this : HERE or NOT_HERE");

    }

    static String[][] checkAttendenceData(StringBuilder attendenceData, String[] student_list, String[][] time_tables) {
        String[] splitData = attendenceData.toString().split("-");
        String[][] attendence = new String[splitData.length][4];
        for (int i = 0; i < splitData.length; i++) {
            // check data befor storing it
            String[] info = splitData[i].split(" ");
            checkOneByOne(info, student_list, time_tables);
            attendence[i] = info;
        }
        return attendence;
    }

    static StringBuilder pickAttendence() {
        StringBuilder data = new StringBuilder();

        while (true) {
            System.out.print("-->");
            String input = scanner.nextLine();
            if (input.contains("-"))
                print_error(
                        "When entering student attendance, there’s no need to include the - symbol in the information.");
            if (input.equals("."))
                break;
            data.append(input + '-');
        }
        return data;
    }

    public static void main(String args[]) {
        String[] students_list = listOfStudents(); // get the student lists
        String[][] time_tables = timeTable();
        StringBuilder data = pickAttendence();
        String[][] attendance = checkAttendenceData(data, students_list, time_tables);
        System.out.print("        ");
        for (int i = 0; i < attendance.length; i++) {
            String day = LocalDate.of(2020, 9, Integer.parseInt(attendance[i][2])).getDayOfWeek().toString();
            System.out.print(attendance[i][1] + ":00 " + day.substring(0, 2) + " " + attendance[i][2] + "|");
        }
        System.out.println();
        for (int i = 0; i < students_list.length; i++) {
            System.out.print(students_list[i] + "|");
            for (int j = 0; j < attendance.length; j++) {
                if (students_list[i].equals(attendance[j][0])) {
                    if (attendance[j][3].equals("HERE"))
                        System.out.print("       1|");
                    else
                        System.out.print("      -1|");
                } else {
                    System.out.print("         |");
                }
            }
            System.out.println();
        }
    }
}
