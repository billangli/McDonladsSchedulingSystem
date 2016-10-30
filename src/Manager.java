/*
 Manager.java
 This is an object for the manager
 Managers are an extension to employee

 Created by Bill Li on 2016-10-30.
 */

public class Manager extends Employee{

    // Setting up variables
    private double salary;

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }
}
