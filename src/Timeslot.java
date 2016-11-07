/*
 Timeslot.java
 @version 1.0
 @author Robbie Zhuang
 @date 7/11/2016
 This object extends an arraylist that is used to store employees in a schedule
 */

import java.util.ArrayList;

public class Timeslot extends ArrayList<Employee> {

    private int requiredEmployees;

    public Timeslot(int requiredEmployees) {
        this.requiredEmployees = requiredEmployees;
    }

    public Timeslot() {
        this.requiredEmployees = 0;
    }

    public void addEmployee(Employee e) {
        this.add(e);
    }

    public void removeEmployee(Employee e) {
        for (Employee x : this) {
            if (x.equals(e)) {
                this.remove(x);
            }
        }
    }
    
    /**
     * getSlot
     * @returns this arraylist
     */
    public ArrayList<Employee> getSlot() {
        return this;
    }

}