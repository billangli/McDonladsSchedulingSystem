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
    private int totalHoursAvailable = 0;

    // Getters and Setters

    /**
     * getFullName
     * This getter gets the full name from the selected employee
     *
     * @return the String containing the full name
     */
    String getFullName() {
        return fullName;
    }

    /**
     * setFullName
     * This setter sets the full name of this employee to the String fullName
     *
     * @param fullName is the new fullName of the employee
     */
    void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     * getAddress
     * This getter gets the address from the selected employee
     *
     * @return the String containing the address
     */
    String getAddress() {
        return address;
    }

    /**
     * setFullName
     * This setter sets the full name of this employee to the String fullName
     *
     * @param address is the new address of the employee
     */
    void setAddress(String address) {
        this.address = address;
    }

    /**
     * getPay
     * This is an abstract getter getting the salary/pay from an employee
     *
     * @return a double value containing the salary/pay
     */
    abstract public double getPay();

    /**
     * setPay
     * This abstract setter sets the new employee salary/wage to the pay variable
     *
     * @param pay is the double value containing the salary/wage
     */
    abstract public void setPay(double pay);

    /**
     * getHoursAvailable
     * This returns the boolean array hoursAvailable
     *
     * @return a boolean array indicating when the employee is available
     */
    boolean[][] getHoursAvailable() {
        return hoursAvailable;
    }

    /**
     * setHoursAvailable
     * This changes when the employee is available
     *
     * @param day          is the day to change
     * @param hour         is the hour to change
     * @param availability is true for available, false for not
     */
    void setHoursAvailable(int day, int hour, boolean availability) {
        this.hoursAvailable[day][hour] = availability;
        totalHoursAvailable++;
    }

    /**
     * getHoursWorking
     * This is a getter which gives a boolean array of when the employee is working
     *
     * @return a boolean array of when the employee is working
     */
    boolean[][] getHoursWorking() {
        return hoursWorking;
    }

    /**
     * setHourWorking
     * This setter changes when the employee is working
     *
     * @param i       is the day to change
     * @param j       is the hour to change
     * @param working is true for available, false for not
     */
    void setHourWorking(int i, int j, boolean working) {
        hoursWorking[i][j] = working;
    }

    /**
     * getTotalHours
     *
     * @return an int indicating the total hours worked by an employee
     */
    int getTotalHours() {
        return totalHours;
    }

    /**
     * setTotalHours
     * This method changes the total hours that an employee has worked
     *
     * @param totalHours is the new totalHours
     */
    void setTotalHours(int totalHours) {
        this.totalHours = totalHours;
    }

    /**
     * getTotalHoursAvailable
     * This is a getter which gets the total number of hours an employee is available for
     *
     * @return an int representing the hours available
     */
    int getTotalHoursAvailable() {
        return totalHoursAvailable;
    }

    // Functions

    /**
     * addHourOfWork
     * It adds another hour of work to the total number of hours worked for an employee
     */
    void addHourOfWork() {
        this.totalHours++;
    }

    /**
     * subtractHourOfWork
     * It subtracts an hour of work from the total number of hours worked for an employee
     */
    void subtractHourOfWork() {
        this.totalHours--;
    }

    /**
     * compareTo
     * This compares the salary/wage of an employee
     *
     * @param e is the employee being compared with
     * @return negative if this employee earns more than the parameter employee, positive if opposite
     */
    public int compareTo(Employee e) {
        return (int) (e.getPay() - this.getPay());
    }

    /**
     * compare
     * This compares the salary of two employees
     *
     * @param a is one employee
     * @param b is another employee
     * @return negative in if a earns more than b, positive if opposite
     */
    public int compare(Employee a, Employee b) {
        return (int) (b.getPay() - a.getPay());
    }
}
