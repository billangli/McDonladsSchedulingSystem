/*
 Scheduler.java
 This is the main program

 Created by Bill Li on 2016-10-30.
 */

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Scheduler {

    // Creating static variables
    private static Schedule s;

    public static void main(String[] args) throws FileNotFoundException {

        // Setting up variables
        Scanner input = new Scanner(System.in);
        String response = "0";

        // Selecting options
        System.out.println("Welcome to the McDonlads Scheduling System\n");

        // Initializing program
        s = new Schedule();
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

    private static void userChoice(String response) throws FileNotFoundException {
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

    /**
     * @param employeeType is 0 if it is manager, 1 if it is worker
     */
    private static void addEditOrRemoveEmployee(int employeeType) throws FileNotFoundException {

        System.out.println("Enter 1 to add employee");
        System.out.println("Enter 2 to edit employee");
        System.out.println("Enter 3 to remove employee");

        Scanner input = new Scanner(System.in);
        int response = input.nextInt();

        if (response == 1) {
            if (employeeType == 0) {
                addManager();
            } else {
                addWorker();
            }
        } else if (response == 2) {
            editEmployee();
        } else if (response == 3) {
            removeEmployee();
        }
    }

    private static void addWorker() throws FileNotFoundException {

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
        // Add worker to all employees
        System.out.println("Adding worker to all employees");
        getE().add(w);

        System.out.println("Updating employee file");
        updateEmployeeFile();
    }

    private static void addManager() throws FileNotFoundException {

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
        // Add manager to all employees
        System.out.println("Adding manager to employees");
        getE().add(m);

        System.out.println("Updating employee file");
        updateEmployeeFile();
    }

    private static void editEmployee() throws FileNotFoundException {

        Scanner input = new Scanner(System.in);
        String response;
        boolean repeat = true;
        int employeeNumber;

        while (repeat) {

            // Displaying all the employees and letting the user choose which one to change
            displayEmployees();
            System.out.println("Enter the employee # to select employee");
            employeeNumber = Integer.parseInt(input.next());

            System.out.println("Enter 1 to change employee name.");
            System.out.println("Enter 2 to change employee address.");
            System.out.println("Enter 3 to change employee wage.");
            System.out.println("Enter 4 to change employee availability.");
            response = input.next();

            if (response.equals("1")) {
                editName(employeeNumber);
            } else if (response.equals("2")) {
                editAddress(employeeNumber);
            } else if (response.equals("3")) {
                if (getE().get(employeeNumber) instanceof Manager) {
                    editSalary(employeeNumber);
                } else {
                    editWage(employeeNumber);
                }
            } else if (response.equals("4")) {
                editAvailability(employeeNumber);
            }

            System.out.println("Enter -1 to edit something else");
            response = input.next();
            if (!response.equals("-1")) {
                repeat = false;
            }
        }

        System.out.println("Updating employee file");
        updateEmployeeFile();
    }

    private static void editName(int employeeNumber) {
        Scanner input = new Scanner(System.in);
        String response;
        System.out.println("Enter the new name of " + getE().get(employeeNumber).getFullName());
        response = input.nextLine();
        getE().get(employeeNumber).setFullName(response);
    }

    private static void editAddress(int employeeNumber) {
        Scanner input = new Scanner(System.in);
        String response;
        System.out.println("Enter the new address of " + getE().get(employeeNumber).getFullName());
        response = input.nextLine();
        getE().get(employeeNumber).setAddress(response);
    }

    private static void editSalary(int employeeNumber) {
        Scanner input = new Scanner(System.in);
        String response;
        System.out.println("Enter the new salary of " + getE().get(employeeNumber).getFullName());
        response = input.nextLine();
        ((Manager) getE().get(employeeNumber)).setSalary(Integer.parseInt(response));
    }

    private static void editWage(int employeeNumber) {
        Scanner input = new Scanner(System.in);
        String response;
        System.out.println("Enter the new wage of " + getE().get(employeeNumber).getFullName());
        response = input.nextLine();
        ((Worker) getE().get(employeeNumber)).setWage(Integer.parseInt(response));
    }

    private static void editAvailability(int employeeNumber) {

        // Creating variables
        Scanner input = new Scanner(System.in);
        String response;
        int day;
        int inHour;
        int outHour;
        boolean newAvailability;

        // Displaying the availability to user
        displayAvailability(getE().get(employeeNumber));

        // Selecting day and hours to change
        System.out.println("Enter the day to change");
        response = input.next();
        day = dayToNum(response);
        System.out.println("Enter the in hour to change");
        response = input.next();
        inHour = Integer.parseInt(response);
        System.out.println("Enter the out hour to change");
        response = input.next();
        outHour = Integer.parseInt(response);
        System.out.println("Enter new availability for those hours (1 for available");
        response = input.next();
        newAvailability = response.equals("1");
        for (int i = inHour; i < outHour; i++) {
            getE().get(employeeNumber).setHoursAvailable(day, i, newAvailability);
        }

        System.out.println("Enter the new salary of " + getE().get(employeeNumber).getFullName());
        response = input.nextLine();
        ((Manager) getE().get(employeeNumber)).setSalary(Integer.parseInt(response));
    }

    private static void removeEmployee() throws FileNotFoundException {

        Scanner input = new Scanner(System.in);
        String response;
        boolean repeat = true;

        while (repeat) {

            displayEmployees();
            System.out.println("Enter employee # to remove employee");
            System.out.println("Enter any other number to not remove any employees");
            response = input.next();
            getE().remove(Integer.parseInt(response));

            System.out.println("Enter -1 to remove another employee");
            response = input.next();
            if (!response.equals("-1")) {
                repeat = false;
            }
        }

        System.out.println("Updating employee file");
        updateEmployeeFile();
    }

    private static void displayEmployees() {
        for (int i = 0; i < getE().size(); i++) {
            System.out.println("Employee #" + i + ": " + getE().get(i).getFullName());
        }
    }

    private static void displayAvailability(Employee employee) {
        boolean[][] availabilty = employee.getHoursAvailable();
        System.out.println("Availability: (1 is available)");
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 24; j++) {
                if (availabilty[i][j]) {
                    System.out.println("1");
                } else {
                    System.out.println("0");
                }
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

    private static void sortEmployees() {

    }

    private static void updateEmployeeFile() throws FileNotFoundException {

        // Making sure all employees are sorted before the output starts
        sortEmployees();


        PrintWriter output = new PrintWriter("src/employeeInfo.txt");

        for (Employee employee : getE()) {
            output.println(employee.getEmployeeType());
            output.println(employee.getFullName());
            output.println(employee.getAddress());
            if (employee.getEmployeeType().equals("Manager")) {
                output.println(((Manager) employee).getSalary());
            } else {
                output.println(((Worker) employee).getWage());
            }
            for (int j = 0; j < 7; j++) {
                for (int k = 0; k < 24; k++) {
                    if (employee.getHoursAvailable()[j][k]) {
                        output.print("1 ");
                    } else {
                        output.print("0 ");
                    }
                }
                output.println("");
            }
        }
        output.close();
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

    private static void manageWorker() throws FileNotFoundException {

        addEditOrRemoveEmployee(1);

    }

    private static void manageManager() throws FileNotFoundException {

        addEditOrRemoveEmployee(0);

    }

    private static void listEmployees() {

        sortEmployees();
        displayEmployees();

    }

    private static void runScheduler() {

    }

    private static void displaySchedule() {

    }

    public static Schedule getS() {
        return s;
    }

    public static void setS(Schedule s) {
        Scheduler.s = s;
    }

    public static ArrayList<Employee> getE() {
        return s.allEmployees;
    }
}
