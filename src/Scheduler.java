/*
 Scheduler.java
 This is the main program

 Created by Bill Li on 2016-10-30.
 */

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Scheduler {
    public static void main(String[] args) throws FileNotFoundException {

        // Setting up variables
        Scanner input = new Scanner(System.in);
        String response = "0";

        // Selecting options
        System.out.println("Welcome to the McDonlads Scheduling System\n");

        // Initializing program
        Schedule s = new Schedule();
        test(s.getTable(), s.getAllEmployees()); //////////////////////////////// for debugging


        // Main program
        while (!response.equals("6")) {

            displayMenu();
            response = input.next();
            userChoice(response);

        }
    }

    /**
     * This is the menu that the user sees
     */
    private static void displayMenu() {
        System.out.println("\nEnter 1 to add/edit/remove worker");
        System.out.println("Enter 2 to add/edit/remove manager");
        System.out.println("Enter 3 to list all employees");
        System.out.println("Enter 4 to run scheduler");
        System.out.println("Enter 5 to display weekly schedule and pay (by employee #)");
        System.out.println("Enter 6 to quit");
    }

    private static void userChoice(String response) {
        switch (response) {
            case "1":
                // Add/edit/remove worker
                manageWorker();

                break;
            case "2":
                // Add/edit/remove manager
                manageManager();

                break;
            case "3":
                // List all employees
                listEmployees();

                break;
            case "4":
                // Run scheduler
                runScheduler();

                break;
            case "5":
                // Display Weekly Schedule
                displaySchedule();

                break;
        }
    }

    private static void addEditOrRemoveWorker() {

        System.out.println("Enter 1 to add worker");
        System.out.println("Enter 2 to edit worker");
        System.out.println("Enter 3 to remove employee");

        Scanner input = new Scanner(System.in);
        int response = input.nextInt();

        if (response == 1) {
            addWorker();
        } else if (response == 2) {
//            editWorker();
        } else if (response == 3) {
//            removeWorker();
        }
    }

    private static void addWorker() {

        Scanner input = new Scanner(System.in);
        String response;
        boolean repeat = true;
        Worker w = new Worker();

        System.out.println("What is the worker's name?");
        response = input.nextLine();
        w.setFullName(response);
        System.out.println("What is the worker's address?");
        response = input.nextLine();
        w.setAddress(response);
        System.out.println("What is the worker's wage?");
        response = input.next();
        w.setWage(Integer.parseInt(response));

        while (repeat) {

            int day;
            int inHour;
            int outHour;

            System.out.println("Enter day when available:");
            response = input.next();
            day = dayToNum(response);
            System.out.println("Enter hour in when available:");
            response = input.next();
            inHour = Integer.parseInt(response);
            System.out.println("Enter hour out when available:");
            response = input.next();
            outHour = Integer.parseInt(response);
            for (int i = inHour; i < outHour; i++) {
                w.setHoursAvailable(day, i, true);
            }

            System.out.println("Enter -1 to add another available time");
            response = input.next();
            if (!response.equals("-1")) {
                repeat = false;
            }
        }
    }

    private static void addManager() {

        Scanner input = new Scanner(System.in);
        String response;
        boolean repeat = true;
        Manager m = new Manager();

        System.out.println("What is the manager's name?");
        response = input.nextLine();
        m.setFullName(response);
        System.out.println("What is the manager's address?");
        response = input.nextLine();
        m.setAddress(response);
        System.out.println("What is the manager's salary?");
        response = input.next();
        m.setSalary(Integer.parseInt(response));

        while (repeat) {

            int day;
            int inHour;
            int outHour;

            System.out.println("Enter day when available:");
            response = input.next();
            day = dayToNum(response);
            System.out.println("Enter hour in when available:");
            response = input.next();
            inHour = Integer.parseInt(response);
            System.out.println("Enter hour out when available:");
            response = input.next();
            outHour = Integer.parseInt(response);
            for (int i = inHour; i < outHour; i++) {
                m.setHoursAvailable(day, i, true);
            }

            System.out.println("Enter -1 to add another available time");
            response = input.next();
            if (!response.equals("-1")) {
                repeat = false;
            }
        }
    }


    private static int dayToNum(String day) {
        if (day.equalsIgnoreCase("M")) {
            return 0;
        } else if (day.equalsIgnoreCase("T")) {
            return 1;
        } else if (day.equalsIgnoreCase("W")) {
            return 2;
        } else if (day.equalsIgnoreCase("U")) {
            return 3;
        } else if (day.equalsIgnoreCase("F")) {
            return 4;
        } else if (day.equalsIgnoreCase("S")) {
            return 5;
        } else if (day.equalsIgnoreCase("U")) {
            return 6;
        } else {
            System.out.println("*** Day is wrong");
            return -1;
        }
    }

    private static void test(Timeslot[][] table, ArrayList<Employee> allEmployees) {
        for (int i = 0; i < allEmployees.size(); i++) {
            System.out.println("Employee: " + allEmployees.get(i).getFullName());
        }
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 24; j++) {
                System.out.println("Day " + i + " Hour " + j + " Demand " + table[i][j].getRequiredEmployees());

                for (int k = 0; k < allEmployees.size(); k++) {
                    if (allEmployees.get(k).getHoursAvailable()[i][j]) {
                        System.out.println(allEmployees.get(k).getFullName() + " is available.");
                    }
                }
            }
        }
    }

    private static void manageWorker() {

        addEditOrRemoveWorker();

    }

    private static void manageManager() {


    }

    private static void listEmployees() {

    }

    private static void runScheduler() {

    }

    private static void displaySchedule() {

    }
}
