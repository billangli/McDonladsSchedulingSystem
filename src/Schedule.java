import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * Created by RobbieZhuang on 2016-10-31.
 */
public class Schedule {

    // Each element of the schedule 2D array contains Timeslot objects
    // which is essentially an arraylist containing employees
    private static Timeslot[][] schedule;
    // Numnber of employees required at each 1h block
    // requiredEmployees is the modifiable one
    private static int[][] totalRequiredEmployees = new int[7][24];
    private static int[][] requiredEmployees = new int[7][24];
    // Recommendation arraylist :D
    private static ArrayList<Recommendation> recs = new ArrayList<>();
    private static String recommendationMessage = "";
    // Individual managers schedule and workers schedule
    private Timeslot[][] managerSchedule;
    private Timeslot[][] workerSchedule;
    // Arraylists containing all employees
    private ArrayList<Employee> allEmployees = new ArrayList<>();
    private ArrayList<Employee> onlyManagers = new ArrayList<>();
    private ArrayList<Employee> onlyWorkers = new ArrayList<>();

    // Build constructor for a schedule
    // The schedule keeps track of how many people are in each 1h block
    public Schedule() throws FileNotFoundException {

        // Declare schedules & initialize
        schedule = new Timeslot[7][24];
        managerSchedule = new Timeslot[7][24];
        workerSchedule = new Timeslot[7][24];
        for (int i = 0; i < schedule.length; i++) {
            for (int j = 0; j < schedule[i].length; j++) {
                managerSchedule[i][j] = new Timeslot();
                workerSchedule[i][j] = new Timeslot();
                schedule[i][j] = new Timeslot(requiredEmployees[i][j]);
            }
        }

        // Reading how many employees are requested each hour and info on each employee
        readHours();
        readEmployeeInfo();

        // Previous methods added all employees into the allEmployees array,
        // seperate managers and workers into seperate arrays
        for (Employee x : allEmployees) {
            if (x instanceof Manager) {
                onlyManagers.add(x);
            } else if (x instanceof Worker) {
                onlyWorkers.add(x);
            }
        }
    }

    //TODO Bill please add comments here
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

    //TODO Bill please add comments here
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

    // Getters (and Setters?)

    /**
     * This method returns the schedule 2D array
     *
     * @return Timeslot[][] schedule
     * @author Robbie Zhuang
     */
    public static Timeslot[][] getSchedule() {
        return schedule;
    }

    /**
     * Returns the 2D integer array containing the mumber of required employees
     * @author Robbie Zhuang
     * @return int[][] totalRequiredEmployees
     */
    public static int[][] getTotalRequiredEmployees() {
        return totalRequiredEmployees;
    }

    //TODO Bill please add comments here
    private static int getInHour(String response, Scanner input) {
        System.out.println("Enter hour in when available:");
        response = input.next();
        return Integer.parseInt(response);
    }

    //TODO Bill please add comments here
    private static int getOutHour(String response, Scanner input) {
        System.out.println("Enter hour out when available:");
        response = input.next();
        return Integer.parseInt(response);
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

    public static ArrayList<Recommendation> getRecs() {
        return recs;
    }

    public static String getRecommendationMessage() {
        return recommendationMessage;
    }

    public ArrayList<Employee> getOnlyManagers() {
        return onlyManagers;
    }

    public ArrayList<Employee> getOnlyWorkers() {
        return onlyWorkers;
    }
    // Add all the managers in and make sure they work at least 40 hours

    public void scheduleEmployees() {
        scheduleManagers();
        scheduleWorkers();
        fillUpTheRest();
    }

    /**
     * Step one of the algorithm which schedules the managers first
     * ADD A DESCRIPTION HERE OF HOW IT WORKS!!! ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     * @author Robbie Zhuang
     * @return nothing
     */
    public void scheduleManagers() {

        // Put all the managers in wherever they can work
        for (Employee x : onlyManagers) {
            for (int i = 0; i < schedule.length; i++) {
                for (int j = 0; j < schedule[i].length; j++) {
                    // If the manager can work at a certain time (can work at that time &
                    // is not working at that time already) and employees are required at that time
                    if (totalRequiredEmployees[i][j] > 0 && x.getHoursAvailable()[i][j] && !x.getHoursWorking()[i][j]) {
                        // Add manager to manager schedule
                        managerSchedule[i][j].addEmployee(x);
                        // Set manager to be working at that time and increase the number of hours they will be
                        // working for
                        x.setHourWorking(i, j, true);
                        x.addHourOfWork();
                        // Update the number of required employees at that time slot
                        if (requiredEmployees[i][j] > 0) {
                            requiredEmployees[i][j]--;
                        }
                    }
                }
            }
        }

        // Optimize managers
        for (Employee x : onlyManagers) {
            // Deals with managers who are currently working overtime AND there are more than one manager in a slot
            int hoursOvertime = x.getTotalHours() - ((Manager) x).getMinWorkHours();
            if (hoursOvertime > 0) {
                for (int i = 0; i < schedule.length; i++) {
                    for (int j = 0; j < schedule[i].length; j++) {
                        // Check if manager is working at a time slot and if they are, then remove
                        if (x.getHoursWorking()[i][j] && hoursOvertime > 0) {
                            // If there are multiple managers working at the same time
                            if (managerSchedule[i][j].getSlot().size() > 1) {
                                managerSchedule[i][j].getSlot().remove(x);
                                removeEmployeeFromSchedule(i, j, x, managerSchedule);
                                hoursOvertime--;
                            }
                        }
                    }
                }
            }
            // If we are still at overtime after first round of optimization
            if (hoursOvertime > 0) {
                // If the manager is still working hours overtime, just remove the first hours which they work at
                for (int i = 0; i < schedule.length; i++) {
                    for (int j = 0; j < schedule[i].length; j++) {
                        // Check if manager is working at a time slot and if they are, then remove
                        if (x.getHoursWorking()[i][j] && hoursOvertime > 0) {
                            managerSchedule[i][j].getSlot().remove(x);
                            // Check if this affects the number of required employees at that time
                            removeEmployeeFromSchedule(i, j, x, managerSchedule);
                            hoursOvertime--;
                        }
                    }
                }
            }
        }

        /*
        /// TESTINGGGGGG
        System.out.println("Number of managers that we currently have");
        for (int i = 0; i < 7; i++){
            for (int j = 0; j < 24; j++){
                System.out.print(managerSchedule[i][j].getSlot().size() + " ");
            }
            System.out.println();
        }
        System.out.println("Required Employees");
        print2DArray(requiredEmployees);
        System.out.println("Total Required Employees");
        print2DArray(totalRequiredEmployees);
        /////////////////
        */

    }

    /**
     * Step two of the algorithm which schedules the workers next
     * Figure out what number gives workers a roughly even amount of time to work and then puts them in.
     * @author Robbie Zhuang
     * @return nothing
     */
    public void scheduleWorkers() {
        // First figures out how long each worker should work for approximately
        int hoursLeftToFill = 0;
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 24; j++) {
                hoursLeftToFill += requiredEmployees[i][j];
            }
        }
        int averageHoursOfWorkLeft = hoursLeftToFill / onlyWorkers.size();

        // Add workers
        for (Employee x : onlyWorkers) {
            int cnt = averageHoursOfWorkLeft;
            for (int i = 0; i < schedule.length; i++) {
                for (int j = 0; j < schedule[i].length; j++) {
                    if (requiredEmployees[i][j] > 0 && !x.getHoursWorking()[i][j] && x.getHoursAvailable()[i][j] && cnt > 0) {
                        addEmployeeToSchedule(i, j, x, workerSchedule);
                        cnt--;
                    }
                }
            }
        }
        /*
        /// TESTINGGGGGG
        System.out.println("Number of workers that we currently have");
        for (int i = 0; i < 7; i++){
            for (int j = 0; j < 24; j++){
                System.out.print(workerSchedule[i][j].getSlot().size() + " ");
            }
            System.out.println();
        }
        System.out.println("Required Employees");
        print2DArray(requiredEmployees);
        System.out.println("Total Required Employees");
        print2DArray(totalRequiredEmployees);
        /////////////////
        */
    }

    /**
     * Step three of the algorithm which fills up empty spots with workers, then fill empty with managers
     * If managers haven't worked their 40h, add them in wherever (they can sweep the floor or something)
     * @author Robbie Zhuang
     * @return nothing
     */
    public void fillUpTheRest() {
        // Add all employees to the merged schedule
        for (int i = 0; i < schedule.length; i++) {
            for (int j = 0; j < schedule[i].length; j++) {
                for (Employee x : managerSchedule[i][j].getSlot()) {
                    schedule[i][j].addEmployee(x);
                }
                for (Employee x : workerSchedule[i][j].getSlot()) {
                    schedule[i][j].addEmployee(x);
                }

            }
        }

        // Fill in the empty spots with workers
        for (int i = 0; i < schedule.length; i++) {
            for (int j = 0; j < schedule[i].length; j++) {
                if (requiredEmployees[i][j] > 0) {
                    for (Employee x : onlyWorkers) {
                        if (requiredEmployees[i][j] > 0 && !x.getHoursWorking()[i][j] && x.getHoursAvailable()[i][j]) {
                            addEmployeeToSchedule(i, j, x, schedule);
                        }
                    }
                    for (Employee x : onlyManagers) {
                        if (requiredEmployees[i][j] > 0 && !x.getHoursWorking()[i][j] && x.getHoursAvailable()[i][j]) {
                            addEmployeeToSchedule(i, j, x, schedule);
                        }
                    }
                }
            }
        }

        // If managers are under 40h
        for (Employee x : onlyManagers) {
            if (x.getTotalHours() < ((Manager) x).getMinWorkHours()) {
                int hoursRequired = ((Manager) x).getMinWorkHours() - x.getTotalHours();
                for (int i = 0; i < schedule.length; i++) {
                    for (int j = 0; j < schedule[i].length; j++) {
                        if (totalRequiredEmployees[i][j] > 0 && !x.getHoursWorking()[i][j] && x.getHoursAvailable()[i][j] && hoursRequired > 0) {
                            addEmployeeToSchedule(i, j, x, schedule);
                            hoursRequired--;
                        }
                    }
                }
            }
        }

        /// TESTINGGGGGG
        System.out.println("Number of everything");
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 24; j++) {
                System.out.print(schedule[i][j].getSlot().size() + " ");
            }
            System.out.println();
        }
        System.out.println("Required Employees");
        print2DArray(requiredEmployees);
        System.out.println("Total Required Employees");
        print2DArray(totalRequiredEmployees);
        /////////////////

    }

    public void addEmployeeToSchedule(int i, int j, Employee x, Timeslot[][] t) {
        t[i][j].addEmployee(x);
        x.setHourWorking(i, j, true);
        x.addHourOfWork();
        requiredEmployees[i][j]--;
    }

    public void removeEmployeeFromSchedule(int i, int j, Employee x, Timeslot[][] t) {
        // Set manager to not be working at that time and decrease the number of hours they will be
        // working for, finally decrease their hours overtime
        x.setHourWorking(i, j, false);
        x.subtractHourOfWork();
        if (totalRequiredEmployees[i][j] > t[i][j].size()) {
            requiredEmployees[i][j] = totalRequiredEmployees[i][j] - managerSchedule[i][j].size();
        } else {
            requiredEmployees[i][j] = 0;
        }
    }

    public boolean isScheduleFilled() {
        boolean filled = true;
        for (int i = 0; i < totalRequiredEmployees.length; i++) {
            for (int j = 0; j < totalRequiredEmployees[i].length; j++) {
                if (totalRequiredEmployees[i][j] - schedule[i][j].size() > 0) {
                    filled = false;
                    Recommendation r = new Recommendation(totalRequiredEmployees[i][j] - schedule[i][j].size(), i, j);
                    recommendationMessage += r.getRecommendation() + "\n";
                    recs.add(r);
                }
            }
        }
        return filled;
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
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("assets/Schedule.txt")));
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
                    Schedule.requiredEmployees[day][i] = requiredEmployees;
                    //schedule[day][i].setRequiredEmployees(requiredEmployees);
                }

            }
        } catch (Exception e) {
            System.out.println("*** Something wrong with the buffered reader in readHours");
        }
        for (int i = 0; i < Schedule.requiredEmployees.length; i++) {
            for (int j = 0; j < Schedule.requiredEmployees[i].length; j++) {
                totalRequiredEmployees[i][j] = Schedule.requiredEmployees[i][j];
            }
        }

    }

    private void readEmployeeInfo() {

        // Creating variables
        String str;
        String employeeType;

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("assets/EmployeeInfo.txt")));
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

    boolean addWorker(String name, String address, String pay, String day, String inHour, String outHour) {

        Worker w = new Worker();

//        try {
        w.setFullName(name);
        w.setAddress(address);
        w.setPay(Double.parseDouble(pay));
        for (int i = Integer.parseInt(inHour); i < Integer.parseInt(outHour); i++) {
            w.setHoursAvailable(dayToNum(day), i, true);
        }
//        } catch (Exception exception) {
//            System.out.println("*** Something wrong with addWorker");
//            return false;
//        }

        // Add worker to all employees
        System.out.println("Adding worker to all employees");
        this.allEmployees.add(w);

        System.out.println("Updating employee file");
        updateEmployeeFile();

        return true;
    }

    void addManager(String name, String address, String pay, String day, String inHour, String outHour) {

        Manager m = new Manager();

        m.setFullName(name);
        m.setAddress(address);
        m.setPay(Integer.parseInt(pay));
        for (int i = Integer.parseInt(inHour); i < Integer.parseInt(outHour); i++) {
            m.setHoursAvailable(dayToNum(day), i, true);
        }

        // Add manager to all employees
        System.out.println("Adding manager to employees");
        this.allEmployees.add(m);

        System.out.println("Updating employee file");
        updateEmployeeFile();
    }

    private int getDay(String response, Scanner input) {
        System.out.println("Enter day when available:");
        response = input.next();
        return dayToNum(response);
    }

    private void editEmployee() throws FileNotFoundException {

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
                    if (this.allEmployees.get(employeeNumber) instanceof Manager) {
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

    private void editName(int employeeNumber) {
        Scanner input = new Scanner(System.in);
        String response;
        System.out.println("Enter the new name of " + this.allEmployees.get(employeeNumber).getFullName());
        response = input.nextLine();
        this.allEmployees.get(employeeNumber).setFullName(response);
    }

    private void editAddress(int employeeNumber) {
        Scanner input = new Scanner(System.in);
        String response;
        System.out.println("Enter the new address of " + this.allEmployees.get(employeeNumber).getFullName());
        response = input.nextLine();
        this.allEmployees.get(employeeNumber).setAddress(response);
    }

    private void editSalary(int employeeNumber) {
        Scanner input = new Scanner(System.in);
        String response;
        System.out.println("Enter the new salary of " + this.allEmployees.get(employeeNumber).getFullName());
        response = input.nextLine();
        this.allEmployees.get(employeeNumber).setPay(Integer.parseInt(response));
    }

    private void editWage(int employeeNumber) {
        Scanner input = new Scanner(System.in);
        String response;
        System.out.println("Enter the new wage of " + this.allEmployees.get(employeeNumber).getFullName());
        response = input.nextLine();
        this.allEmployees.get(employeeNumber).setPay(Integer.parseInt(response));
    }

    private void editAvailability(int employeeNumber) {

        // Creating variables
        Scanner input = new Scanner(System.in);
        String response;
        int day;
        int inHour;
        int outHour;
        boolean newAvailability;

        // Displaying the availability to user
        displayAvailability(this.allEmployees.get(employeeNumber));

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
            this.allEmployees.get(employeeNumber).setHoursAvailable(day, i, newAvailability);
        }
    }

    void removeEmployee(int response) {
        this.allEmployees.remove(response);

        System.out.println("Updating employee file");
        try {
            updateEmployeeFile();
        } catch (Exception exception) {
            System.out.println("*** Something wrong with remove employee");
            exception.printStackTrace();
        }
    }

    private void displayEmployees() {
        for (int i = 0; i < this.allEmployees.size(); i++) {
            System.out.println("Employee #" + i + ": " + this.allEmployees.get(i).getFullName());
        }
    }

    private void displayAvailability(Employee employee) {
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

    private int dayToNum(String day) {
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
        } else if (day.equalsIgnoreCase("this")) {
            return 5;
        } else if (day.equalsIgnoreCase("U")) {
            return 6;
        } else {
            System.out.println("*** Day is wrong");
            return -1;
        }
    }

    private String numToDay(int day) {
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
            return "this";
        } else if (day == 6) {
            return "U";
        } else {
            System.out.println("*** Day is wrong");
            return "Error";
        }
    }

    private void updateEmployeeFile() {

        // Making sure all employees are sorted before the output starts
        organizeEmployees();

        try {
            PrintWriter output = new PrintWriter("assets/EmployeeInfo.txt");
            for (Employee employee : this.allEmployees) {
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
        } catch (Exception exception) {
            System.out.println("*** Something wrong with updateEmployeeFile");
            exception.printStackTrace();
        }
    }

    void listEmployees() {

        organizeEmployees();
//        displayEmployees();

    }

    void organizeEmployees() {

        System.out.println("Organizing Employees");

        // Creating variables to swap employee objects
        Employee swapToBack;
        boolean repeat = true;

        while (repeat) {
            // Bubbles sorting the managers by pay
            repeat = false;
            for (int i = 0; i < this.onlyManagers.size() - 1; i++) {
                if (this.onlyManagers.get(i).getPay() < this.onlyManagers.get(i + 1).getPay()) {
                    // Swapping the manager objects
                    swapToBack = this.onlyManagers.get(i);
                    this.onlyManagers.set(i, this.onlyManagers.get(i + 1));
                    this.onlyManagers.set(i + 1, swapToBack);
                    repeat = true;
                }
            }
        }

        repeat = true;
        while (repeat) {
            // Bubbles sorting the workers by pay
            repeat = false;
            for (int i = 0; i < this.onlyWorkers.size() - 1; i++) {
                if (this.onlyWorkers.get(i).getPay() < this.onlyWorkers.get(i + 1).getPay()) {
                    // Swapping the worker objects
                    swapToBack = this.onlyWorkers.get(i);
                    this.onlyWorkers.set(i, this.onlyWorkers.get(i + 1));
                    this.onlyWorkers.set(i + 1, swapToBack);
                    repeat = true;
                }
            }
        }

        // Displaying the employees
        for (int i = 0; i < this.onlyManagers.size(); i++) {
            System.out.println("Manager: " + this.onlyManagers.get(i).getFullName() + "\tSalary: " + this.onlyManagers.get(i).getPay());
        }
        for (int i = 0; i < this.onlyWorkers.size(); i++) {
            System.out.println("Employee: " + this.onlyWorkers.get(i).getFullName() + "\tWage: " + this.onlyWorkers.get(i).getPay());
        }
    }

    void runScheduler() {
        //this.dumpEmployees();
        //this.optimizeEmployees();
        this.scheduleManagers();
        this.scheduleWorkers();
        this.fillUpTheRest();
    }

    void displaySchedule() {

        for (int i = 0; i < 7; i++) {
            System.out.println("\nDay: " + numToDay(i));
            for (int j = 0; j < 24; j++) {
                System.out.println("Hour: " + j);

                for (int k = 0; k < schedule[i][j].getSlot().size(); k++) {
                    System.out.println(schedule[i][j].getSlot().get(k).getFullName());
                }
            }
        }

    }
}
