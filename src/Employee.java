/*
 Employee.java
 @version 1.0
 @author Bill Li
 @date 7/11/2016
 This creates an abstract class employee which is the superclass of the worker and manager objects
 */

// Imports
import java.util.Comparator;

abstract class Employee implements Comparator<Employee>, Comparable<Employee> {

    // Setting up variables
    private String fullName;
    private String address;
    private String employeeNumber;
    private boolean[][] hoursAvailable = new boolean[7][24];
    private boolean[][] hoursWorking = new boolean[7][24];
    private int totalHours = 0;
    private int totalHoursAvaliable = 0;

    // Getters and Setters
    String getFullName() {
        return fullName;
    }

    void setFullName(String fullName) {
        this.fullName = fullName;
    }

    String getAddress() {
        return address;
    }

    void setAddress(String address) {
        this.address = address;
    }

    abstract public double getPay();

    abstract public void setPay(double pay);

    boolean[][] getHoursAvailable() {
        return hoursAvailable;
    }

    void setHoursAvailable(int day, int hour, boolean availability) {
        this.hoursAvailable[day][hour] = availability;
        totalHoursAvaliable++;
    }

    public boolean[][] getHoursWorking() {
        return hoursWorking;
    }

    public void setHourWorking(int i, int j, boolean working) {
        hoursWorking[i][j] = working;
    }

    int getTotalHours() {
        return totalHours;
    }

    // Functions
    void addHourOfWork() {
        this.totalHours++;
    }

    void subtractHourOfWork() {
        this.totalHours--;
    }

    public int compareTo(Employee e) {
        return (int) (e.getPay() - this.getPay());
    }

    public int compare(Employee a, Employee b) {
        return (int) (b.getPay() - a.getPay());
    }
}
