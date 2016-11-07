import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by RobbieZhuang on 2016-11-06.
 */
public class GUIScheduleDisplayPanel extends JPanel {
    String[] columnNames = new String[25];
    String[] daysOfTheWeek = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
    Object[][] data = new Object[8][25];
    private JLabel title;
    private JTable schedule;
    private JButton mainMenu;

    public GUIScheduleDisplayPanel() {
        columnNames[0] = "X";
        for (int i = 1; i < columnNames.length; i++) {
            columnNames[i] = i - 1 + "";
        }
        for (int i = 1; i < 25; i++) {
            data[0][i] = (i - 1) + ":00";
        }
        for (int j = 1; j < 8; j++) {
            data[j][0] = daysOfTheWeek[j - 1];
        }

        for (int i = 0; i < 7; i++) {

            for (int j = 0; j < 24; j++) {
                for (Employee x : Schedule.getSchedule()[i][j].getSlot()) {
                    if (data[i + 1][j + 1] == null) {
                        data[i + 1][j + 1] = x.getFullName() + "\n";
                    } else {
                        data[i + 1][j + 1] += x.getFullName() + "\n";
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
        schedule = new JTable(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                //all cells false
                return false;
            }
        };
        schedule.setRowHeight(schedule.getRowHeight() * maxRowHeight);
        schedule.setFont(new Font("Serif", Font.BOLD, 8));
        mainMenu = new JButton("Go back to main menu");
        mainMenu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Open up the main panel
                switchPanel(new GUIMainPanel());
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