import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by RobbieZhuang on 2016-11-06.
 */
public class ScheduleDisplayPanel extends JPanel {
    String[] columnNames = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
    Object[][] data = new Object[25][8];
    private JLabel title;
    private JTable schedule;
    private JButton mainMenu;

    public ScheduleDisplayPanel() {
        for (int i = 1; i < 25; i++) {
            data[i][0] = i + ":00";
        }
        for (int j = 1; j < 8; j++) {
            data[0][j] = columnNames[j - 1];
        }

        for (int i = 0; i < 7; i++) {

            for (int j = 0; j < 24; j++) {
                for (Employee x : Schedule.getSchedule()[i][j].getSlot()) {
                    if (data[j + 1][i + 1] == null) {
                        data[j + 1][i + 1] = x.getFullName() + "\n";
                    } else {
                        data[j + 1][i + 1] += x.getFullName() + "\n";
                    }
                    System.out.println("Something is happening");
                }
            }
        }
        int maxRowHeight = 0;
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 24; j++) {
                if (Schedule.getTotalRequiredEmployees()[i][j] > maxRowHeight) {
                    maxRowHeight = Schedule.getTotalRequiredEmployees()[i][j];
                }
            }
        }
        schedule = new JTable(data, columnNames);
        schedule.setRowHeight(schedule.getRowHeight() * maxRowHeight);

        mainMenu = new JButton("Go back to main menu");
        mainMenu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Open up the main panel
                switchPanel(new MainPanel());
            }
        });
        this.add(schedule);
        this.add(mainMenu);
        this.repaint();
    }

    void switchPanel(JPanel p) {
        Scheduler.g.switchPanel(this, p);
    }
}