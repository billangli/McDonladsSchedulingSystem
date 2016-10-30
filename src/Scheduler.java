/*
 Scheduler.java
 This is the main program

 Created by Bill Li on 2016-10-30.
 */

import java.util.Scanner;

public class Scheduler {
    public static void main(String[] args) {

        // Setting up variables
        Scanner input = new Scanner(System.in);
        String response = "0";

        // Selecting options
        System.out.println("Welcome to the McDonlads Scheduling System\n");

        // Main program
        while (!response.equals("6")) {

            displayMenu();
            response = input.next();

        }
    }

    /**
     * This is the menu that the user sees
     */
    private static void displayMenu() {
        System.out.println("Enter 1 to add/edit/remove worker");
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

    private static void manageWorker() {

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
