/*
 ListEmployeesPanel.java
 This panel lists the existing employees on a panel

 Created by Bill Li on 2016-11-07.
 */

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ListEmployeesPanel extends JPanel {
    Object[][] data = new Object[Scheduler.s.getAllEmployees().size() + 1][5];
    private JLabel titleLabel = new JLabel("Employee List at McDonlads");
    private String[] columnNames = {"Employee #", "Type", "Name", "Address", "Salary/Wage"};
    private int[] columnWidth = {100, 80, 150, 150, 100};
    private JTable table;
    private JButton menuButton;

    ListEmployeesPanel() {
        Scheduler.s.organizeEmployees();

        titleLabel.setFont(titleLabel.getFont().deriveFont(52.0f));

        for (int i = 0; i < 5; i++) {
            data[0][i] = columnNames[i];
        }

        int counter = 1;
        for (int i = 0; i < Scheduler.s.getAllEmployees().size(); i++) {
            if (Scheduler.s.getAllEmployees().get(i) instanceof Manager) {
                addManager(counter, i);
                counter++;
            }
        }
        for (int i = 0; i < Scheduler.s.getAllEmployees().size(); i++) {
            if (Scheduler.s.getAllEmployees().get(i) instanceof Worker) {
                addWorker(counter, i);
                counter++;
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

        // Button to go back to menu
        menuButton = new JButton("Menu");
        menuButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Open up the worker editing panel
                switchPanel(new GUIMainPanel());
            }
        });

        table = new JTable(data, columnNames);
        table.setRowHeight(table.getRowHeight() * maxRowHeight);
        for (int i = 0; i < 5; i++) {
            table.getColumnModel().getColumn(i).setPreferredWidth(columnWidth[i]);
        }

        this.add(titleLabel);
        this.add(table);
        this.add(menuButton);
    }

    private void addManager(int counter, int i) {
        data[counter][0] = i;
        data[counter][1] = "Manager";
        data[counter][2] = Scheduler.s.getAllEmployees().get(i).getFullName();
        data[counter][3] = Scheduler.s.getAllEmployees().get(i).getAddress();
        data[counter][4] = "$" + Scheduler.s.getAllEmployees().get(i).getPay() + "/yr";
    }

    private void addWorker(int counter, int i) {
        data[counter][0] = i;
        data[counter][1] = "Worker";
        data[counter][2] = Scheduler.s.getAllEmployees().get(i).getFullName();
        data[counter][3] = Scheduler.s.getAllEmployees().get(i).getAddress();
        data[counter][4] = "$" + Scheduler.s.getAllEmployees().get(i).getPay() + "/hr";
    }


    void switchPanel(JPanel p) {
        Scheduler.g.switchPanel(this, p);
    }
}
