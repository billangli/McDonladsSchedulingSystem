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

    static GUI g;
    // Creating static variables
    private static Schedule s;

    public static void main(String[] args) throws FileNotFoundException {

        g = new GUI();

        // Setting up variables
        Scanner input = new Scanner(System.in);
        String response = "0";

        // Selecting options
        System.out.println("Welcome to the McDonlads Scheduling System\n");

        // Initializing program
        s = new Schedule();
        test(Schedule.getTable(), s.getAllEmployees()); //////////////////////////////// for debugging

        // Main program
        while (!response.equals("6")) {

            displayMenu();
            response = input.next();
            userChoice(response);

        }
    }

    public static GUI getGUI() {
        return g;
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
        w.setPay(Integer.parseInt(response));

        while (repeat) {

            int day = getDay(response, input);
            int inHour = getInHour(response, input);
            int outHour = getOutHour(response, input);


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
        m.setPay(Integer.parseInt(response));

        while (repeat) {

            int day = getInHour(response, input);
            int inHour = getInHour(response, input);
            int outHour = getOutHour(response, input);

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

    private static int getDay(String response, Scanner input) {
        System.out.println("Enter day when available:");
        response = input.next();
        return dayToNum(response);
    }

    private static int getInHour(String response, Scanner input) {
        System.out.println("Enter hour in when available:");
        response = input.next();
        return Integer.parseInt(response);
    }

    private static int getOutHour(String response, Scanner input) {
        System.out.println("Enter hour out when available:");
        response = input.next();
        return Integer.parseInt(response);
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

            switch (response) {
                case "1":
                    editName(employeeNumber);
                    break;
                case "2":
                    editAddress(employeeNumber);
                    break;
                case "3":
                    if (getE().get(employeeNumber) instanceof Manager) {
                        editSalary(employeeNumber);
                    } else {
                        editWage(employeeNumber);
                    }
                    break;
                case "4":
                    editAvailability(employeeNumber);
                    break;
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
        getE().get(employeeNumber).setPay(Integer.parseInt(response));
    }

    private static void editWage(int employeeNumber) {
        Scanner input = new Scanner(System.in);
        String response;
        System.out.println("Enter the new wage of " + getE().get(employeeNumber).getFullName());
        response = input.nextLine();
        getE().get(employeeNumber).setPay(Integer.parseInt(response));
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
        } else if (day.equalsIgnoreCase("R")) {
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

    private static String numToDay(int day) {
        if (day == 0) {
            return "M";
        } else if (day == 1) {
            return "T";
        } else if (day == 2) {
            return "W";
        } else if (day == 3) {
            return "R";
        } else if (day == 4) {
            return "F";
        } else if (day == 5) {
            return "S";
        } else if (day == 6) {
            return "U";
        } else {
            System.out.println("*** Day is wrong");
            return "Error";
        }
    }

    private static void updateEmployeeFile() throws FileNotFoundException {

        // Making sure all employees are sorted before the output starts
        organizeEmployees();


        PrintWriter output = new PrintWriter("src/EmployeeInfo.txt");

        for (Employee employee : getE()) {
            output.println(employee.getClass());
            output.println(employee.getFullName());
            output.println(employee.getAddress());
            if (employee instanceof Manager) {
                output.println(employee.getPay());
            } else {
                output.println(employee.getPay());
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
        for (Employee allEmployee : allEmployees) {
            System.out.println("Employee: " + allEmployee.getFullName());
        }
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 24; j++) {
                System.out.println("Day " + i + " Hour " + j + " Demand " + table[i][j].getRequiredEmployees());

                for (Employee allEmployee : allEmployees) {
                    if (allEmployee.getHoursAvailable()[i][j]) {
                        System.out.println(allEmployee.getFullName() + " is available.");
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

        organizeEmployees();
//        displayEmployees();

    }

    private static void organizeEmployees() {

        System.out.println("Organizing Employees");

        // Creating variables to swap employee objects
        Employee swapToBack;
        boolean repeat = true;

        while (repeat) {
            // Bubbles sorting the managers by pay
            repeat = false;
            for (int i = 0; i < s.getOnlyManagers().size() - 1; i++) {
                if (s.getOnlyManagers().get(i).getPay() < s.getOnlyManagers().get(i + 1).getPay()) {
                    // Swapping the manager objects
                    swapToBack = s.getOnlyManagers().get(i);
                    s.getOnlyManagers().set(i, s.getOnlyManagers().get(i + 1));
                    s.getOnlyManagers().set(i + 1, swapToBack);
                    repeat = true;
                }
            }
        }

        repeat = true;
        while (repeat) {
            // Bubbles sorting the workers by pay
            repeat = false;
            for (int i = 0; i < s.getOnlyWorkers().size() - 1; i++) {
                if (s.getOnlyWorkers().get(i).getPay() < s.getOnlyWorkers().get(i + 1).getPay()) {
                    // Swapping the worker objects
                    swapToBack = s.getOnlyWorkers().get(i);
                    s.getOnlyWorkers().set(i, s.getOnlyWorkers().get(i + 1));
                    s.getOnlyWorkers().set(i + 1, swapToBack);
                    repeat = true;
                }
            }
        }

        // Displaying the employees
        for (int i = 0; i < s.getOnlyManagers().size(); i++) {
            System.out.println("Manager: " + s.getOnlyManagers().get(i).getFullName() + "\tSalary: " + s.getOnlyManagers().get(i).getPay());
        }
        for (int i = 0; i < s.getOnlyWorkers().size(); i++) {
            System.out.println("Employee: " + s.getOnlyWorkers().get(i).getFullName() + "\tWage: " + s.getOnlyWorkers().get(i).getPay());
        }
    }

    private static void runScheduler() {
        //s.dumpEmployees();
        //s.optimizeEmployees();
        s.scheduleManagers();
        s.scheduleWorkers();
        s.fillUpTheRest();
    }

    private static void displaySchedule() {

        for (int i = 0; i < 7; i++) {
            System.out.println("\nDay: " + numToDay(i));
            for (int j = 0; j < 24; j++) {
                System.out.println("Hour: " + j);

                for (int k = 0; k < Schedule.getTable()[i][j].getSlot().size(); k++) {
                    System.out.println(Schedule.getTable()[i][j].getSlot().get(k).getFullName());
                }
            }
        }

    }

    public static Schedule getS() {
        return s;
    }

    public static void setS(Schedule s) {
        Scheduler.s = s;
    }

    private static ArrayList<Employee> getE() {
        return s.getAllEmployees();
    }
}
