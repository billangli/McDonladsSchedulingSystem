/*
 ListEmployeesPanel.java
 This panel lists the existing employees

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
        for (int i = 0; i < Scheduler.s.getAllEmployees().size(); i++) {
            data[i + 1][0] = i;
            data[i + 1][2] = Scheduler.s.getAllEmployees().get(i).getFullName();
            data[i + 1][3] = Scheduler.s.getAllEmployees().get(i).getAddress();
            if (Scheduler.s.getAllEmployees().get(i) instanceof Manager) {
                data[i + 1][1] = "Manager";
                data[i + 1][4] = "$" + Scheduler.s.getAllEmployees().get(i).getPay() + "/yr";
            } else {
                data[i + 1][1] = "Worker";
                data[i + 1][4] = "$" + Scheduler.s.getAllEmployees().get(i).getPay() + "/hr";
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
                switchPanel(new MainPanel());
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

    void switchPanel(JPanel p) {
        Scheduler.g.switchPanel(this, p);
    }
}
