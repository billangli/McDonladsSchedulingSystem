import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Created by RobbieZhuang on 2016-10-31.
 */
public class Schedule {

    // Our take on building a 2d array of ARRAYLISTS, Timeslot is the individual ArrayList that holds Employee objects
    private static Timeslot[][] table;
    private Timeslot[][] managerTable;
    private Timeslot[][] workerTable;

    // Numnber of employees required at each 1h block
    private int[][] requiredEmployees = new int[7][24];
    private int[][] totalRequiredEmployees = new int[7][24];

    // Employees
    private ArrayList<Employee> allEmployees = new ArrayList<>();
    private ArrayList<Employee> getOnlyManagers = new ArrayList<>();
    private ArrayList<Employee> getOnlyWorkers = new ArrayList<>();

    // Build constructor for a schedule
    // The schedule keeps track of how many people are in each 1h block
    public Schedule() throws FileNotFoundException {
        table = new Timeslot[7][24];
        managerTable = new Timeslot[7][24];
        workerTable = new Timeslot[7][24];
        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < table[i].length; j++) {
                managerTable[i][j] = new Timeslot();
                workerTable[i][j] = new Timeslot();
                table[i][j] = new Timeslot(requiredEmployees[i][j]);
            }
        }
        readHours();
        readEmployeeInfo();

        for (Employee x : allEmployees) {
            if (x instanceof Manager) {
                getOnlyManagers.add(x);
            } else if (x instanceof Worker) {
                getOnlyWorkers.add(x);
            }
        }
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

    public static void print2DArray(int[][] a) {
        System.out.println("_________________________");
        for (int[] b : a) {
            for (int c : b) {
                System.out.print(c + " ");
            }
            System.out.println();
        }
    }

    public static Timeslot[][] getTable() {
        return table;
    }

    public ArrayList<Employee> getOnlyManagers() {
        return getOnlyManagers;
    }

    public ArrayList<Employee> getOnlyWorkers() {
        return getOnlyWorkers;
    }

    public void scheduleEmployees() {

    }

    // Add all the managers in and make sure they work at least 40 hours
    public void scheduleManagers() {
        // Put all the managers in (have some work more than total required just for the hours)
        for (Employee x : getOnlyManagers) {
            for (int i = 0; i < 7; i++) {
                for (int j = 0; j < 24; j++) {
                    if (x.getHoursAvailable()[i][j] && !x.getHoursWorking()[i][j] && totalRequiredEmployees[i][j] > 0) {
                        managerTable[i][j].addEmployee(x);
                        x.setHourWorking(i, j, true);
                        x.addHourOfWork();
                        if (requiredEmployees[i][j] > 0) {
                            requiredEmployees[i][j]--;
                        }
                    }
                }
            }
        }
        // Optimize managers
        for (Employee x : getOnlyManagers) {
            int hoursOvertime = x.getTotalHours() - ((Manager) x).getMinWorkHours();
//          If manager is not removed at any of the times, then we should skip checking this manager
//          int shouldSkip = 0;
            for (int i = 0; i < 7; i++) {
                for (int j = 0; j < 24; j++) {
                    // If manager is actually working at that time
                    if (x.getHoursWorking()[i][j] && hoursOvertime > 0) {
                        // If there are multiple managers working at the same time
                        if (managerTable[i][j].getSlot().size() > 1) {
                            managerTable[i][j].getSlot().remove(x);
                            x.setHourWorking(i, j, false);
                            x.subtractHourOfWork();
                            hoursOvertime--;
                        }
                    }
                }
            }
            // If the manager is still working hours overtime, just remove their first hours which they work at
            for (int i = 0; i < 7; i++) {
                for (int j = 0; j < 24; j++) {
                    if (x.getHoursWorking()[i][j] && hoursOvertime > 0) {
                        managerTable[i][j].getSlot().remove(x);
                        if (managerTable[i][j].getSlot().size() < totalRequiredEmployees[i][j]) {
                            requiredEmployees[i][j] = totalRequiredEmployees[i][j] - managerTable[i][j].getSlot().size();
                        }
                        x.setHourWorking(i, j, false);
                        x.subtractHourOfWork();
                        hoursOvertime--;
                    }
                }
            }
        }

        // refresh the requiredEmployees array
//        for (int i = 0; i < 7; i++){
//            for (int j = 0; j < 24; j++){
//                if (managerTable[i][j].getSlot().size() < requiredEmployees[i][j]){
//                    requiredEmployees[i][j] -= managerTable[i][j].getSlot().size();
//                }
//                else{
//                    requiredEmployees[i][j] = 0;
//                }
//            }
//        }
//        print2DArray(requiredEmployees);
    }

    // Figure out what number gives workers a roughly even amount of time to work and then puts them in.
    public void scheduleWorkers() {
        int hoursLeftToFill = 0;
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 24; j++) {
                hoursLeftToFill += requiredEmployees[i][j];
            }
        }

        int averageHoursOfWorkLeft = hoursLeftToFill / getOnlyWorkers.size() * 2;
        for (Employee x : getOnlyWorkers) {
            int cnt = averageHoursOfWorkLeft;
            for (int i = 0; i < 7; i++) {
                for (int j = 0; j < 24; j++) {
                    if (requiredEmployees[i][j] > 0 && !x.getHoursWorking()[i][j] && x.getHoursAvailable()[i][j] && cnt > 0) {
                        workerTable[i][j].addEmployee(x);
                        x.addHourOfWork();
                        x.setHourWorking(i, j, true);
                        requiredEmployees[i][j]--;
                        cnt--;
                    }
                }
            }
        }

        for (Employee x : getOnlyWorkers) {
            for (int i = 0; i < 7; i++) {
                for (int j = 0; j < 24; j++) {
                    if (requiredEmployees[i][j] > 0 && !x.getHoursWorking()[i][j] && x.getHoursAvailable()[i][j]) {
                        workerTable[i][j].addEmployee(x);
                        x.addHourOfWork();
                        x.setHourWorking(i, j, true);
                        requiredEmployees[i][j]--;
                    }
                }
            }
        }

    }

    // Add in managers if required
    public void fillUpTheRest() {
        // Add everyone to the final table
        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < table[i].length; j++) {
                for (Employee x : managerTable[i][j].getSlot()) {
                    table[i][j].addEmployee(x);
                }
                for (Employee x : workerTable[i][j].getSlot()) {
                    table[i][j].addEmployee(x);
                }

            }
        }
        // Fill in the empty spots
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 24; j++) {
                if (requiredEmployees[i][j] > 0) {
                    for (Employee x : getOnlyManagers) {
                        if (requiredEmployees[i][j] > 0 && !x.getHoursWorking()[i][j] && x.getHoursAvailable()[i][j]) {
                            table[i][j].addEmployee(x);
                            x.addHourOfWork();
                            x.setHourWorking(i, j, true);
                            requiredEmployees[i][j]--;
                        }
                    }
                }
            }
        }

        print2DArray(totalRequiredEmployees);
        System.out.println("The good stuff");
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 24; j++) {
                System.out.print(table[i][j].getSlot().size() + " ");
            }
            System.out.println();
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
                    this.requiredEmployees[day][i] = requiredEmployees;
                    //table[day][i].setRequiredEmployees(requiredEmployees);
                }

            }
        } catch (Exception e) {
            System.out.println("*** Something wrong with the buffered reader in readHours");
        }
        for (int i = 0; i < this.requiredEmployees.length; i++) {
            for (int j = 0; j < this.requiredEmployees[i].length; j++) {
                totalRequiredEmployees[i][j] = this.requiredEmployees[i][j];
            }
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
                if (employeeType.contains("Manager")) {
                    e = new Manager();
                    readManager((Manager) e, br);
                    allEmployees.add(e);
                } else if (employeeType.contains("Worker")) {
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
        m.setPay(Double.parseDouble(br.readLine()));

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
        w.setPay(Double.parseDouble(br.readLine()));

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

    public ArrayList<Employee> getAllEmployees() {
        return allEmployees;
    }
}
