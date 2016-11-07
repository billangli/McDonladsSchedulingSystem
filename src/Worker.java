/*
 Worker.java
 This creates an object for a worker
 Worker is an extension of employee

 Created by Bill Li on 2016-10-30.
 */

public class Worker extends Employee {

    // Setting up variables
    private double wage;

    // Default constructor for worker
    Worker() {
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 24; j++) {
                this.setHoursAvailable(i, j, false);
            }
        }
    }

    /**
     * getPay
     * Overrides the abstract method
     * It gets the wage of a worker
     *
     * @return double as the wage
     */
    public double getPay() {
        return wage;
    }

    /**
     * setPay
     * Overrides the abstract method
     *
     * @param wage is the double wage to be set
     */
    public void setPay(double wage) {
        this.wage = wage;
    }
}
