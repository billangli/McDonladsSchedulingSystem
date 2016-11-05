import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by RobbieZhuang on 2016-11-05.
 */
class MainPanel extends JPanel {
    private JButton editWorker;
    private JButton editManager;
    private JButton listEmployees;
    private JButton runScheduler;
    private JButton displaySchedule;
    private JLabel title;

    MainPanel() {
        this.setLayout(new FlowLayout());
        title = new JLabel("McDonlads Scheduling System 69.420");
        title.setFont(title.getFont().deriveFont(52.0f));
        this.add(title);


        editWorker = new JButton("Add/Edit/Remove Worker");
        editWorker.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Open up the worker editing panel
            }
        });

        editManager = new JButton("Add/Edit/Remove Manager");
        editManager.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Open up the manager editing panel
            }
        });

        listEmployees = new JButton("List out all the employees");
        listEmployees.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Open up panel displaying all employees
            }
        });

        runScheduler = new JButton("Runs the scheduler");
        runScheduler.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Runs scheduler
                // Add loading bar or smth i guess
            }
        });

        displaySchedule = new JButton("Displays the schedule");
        displaySchedule.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Open up the schedule panel
            }
        });


        this.add(editManager);
        this.add(editWorker);
        this.add(listEmployees);
        this.add(runScheduler);
        this.add(displaySchedule);
    }
}