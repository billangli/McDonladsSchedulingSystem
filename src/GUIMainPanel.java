import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by RobbieZhuang on 2016-11-05.
 */
public class GUIMainPanel extends JPanel {
    private JButton editWorker;
    private JButton listEmployees;
    private JButton runScheduler;
    private JButton displaySchedule;
    private JButton quit;
    private JLabel title;

    public GUIMainPanel() {
        this.setLayout(new BorderLayout());

        title = new JLabel("McDonlads Scheduling System 69.420", SwingConstants.CENTER);
        title.setFont(title.getFont().deriveFont(52.0f));
        this.add(title, BorderLayout.NORTH);

        JPanel buttons = new JPanel(new FlowLayout());

        editWorker = new JButton("Add/Edit/Remove Employee");
        editWorker.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Open up the worker editing panel
                switchPanel(new GUIManageWorkerPanel());
            }
        });

        listEmployees = new JButton("List out all the employees");
        listEmployees.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Open up panel displaying all employees
                switchPanel(new ListEmployeesPanel());
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


        buttons.add(editWorker);
        buttons.add(listEmployees);
        buttons.add(runScheduler);
        buttons.add(displaySchedule);
        buttons.add(quit);

        this.add(buttons, BorderLayout.CENTER);
        /*
        ImageIcon icon = new ImageIcon("assets/McDonalds.PNG");
        JLabel logo = new JLabel();
        logo.setIcon(icon);
        this.add(logo, BorderLayout.SOUTH);
        */
    }


    void switchPanel(JPanel p) {
        Scheduler.g.switchPanel(this, p);
    }
}