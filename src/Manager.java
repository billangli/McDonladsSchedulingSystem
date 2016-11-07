/*
 Manager.java
 @version 1.0
 @author Bill Li
 @date 2016-10-30
 This is an object for the manager, Managers are an subclass to employee
 */

public class Manager extends Employee {

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
     * Returns the salary of the manager
     * @return The salary
     */
    public double getPay() {
        return salary;
    }

    /**
     * Modifys the salary of the manager
     * @param salary, in the form of a double
     */
    public void setPay(double salary) {
        this.salary = salary;
    }

    /**
     * Gets the minimum number of hours that managers have to work
     * @return the integer
     */
    public int getMinWorkHours() {
        return minWorkHours;
    }
}
