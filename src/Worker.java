/*
 Worker.java
 This creates an object for a worker
 Worker is an extension of employee

 Created by Bill Li on 2016-10-30.
 */

public class Worker extends Employee{

    // Setting up variables
    private double wage;

    // Default constructor for worker
    Worker() {
        this.setEmployeeType("Worker");
    }

    public double getPay() {
        return wage;
    }

    public void setPay(double wage) {
        this.wage = wage;
    }
}
