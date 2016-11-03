import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

/**
 * Created by RobbieZhuang on 2016-10-31.
 */
public class Schedule {

    // Our take on building a 2d array of ARRAYLISTS, Timeslot is the individual ArrayList that holds Employee objects
    Timeslot[][] table;

    // Numnber of employees required at each 1h block
    int[][] requiredEmployees = new int[7][24];

    // Employees
    ArrayList<Employee> allEmployees = new ArrayList<>();
    ArrayList<Employee> onlyManagers = new ArrayList<>();
    ArrayList<Employee> onlyWorkers = new ArrayList<>();

    // Build constructor for a schedule
    // The schedule keeps track of how many people are in each 1h block
    public Schedule() throws FileNotFoundException {
        table = new Timeslot[7][24];
        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < table[i].length; j++) {
                table[i][j] = new Timeslot(requiredEmployees[i][j]);
            }
        }
        readHours();
        readEmployeeInfo();
    }

    private static int determineHour(String str) {

        int hour;
        str = str.substring(0, 2);

        try {
            return Integer.parseInt(str);
        } catch (Exception e) {
            System.out.println("*** Something wrong with hour converter");
        }

        System.out.println("*** Something wrong with hour converter");
        return 0;
    }

    private static int determineDay(String str) {

        if (str.substring(0, 1).equalsIgnoreCase("M")) {
            return 0;
        } else if (str.substring(0, 1).equalsIgnoreCase("T")) {
            return 1;
        } else if (str.substring(0, 1).equalsIgnoreCase("W")) {
            return 2;
        } else if (str.substring(0, 1).equalsIgnoreCase("R")) {
            return 3;
        } else if (str.substring(0, 1).equalsIgnoreCase("F")) {
            return 4;
        } else if (str.substring(0, 1).equalsIgnoreCase("S")) {
            return 5;
        } else if (str.substring(0, 1).equalsIgnoreCase("U")) {
            return 6;
        }

        System.out.println("*** determineDay");
        return -1;
    }

    public void dumpEmployees() {
        for (Employee e : allEmployees) {
            if (e instanceof Worker) {
                onlyWorkers.add(e);
            } else if (e instanceof Manager) {
                onlyManagers.add(e);
            }
        }

        // Spot to add in manager
        // Only if at least one employee is required at that time
        // Make it so that managers have on average 40h of work

        for (Employee e : onlyWorkers) {
            for (int i = 0; i < 7; i++) {
                for (int j = 0; j < 24; j++) {
                    if (e.getHoursAvailable()[i][j]) {
                        table[i][j].addEmployee(e);
                        e.addHourOfWork();
                    }
                }
            }
        }
    }

    public void optimizeEmployees() {
        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < table[i].length; j++) {
                int hadToRemove;
                do {
                    hadToRemove = 0;
                    if (table[i][j].currentNumberOfEmployees() > table[i][j].getRequiredEmployees()) {
                        hadToRemove++;
                        int max = 0;
                        Employee x = null;
                        Collections.sort(table[i][j].getSlot());

                        for (Employee e : table[i][j].getSlot()) {
                            // If e is not a manager
                            if (e instanceof Worker && e.getTotalHours() > max) {
                                max = e.getTotalHours();
                                x = e;
                            }
                        }
                        //System.out.println("Max hours: " + max);
                        x.subtractHourOfWork();
                        table[i][j].getSlot().remove(x);
                    }
                } while (hadToRemove > 0);
            }
        }
    }

    private void readHours() throws FileNotFoundException {

        System.out.println("Reading hours from text file");

        // Creating variables
        String str;
        int day = 0;
        int inHour = 0;
        int outHour = 0;
        int requiredEmployees = 0;

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("src/Schedule.txt")));
            while ((str = br.readLine()) != null) {

                // Reading the file
                if (str.length() == 1) {
                    day = determineDay(str);
                } else {
                    inHour = determineHour(str);
                    outHour = determineHour(str.substring(str.indexOf("-") + 1));
                    requiredEmployees = Integer.parseInt(str.substring(str.length() - 1));
                }

                // Adding hours and requiredEmployees to program
                for (int i = inHour; i < outHour; i++) {
                    // Add requiredEmployees to each time slot
                    table[day][i].setRequiredEmployees(requiredEmployees);
                }

            }
        } catch (Exception e) {
            System.out.println("*** Something wrong with the buffered reader in readHours");
        }

    }

    private void readEmployeeInfo() {

        // Creating variables
        String str;
        String employeeType;

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("src/EmployeeInfo.txt")));
            while ((str = br.readLine()) != null) {

                Employee e;

                // Read the file to find out what type of employee this person is
                employeeType = str;
                if (employeeType.equalsIgnoreCase("Manager")) {
                    e = new Manager();
                    readManager((Manager) e, br);
                    allEmployees.add(e);
                } else if (employeeType.equalsIgnoreCase("Worker")) {
                    e = new Worker();
                    readWorker((Worker) e, br);
                    allEmployees.add(e);
                }
            }
        } catch (Exception e) {
            System.out.println("*** Something wrong with the buffered reader in readEmployeeIno");
        }

    }

    private Manager readManager(Manager m, BufferedReader br) throws IOException {

        // Reading some general information
        m.setFullName(br.readLine());
        m.setAddress(br.readLine());
        m.setSalary(Double.parseDouble(br.readLine()));

        // Reading the availability information
        for (int i = 0; i < 7; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 0; j < 24; j++) {
                if (st.nextToken().equals("1")) {
                    m.setHoursAvailable(i, j, true);
                }
            }
        }

        return m;
    }

    private Worker readWorker(Worker w, BufferedReader br) throws IOException {

        // Reading some general information
        w.setFullName(br.readLine());
        w.setAddress(br.readLine());
        w.setWage(Double.parseDouble(br.readLine()));

        // Reading the availability information
        for (int i = 0; i < 7; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 0; j < 24; j++) {
                if (st.nextToken().equals("1")) {
                    w.setHoursAvailable(i, j, true);
                }
            }
        }

        return w;
    }

    public Timeslot[][] getTable() {
        return table;
    }

    public ArrayList<Employee> getAllEmployees() {
        return allEmployees;
    }
}
