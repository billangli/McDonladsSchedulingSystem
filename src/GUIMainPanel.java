/*
 GUIMainPanel.java
 @version 1.0
 @author Robbie Zhuang
 @date 7/11/2016
 This is the main menu panel
 */

// Imports

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUIMainPanel extends JPanel {

    private JButton editWorker;
    private JButton listEmployees;
    private JButton runScheduler;
    private JButton displaySchedule;
    private JButton quit;
    private JLabel title;

    public GUIMainPanel() {
        this.setLayout(new BorderLayout());

        title = new JLabel("McDonlads Scheduling System", SwingConstants.CENTER);
        title.setFont(title.getFont().deriveFont(52.0f));
        this.add(title, BorderLayout.NORTH);

        JPanel buttons = new JPanel(new GridLayout(2, 2));

        editWorker = new JButton("Add/Edit/Remove Employee");
        editWorker.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Open up the worker editing panel
                switchPanel(new GUIManageEmployeePanel());
            }
        });

        listEmployees = new JButton("List out all the employees");
        listEmployees.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Open up panel displaying all employees
                switchPanel(new GUIListEmployeesPanel());
            }
        });

        runScheduler = new JButton("Runs the scheduler");
        runScheduler.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Runs scheduler
                Scheduler.s.scheduleEmployees();
                if (Scheduler.getS().isScheduleFilled()) {
                    JFrame frame = new JFrame();
                    JOptionPane.showMessageDialog(frame, "Servants successfully scheduled.");
                } else {
                    JFrame frame = new JFrame();
                    JOptionPane.showMessageDialog(frame, Schedule.getRecommendationMessage());
                }
            }
        });

        displaySchedule = new JButton("Displays the schedule");
        displaySchedule.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Open up the schedule panel
                switchPanel(new GUIScheduleDisplayPanel());
            }
        });

        buttons.add(editWorker);
        buttons.add(listEmployees);
        buttons.add(runScheduler);
        buttons.add(displaySchedule);

        this.add(buttons, BorderLayout.CENTER);
    }

    /**
     * switchPanel
     * Calls the method from GUI to switch panels
     *
     * @param p JPanel that is to be switched to
     */
    void switchPanel(JPanel p) {
        Scheduler.g.switchPanel(this, p);
    }
}