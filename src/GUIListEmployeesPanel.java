/*
 ListEmployeesPanel.java
 @version 1.0
 @author Bill Li
 @date 7/11/2016
 This panel lists the existing employees on a panel\
 */

// Imports
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUIListEmployeesPanel extends JPanel {
    Object[][] data = new Object[Scheduler.s.getAllEmployees().size()][6];
    private JLabel titleLabel = new JLabel("Employee List", SwingConstants.CENTER);
    private String[] columnNames = {"Employee #", "Type", "Name", "Address", "Salary/Wage", "Hours Worked"};
    private int[] columnWidth = {100, 80, 150, 150, 100, 100};
    private JTable table;
    private JButton menuButton;

    /**
     * The default constructor for creating a listEmployeePanel
     */
    GUIListEmployeesPanel() {
        this.setLayout(new BorderLayout());

        Scheduler.s.organizeEmployees();
        titleLabel.setFont(titleLabel.getFont().deriveFont(52.0f));

        int counter = 0;
        for (int i = 0; i < Scheduler.s.getOnlyManagers().size(); i++) {
            addManager(counter, i);
            counter++;
        }
        for (int i = 0; i < Scheduler.s.getOnlyWorkers().size(); i++) {
            addWorker(counter, i);
            counter++;
        }

        int maxRowHeight = 0;
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 24; j++) {
                if (Schedule.getTotalRequiredEmployees()[i][j] > maxRowHeight) {
                    maxRowHeight = Schedule.getTotalRequiredEmployees()[i][j];
                }
            }
        }

        // Button to go back to menu
        menuButton = new JButton("Menu");
        menuButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Open up the worker editing panel
                switchPanel(new GUIMainPanel());
            }
        });

        table = new JTable(data, columnNames);
        table.setRowHeight(20);
        for (int i = 0; i < 6; i++) {
            table.getColumnModel().getColumn(i).setPreferredWidth(columnWidth[i]);
        }

        this.add(titleLabel, BorderLayout.NORTH);
        JScrollPane scroll = new JScrollPane(table);
        this.add(scroll, BorderLayout.CENTER);
        this.add(menuButton, BorderLayout.SOUTH);
    }

    /**
     * addManager
     * This method adds a manager's information to the JTable
     *
     * @param counter is the location of the JTable to add to
     * @param i       is the employee # to add
     * @author Bill Li
     */
    private void addManager(int counter, int i) {
        data[counter][0] = "000" + i;
        data[counter][1] = "Manager";
        data[counter][2] = Scheduler.s.getOnlyManagers().get(i).getFullName();
        data[counter][3] = Scheduler.s.getOnlyManagers().get(i).getAddress();
        data[counter][4] = "$" + Scheduler.s.getOnlyManagers().get(i).getPay() + "/yr";
        data[counter][5] = Scheduler.s.getOnlyManagers().get(i).getTotalHours();
    }

    /**
     * addWorker
     * This method add's a worker's information to the JTable
     *
     * @param counter is the location of the JTable to add to
     * @param i       is the employee # to add
     * @author Bill Li
     */
    private void addWorker(int counter, int i) {
        data[counter][0] = "100" + i;
        data[counter][1] = "Worker";
        data[counter][2] = Scheduler.s.getOnlyWorkers().get(i).getFullName();
        data[counter][3] = Scheduler.s.getOnlyWorkers().get(i).getAddress();
        data[counter][4] = "$" + Scheduler.s.getOnlyWorkers().get(i).getPay() + "/hr";
        data[counter][5] = Scheduler.s.getOnlyWorkers().get(i).getTotalHours();
    }

    /**
     * switchPanel
     * This switches to another panel
     *
     * @param p is the new panel to switch to
     */
    void switchPanel(JPanel p) {
        Scheduler.g.switchPanel(this, p);
    }
}