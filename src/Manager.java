/*
 Manager.java
 @version 1.0
 @author Bill Li
 @date 2016-10-30
 This is an object for the manager, Managers are an subclass to employee
 */

public class Manager extends Employee {

    // Setting up variables
    private static int minWorkHours = 40;
    private double salary;

    // Default constructor for manager
    Manager() {
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 24; j++) {
                this.setHoursAvailable(i, j, false);
            }
        }
    }

    /**
     * getPay
     * Overrides the abstract method
     * It gets the salary of a manager
     *
     * @return double as the salary
     */
    public double getPay() {
        return salary;
    }

    /**
     * setPay
     * Overrides the abstract method
     *
     * @param salary is the double salary to be set
     */
    public void setPay(double salary) {
        this.salary = salary;
    }

    /**
     * getMinWorkHours
     * Gets the min hours for a manager to work
     *
     * @return the int of minimum hours
     */
    int getMinWorkHours() {
        return minWorkHours;
    }
}
