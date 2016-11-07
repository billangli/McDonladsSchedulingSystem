/*
 GUIScheduleDisplayPanel.java
 @version 1.0
 @author Robbie Zhuang
 @date 2016-11-06
 This is the schedule display panel
 */

// Imports

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUIScheduleDisplayPanel extends JPanel {
    //    String[] columnNames = new String[8];
    String[] daysOfTheWeek = {"Time", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
    Object[][] data = new Object[24][8];
    private JLabel title;
    private JTable schedule;
    private JButton mainMenu;

    GUIScheduleDisplayPanel() {

        this.setLayout(new BorderLayout());
        for (int i = 0; i < 24; i++) {
            data[i][0] = i + ":00";
        }

        for (int i = 0; i < 7; i++) {

            for (int j = 0; j < 24; j++) {
                for (Employee x : Schedule.getSchedule()[i][j].getSlot()) {
                    if (data[j][i + 1] == null) {
                        if (x instanceof Manager) {
                            data[j][i + 1] = "M ~ " + x.getFullName() + "\n";
                        } else {
                            data[j][i + 1] = "W ~ " + x.getFullName() + "\n";
                        }
                    } else {
                        if (x instanceof Manager) {
                            data[j][i + 1] += "M ~ " + x.getFullName() + "\n";
                        } else {
                            data[j][i + 1] += "W ~ " + x.getFullName() + "\n";
                        }
                    }
                }
            }
        }


        int maxRowHeight = 1;
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 24; j++) {
                if (Schedule.getSchedule()[i][j].size() > maxRowHeight) {
                    maxRowHeight = Schedule.getSchedule()[i][j].size();
                }
            }
        }
        DefaultTableModel dm = new DefaultTableModel() {
            public Class getColumnClass(int columnIndex) {
                return String.class;
            }
            @Override
            public boolean isCellEditable(int row, int column) {
                //all cells false
                return false;
            }

        };
        dm.setDataVector(data, daysOfTheWeek);

        schedule = new JTable(dm);
        schedule.setDefaultRenderer(String.class, new MultiLineCellRender());
        schedule.setRowHeight(schedule.getRowHeight() * maxRowHeight);
        TableColumn column = null;
        for (int i = 0; i < 5; i++) {
            column = schedule.getColumnModel().getColumn(i);
            column.setPreferredWidth(50);
        }

        schedule.setFont(new Font("Serif", Font.BOLD, 10));
        JScrollPane scroll = new JScrollPane(schedule);

        scroll.setPreferredSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width - 200, Toolkit.getDefaultToolkit().getScreenSize().height - 200));
        mainMenu = new JButton("Menu");
        mainMenu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Open up the main panel
                switchPanel(new GUIMainPanel());
            }
        });
        title = new JLabel("McDonlads Scheduling System", SwingConstants.CENTER);
        title.setFont(title.getFont().deriveFont(52.0f));
        this.add(title, BorderLayout.NORTH);
        this.add(scroll, BorderLayout.CENTER);
        this.add(mainMenu, BorderLayout.SOUTH);
        this.repaint();
    }

    /**
     * switchPanel
     * Calls the method from GUI to switch panels
     *
     * @param p JPanel that is to be swtiched to
     */
    void switchPanel(JPanel p) {
        Scheduler.g.switchPanel(this, p);
    }
}

