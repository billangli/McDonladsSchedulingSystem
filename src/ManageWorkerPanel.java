/*
 

 Created by Bill Li on 2016-11-06.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ManageWorkerPanel extends JPanel {
    private JButton menuButton;
    private JButton addButton;
    private JButton editButton;
    private JButton removeButton;
    private JLabel titleLabel;
    private JComboBox selectEmployee;
    private String employeeType;
    private String employeeName;
    private String employeeAddress;
    private String employeePay;
    private String day;
    private String inHour;
    private String outHour;

    ManageWorkerPanel(Schedule s) {
        this.setLayout(new FlowLayout());
        titleLabel = new JLabel("Manage Worker");
        titleLabel.setFont(titleLabel.getFont().deriveFont(52.0f));
        this.add(titleLabel);


        addButton = new JButton("Add");
        addButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Switch to the add worker panel
                switchPanel(new addWorkerPanel(s));
            }
        });

        editButton = new JButton("Edit");
        editButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        editButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Switch to the remove worker panel
                switchPanel(new editWorkerPanel(s));
            }
        });

        removeButton = new JButton("Remove");
        removeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        removeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Switch to the remove worker panel
                switchPanel(new removeWorkerPanel(s));
            }
        });

        // Button to go back to menu
        menuButton = new JButton("Menu");
        menuButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        menuButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Open up the worker editing panel
                switchPanel(new MainPanel(s));
            }
        });

        this.add(addButton);
        this.add(editButton);
        this.add(removeButton);
        this.add(menuButton);
    }

    void switchPanel(JPanel p) {
        Scheduler.g.switchPanel(this, p);
    }

    class addWorkerPanel extends JPanel {
        private JLabel titleLabel;

        addWorkerPanel(Schedule s) {
            this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            titleLabel = new JLabel("Add Worker");
            titleLabel.setFont(titleLabel.getFont().deriveFont(52.0f));
            this.add(titleLabel);
        }
    }

    class editWorkerPanel extends JPanel {
        private JLabel titleLabel;

        editWorkerPanel(Schedule s) {
            this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            titleLabel = new JLabel("Edit Worker");
            titleLabel.setFont(titleLabel.getFont().deriveFont(52.0f));
            this.add(titleLabel);
        }
    }

    class removeWorkerPanel extends JPanel {
        private JPanel topPanel = new JPanel();
        private JPanel bottomPanel = new JPanel();
        private JLabel titleLabel;
        private JLabel employeeNameLabel;
        private JComboBox employeeNameComboBox;
        private JButton nextButton;

        removeWorkerPanel(Schedule s) {
            this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
            bottomPanel.setLayout(new BorderLayout());

            titleLabel = new JLabel("Remove Worker");
            titleLabel.setFont(titleLabel.getFont().deriveFont(52.0f));

            employeeNameLabel = new JLabel("Employee Name");
            employeeNameLabel.setFont(titleLabel.getFont().deriveFont(24.0f));

            employeeNameComboBox = new JComboBox();
            for (int i = 0; i < s.getOnlyWorkers().size(); i++) {
                employeeNameComboBox.addItem(s.getOnlyWorkers().get(i).getFullName());
            }

            // Button to go back to menu
            menuButton = new JButton("Menu");
            menuButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    // Open up the worker editing panel
                    switchPanel(new MainPanel(s));
                }
            });

            nextButton = new JButton("Next");
            nextButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    // Open up the worker editing panel
                    s.removeEmployee(employeeNameComboBox.getSelectedIndex());
                }
            });

            topPanel.add(titleLabel);
            topPanel.add(employeeNameLabel);
            topPanel.add(employeeNameComboBox);

            bottomPanel.add(BorderLayout.WEST, menuButton);
            bottomPanel.add(BorderLayout.EAST, nextButton);

            this.add(topPanel);
            this.add(bottomPanel);
        }

        void switchPanel(JPanel p) {
            Scheduler.g.switchPanel(this, p);
        }
    }
}