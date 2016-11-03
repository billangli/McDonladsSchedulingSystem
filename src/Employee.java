/*
 Employee.java
 This creates a general employee object

 Created by Bill Li on 2016-10-30.
 */

import java.util.Comparator;

abstract class Employee implements Comparator<Employee>, Comparable<Employee> {

    // Setting up variables
    private String employeeType;
    private String fullName;
    private String address;
    private String employeeNumber;
    private boolean[][] hoursAvailable = new boolean[7][24];
    private boolean[][] hoursWorking = new boolean[7][24];
    private int totalHours = 0;

    public String getEmployeeType() {
        return employeeType;
    }

    public void setEmployeeType(String employeeType) {
        this.employeeType = employeeType;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
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

    public boolean[][] getHoursAvailable() {
        return hoursAvailable;
    }

    public void setHoursAvailable(int day, int hour, boolean availability) {
        this.hoursAvailable[day][hour] = availability;
    }

    public boolean[][] getHoursWorking() {
        return hoursWorking;
    }

    public void setHoursWorking(boolean[][] hoursWorking) {
        this.hoursWorking = hoursWorking;
    }

    public int getTotalHours() {
        return totalHours;
    }

    public void setTotalHours(int totalHours) {
        this.totalHours = totalHours;
    }

    public void addHourOfWork() {
        this.totalHours++;
    }

    public void subtractHourOfWork() {
        this.totalHours--;
    }

    public int compareTo(Employee e) {
        return e.getTotalHours() - this.getTotalHours();
    }

    public int compare(Employee a, Employee b) {
        return b.totalHours - a.totalHours;
    }
}
