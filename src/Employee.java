/*
 Employee.java
 This creates a general employee object

 Created by Bill Li on 2016-10-30.
 */

import java.util.Comparator;

abstract class Employee implements Comparator<Employee>, Comparable<Employee> {

    // Setting up variables
    private String fullName;
    private String address;
    private String employeeNumber;
    private boolean[][] hoursAvailable = new boolean[7][24];
    private boolean[][] hoursWorking = new boolean[7][24];
    private int totalHours = 0;

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

    public String getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    abstract public double getPay();

    abstract public void setPay(double pay);

    boolean[][] getHoursAvailable() {
        return hoursAvailable;
    }

    void setHoursAvailable(int day, int hour, boolean availability) {
        this.hoursAvailable[day][hour] = availability;
    }

    public boolean[][] getHoursWorking() {
        return hoursWorking;
    }

    public void setHoursWorking(boolean[][] hoursWorking) {
        this.hoursWorking = hoursWorking;
    }

    int getTotalHours() {
        return totalHours;
    }

    public void setTotalHours(int totalHours) {
        this.totalHours = totalHours;
    }

    void addHourOfWork() {
        this.totalHours++;
    }

    void subtractHourOfWork() {
        this.totalHours--;
    }

    public int compareTo(Employee e) {
        return e.getTotalHours() - this.getTotalHours();
    }

    public int compare(Employee a, Employee b) {
        return b.totalHours - a.totalHours;
    }
}
