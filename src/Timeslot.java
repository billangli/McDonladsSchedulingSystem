import java.util.ArrayList;

/**
 * Created by RobbieZhuang on 2016-10-31.
 */
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

    public int getRequiredEmployees() {
        return requiredEmployees;
    }

    public void setRequiredEmployees(int requiredEmployees) {
        this.requiredEmployees = requiredEmployees;
    }

    public ArrayList<Employee> getSlot() {
        return this;
    }

    public int currentNumberOfEmployees() {
        return this.size();
    }
}