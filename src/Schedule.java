import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

/**
 * @Author Robbie Zhuang, Bill Li
 * The schedule objects holds and generates the schedule
 */
class Schedule {

    // Each element of the schedule 2D array contains Timeslot objects
    // which is essentially an arraylist containing employees
    private static Timeslot[][] schedule;
    // Number of employees required at each 1h block
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
    Schedule() throws FileNotFoundException {

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
        // separate managers and workers into seperate arrays
        for (Employee x : allEmployees) {
            if (x instanceof Manager) {
                onlyManagers.add(x);
            } else if (x instanceof Worker) {
                onlyWorkers.add(x);
            }
        }
    }

    /**
     * determineHour method
     * It converts an hour string to an integer value
     *
     * @param str is the string that the hour is converted from
     * @return the integer value of the hour
     * @author Bill Li
     */
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

    /**
     * determineDay method
     * This method takes in a string value of the day and returns the integer value of it
     *
     * @param str is the string value of the day
     * @return the integer value of the day
     * @author Bill Li
     */
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

        System.out.println("*** Something wrong with determineDay");
        return -1;
    }

    // Getters (and Setters?)

    /**
     * This method returns the schedule 2D array
     *
     * @return Timeslot[][] schedule
     * @author Robbie Zhuang
     */
    static Timeslot[][] getSchedule() {
        return schedule;
    }

    /**
     * Returns the 2D integer array containing the mumber of required employees
     *
     * @return int[][] totalRequiredEmployees
     * @author Robbie Zhuang
     */
    static int[][] getTotalRequiredEmployees() {
        return totalRequiredEmployees;
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

    public ArrayList<Employee> getAllEmployees() {
        return allEmployees;
    }

    /**
     * Schedule Employees method that calls the three steps to scheduling employees
     *
     * @author Robbie Zhuang
     */
    public void scheduleEmployees() {
        scheduleManagers();
        scheduleWorkers();
        fillUpTheRest();
    }

    /**
     * Step one of the algorithm which schedules the managers first
     * ADD A DESCRIPTION HERE OF HOW IT WORKS!!! ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     *
     * @author Robbie Zhuang
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
    }

    /**
     * Step two of the algorithm which schedules the workers next
     * Figure out what number gives workers a roughly even amount of time to work and then puts them in.
     *
     * @return nothing
     * @author Robbie Zhuang
     */
    public void scheduleWorkers() {
        // First figures out how long each worker should work for approximately
        int hoursLeftToFill = 0;
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 24; j++) {
                hoursLeftToFill += requiredEmployees[i][j];
            }
        }
        int averageHoursOfWorkLeft = 0;
        if (onlyWorkers.size() == 0) {
            averageHoursOfWorkLeft = hoursLeftToFill;
        } else {
            averageHoursOfWorkLeft = hoursLeftToFill / onlyWorkers.size();
        }

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
    }

    /**
     * Step three of the algorithm which fills up empty spots with workers, then fill empty with managers
     * If managers haven't worked their 40h, add them in wherever (they can sweep the floor or something)
     *
     * @return nothing
     * @author Robbie Zhuang
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
    }

    /**
     * Used to add employees to a schedule and update data for employees
     * @author Robbie Zhuang
     * @param i day
     * @param j hour
     * @param x employee object
     * @param t the timeslot 2D array
     */
    public void addEmployeeToSchedule(int i, int j, Employee x, Timeslot[][] t) {
        t[i][j].addEmployee(x);
        x.setHourWorking(i, j, true);
        x.addHourOfWork();
        requiredEmployees[i][j]--;
    }

    /**
     * Remove employees from a schedule
     * @author Robbie Zhuang
     * @param i day
     * @param j hour
     * @param x employee object
     * @param t the timeslot 2D array
     */
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

    /**
     * Checks if all spots in the schedule are able to be filled
     * @return boolean whether or not schedule is filled
     */
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

    /**
     * readHours method
     * It reads the number of required employees for each time slot from the text file
     *
     * @author Bill Li
     */
    private void readHours() {

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
                requiredEmployees = 0;
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
                    Schedule.totalRequiredEmployees[day][i] = requiredEmployees;
                    //schedule[day][i].setRequiredEmployees(requiredEmployees);
                }

            }
        } catch (Exception e) {
            System.out.println("*** Something wrong with the buffered reader in readHours");
        }
        for (int i = 0; i < Schedule.totalRequiredEmployees.length; i++) {
            for (int j = 0; j < Schedule.totalRequiredEmployees[i].length; j++) {
                Schedule.requiredEmployees[i][j] = Schedule.totalRequiredEmployees[i][j];
            }
        }


        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 24; j++) {
                System.out.print(totalRequiredEmployees[i][j] + " ");
            }
            System.out.println();
        }
    }

    /**
     * readEmployeeInfo
     * It reads information of each employee from the EmployeeInfo.txt
     *
     * @author Bill Li
     */
    private void readEmployeeInfo() {

        // Creating variables
        String str;
        String employeeType;

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("assets/EmployeeInfo.txt")));
            while ((str = br.readLine()) != null) {

                // Creating a new employee
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

    /**
     * readManager
     * This method reads info from the EmployeeInfo.txt to create a worker
     *
     * @param m  is the manager object to be added
     * @param br is the BufferedReader
     * @return the updated manager object
     * @throws IOException in case something goes on with IO
     * @author Bill Li
     */
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

    /**
     * readWorker
     * This method reads info from the EmployeeInfo.txt to create a worker
     *
     * @author Bill Li
     * @param w is the worker object to be added
     * @param br is the BufferedReader
     * @return the updated worker object
     * @throws IOException in case something goes on with IO
     */
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

    /**
     * addWorker
     * This method adds a worker to the employee arrayList
     *
     * @author Bill Li
     * @param name is the name of the new worker
     * @param address is the address of the new worker
     * @param pay is the wage of the new worker
     */
    void addWorker(String name, String address, String pay) {

        Worker w = new Worker();

        w.setFullName(name);
        w.setAddress(address);
        w.setPay(Double.parseDouble(pay));

        // Add worker to all employees
        System.out.println("Adding worker to all employees");
        this.allEmployees.add(w);

        System.out.println("Updating employee file");
        updateEmployeeFile();
    }

    /**
     * addManager
     * This method adds a manager to employee arrayList
     *
     * @author Bill Li
     * @param name is the name of the new manager
     * @param address is the address of the new manager
     * @param pay is the salary of the new manager
     */
    void addManager(String name, String address, String pay) {

        Manager m = new Manager();

        m.setFullName(name);
        m.setAddress(address);
        m.setPay(Integer.parseInt(pay));

        // Add manager to all employees
        System.out.println("Adding manager to employees");
        this.allEmployees.add(m);

        System.out.println("Updating employee file");
        updateEmployeeFile();
    }

    /**
     * editName
     * This method changes the name of an employee
     *
     * @author Bill Li
     * @param employeeNumber is the employee number to have something changed
     * @param name is the new address
     */
    void editName(int employeeNumber, String name) {
        this.allEmployees.get(employeeNumber).setFullName(name);
    }

    /**
     * editAddress
     * This method changes the address of an employee
     *
     * @author Bill Li
     * @param employeeNumber is the employee number to have something chnaged
     * @param address is the new address
     */
    void editAddress(int employeeNumber, String address) {
        this.allEmployees.get(employeeNumber).setAddress(address);
    }

    /**
     * editPay
     * This method changes the salary/wage of an employee
     *
     * @author Bill Li
     * @param employeeNumber is the employee number
     * @param pay is the employee's salary/wage
     */
    void editPay(int employeeNumber, double pay) {
        this.allEmployees.get(employeeNumber).setPay(pay);
    }

    /**
     * removeEmployee
     * This method removes a selected employee
     *
     * @author Bill Li
     * @param response is the employee #
     */
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

    /**
     * dayToNum method
     * This method converts day from from String to int
     *
     * @author Bill Li
     * @param day is the day in String
     * @return day in int
     */
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

    /**
     * updateEmployeeFile
     * It updates the employee text file
     *
     * @author Bill Li
     */
    void updateEmployeeFile() {

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

    /**
     * organizeEmployees
     * Rearranging the employees in order of pay
     *
     * @author Bill Li
     */
    void organizeEmployees() {

        System.out.println("Organizing Employees");

        // Sorting managers and employees
        Collections.sort(onlyManagers);
        Collections.sort(onlyWorkers);

        // Displaying the employees
        for (Employee onlyManager : this.onlyManagers) {
            System.out.println("Manager: " + onlyManager.getFullName() + "\tSalary: " + onlyManager.getPay());
        }
        for (Employee onlyWorker : this.onlyWorkers) {
            System.out.println("Employee: " + onlyWorker.getFullName() + "\tWage: " + onlyWorker.getPay());
        }
    }
}
