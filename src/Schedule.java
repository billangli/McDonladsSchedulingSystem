import java.util.ArrayList;

/**
 * Created by RobbieZhuang on 2016-10-31.
 */
public class Schedule {

    // Our take on building a 2d array of ARRAYLISTS, Timeslot is the individual ArrayList that holds Employee objects
    Timeslot[][] table;

    // Numnber of employees required at each 1h block
    int[][] requiredEmployees = new int[7][24];

    // Employees
    ArrayList<Employee> allEmployees = new ArrayList<Employee>();
    ArrayList<Employee> allWorkers = new ArrayList<Employee>();

    // Build constructor for a schedule
    // The schedule keeps track of how many people are in each 1h block
    public Schedule() {
        table = new Timeslot[7][24];
        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < table[i].length; j++) {
                table[i][j] = new Timeslot(requiredEmployees[i][j]);
            }
        }
    }

    public void dumpEmployees() {
        for (Employee e : allEmployees) {
            for (int i = 0; i < 7; i++) {
                for (int j = 0; j < 24; j++) {
                    if (e.getHoursAvailable()[i][j]) {
                        table[i][j].addEmployee(e);
                        e.addHourOfWork();
                    }
                }
            }
        }
    }

    public void optimizeEmployees() {
        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < table[i].length; j++) {
                if (table[i][j].currentNumberOfEmployees() > table[i][j].getRequiredEmployees()) {
                    int max = 0;
                    Employee x = new Employee();
                    for (Employee e : table[i][j].getEmployeesAtSlot()) {
                        // If e is not a manager
                        if (!(e instanceof Manager) && e.getTotalHours() > max) {
                            x = e;
                        }
                    }
                    table[i][j].getEmployeesAtSlot().remove(x);
                }
            }
        }
    }
}
