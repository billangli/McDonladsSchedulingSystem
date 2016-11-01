import java.util.ArrayList;

/**
 * Created by RobbieZhuang on 2016-10-31.
 */
public class Timeslot {
    private int requiredEmployees;
    private ArrayList<Employee> slot = new ArrayList<Employee>();

    public Timeslot(int requiredEmployees) {
        this.requiredEmployees = requiredEmployees;
    }

    public void addEmployee(Employee e) {
        slot.add(e);
    }

    public void removeEmployee(Employee e) {
        for (Employee x : slot) {
            if (x.equals(e)) {
                slot.remove(x);
            }
        }
    }

    public int getRequiredEmployees() {
        return requiredEmployees;
    }

    public void setRequiredEmployees(int requiredEmployees) {
        this.requiredEmployees = requiredEmployees;
    }

    public ArrayList<Employee> getEmployeesAtSlot() {
        return slot;
    }

    public int currentNumberOfEmployees() {
        return slot.size();
    }
}