import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by RobbieZhuang on 2016-11-05.
 */
public class GUIMainPanel extends JPanel {
    private JButton editWorker;
    private JButton editManager;
    private JButton listEmployees;
    private JButton runScheduler;
    private JButton displaySchedule;
    private JButton quit;
    private JLabel title;

    public GUIMainPanel() {
        this.setLayout(new FlowLayout());
        title = new JLabel("McDonlads Scheduling System 69.420");
        title.setFont(title.getFont().deriveFont(52.0f));
        this.add(title);


        editWorker = new JButton("Add/Edit/Remove Worker");
        editWorker.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Open up the worker editing panel
                switchPanel(new GUIManageWorkerPanel());
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
                System.out.println("SOMETHING");
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

        quit = new JButton("Quit");
        quit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Quit program
                Scheduler.getGUI().quit();
            }
        });


        this.add(editWorker);
        this.add(editManager);
        this.add(listEmployees);
        this.add(runScheduler);
        this.add(displaySchedule);
        this.add(quit);
    }

    void switchPanel(JPanel p) {
        Scheduler.g.switchPanel(this, p);
    }
}