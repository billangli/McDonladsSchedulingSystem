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

    ManageWorkerPanel() {
        titleLabel = new JLabel("                    Manage Worker                    ");
        titleLabel.setFont(titleLabel.getFont().deriveFont(52.0f));
        this.add(titleLabel);


        addButton = new JButton("Add");
        addButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Switch to the add worker panel
                switchPanel(new addWorkerPanel());
            }
        });

        editButton = new JButton("Edit");
        editButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        editButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Switch to the remove worker panel
                switchPanel(new editWorkerPanel());
            }
        });

        removeButton = new JButton("Remove");
        removeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        removeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Switch to the remove worker panel
                switchPanel(new removeWorkerPanel());
            }
        });

        // Button to go back to menu
        menuButton = new JButton("Menu");
        menuButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        menuButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Open up the worker editing panel
                switchPanel(new MainPanel());
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
        private JLabel employeeTypeLabel = new JLabel("Employee Type");
        private JLabel employeeNameLabel = new JLabel("Employee Name");
        private JLabel employeeAddressLabel = new JLabel("Employee Address");
        private JLabel employeePayLabel = new JLabel("Employee Pay");
        private JLabel employeeDayLabel = new JLabel("Day Working");
        private JLabel employeeInHoursLabel = new JLabel("Hour In");
        private JLabel employeeOutHoursLabel = new JLabel("Hour Out");
        private JComboBox employeeTypeComboBox = new JComboBox();
        private JTextField employeeNameTextField = new JTextField(20);
        private JTextField employeeAddressTextField = new JTextField(20);
        private JTextField employeePayTextField = new JTextField(20);
        private JTextField employeeDayTextField = new JTextField(20);
        private JTextField employeeInHoursTextField = new JTextField(20);
        private JTextField employeeOutHoursTextField = new JTextField(20);
        private JPanel employeeTypePanel = new JPanel(new BorderLayout());
        private JPanel employeeNamePanel = new JPanel(new BorderLayout());
        private JPanel employeeAddressPanel = new JPanel(new BorderLayout());
        private JPanel employeePayPanel = new JPanel(new BorderLayout());
        private JPanel employeeDayPanel = new JPanel(new BorderLayout());
        private JPanel employeeInHoursPanel = new JPanel(new BorderLayout());
        private JPanel employeeOutHoursPanel = new JPanel(new BorderLayout());
        private JPanel buttonsPanel = new JPanel(new BorderLayout());

        addWorkerPanel() {
            this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            titleLabel = new JLabel("Add Worker");
            titleLabel.setFont(titleLabel.getFont().deriveFont(52.0f));
            this.add(titleLabel);

            employeeTypeComboBox.addItem("Manager");
            employeeTypeComboBox.addItem("Worker");
            employeeTypePanel.add(BorderLayout.WEST, employeeTypeLabel);
            employeeTypePanel.add(BorderLayout.EAST, employeeTypeComboBox);

            employeeNamePanel.add(BorderLayout.WEST, employeeNameLabel);
            employeeNamePanel.add(BorderLayout.EAST, employeeNameTextField);

            employeeAddressPanel.add(BorderLayout.WEST, employeeAddressLabel);
            employeeAddressPanel.add(BorderLayout.EAST, employeeAddressTextField);

            employeePayPanel.add(BorderLayout.WEST, employeePayLabel);
            employeePayPanel.add(BorderLayout.EAST, employeePayTextField);

            employeeDayPanel.add(BorderLayout.WEST, employeeDayLabel);
            employeeDayPanel.add(BorderLayout.EAST, employeeDayTextField);

            employeeInHoursPanel.add(BorderLayout.WEST, employeeInHoursLabel);
            employeeInHoursPanel.add(BorderLayout.EAST, employeeInHoursTextField);

            employeeOutHoursPanel.add(BorderLayout.WEST, employeeOutHoursLabel);
            employeeOutHoursPanel.add(BorderLayout.EAST, employeeOutHoursTextField);

            // Button to go back to menu
            menuButton = new JButton("Menu");
            menuButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    // Open up the worker editing panel
                    switchPanel(new MainPanel());
                }
            });

            addButton = new JButton("Add");
            addButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    // Open up the worker editing panel
                    if (employeeTypeComboBox.getSelectedIndex() == 0) {
                        Scheduler.s.addManager(employeeNameTextField.getText(),
                                employeeAddressTextField.getText(),
                                employeePayTextField.getText(),
                                employeeDayTextField.getText(),
                                employeeInHoursTextField.getText(),
                                employeeOutHoursTextField.getText());
                        switchPanel(new MainPanel());
                    } else {
                        Scheduler.s.addWorker(employeeNameTextField.getText(),
                                employeeAddressTextField.getText(),
                                employeePayTextField.getText(),
                                employeeDayTextField.getText(),
                                employeeInHoursTextField.getText(),
                                employeeOutHoursTextField.getText());
                        switchPanel(new MainPanel());
                    }
                }
            });

            buttonsPanel.add(BorderLayout.WEST, menuButton);
            buttonsPanel.add(BorderLayout.EAST, addButton);

            // Adding all panels together
            this.add(employeeTypePanel);
            this.add(employeeNamePanel);
            this.add(employeeAddressPanel);
            this.add(employeePayPanel);
            this.add(employeeDayPanel);
            this.add(employeeInHoursPanel);
            this.add(employeeOutHoursPanel);
            this.add(buttonsPanel);
        }

        void switchPanel(JPanel p) {
            Scheduler.g.switchPanel(this, p);
        }
    }

    class editWorkerPanel extends JPanel {
        private JLabel titleLabel;

        editWorkerPanel() {
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

        removeWorkerPanel() {
            this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
            bottomPanel.setLayout(new BorderLayout());

            titleLabel = new JLabel("Remove Worker");
            titleLabel.setFont(titleLabel.getFont().deriveFont(52.0f));

            employeeNameLabel = new JLabel("Employee Name");
            employeeNameLabel.setFont(titleLabel.getFont().deriveFont(24.0f));

            employeeNameComboBox = new JComboBox();
            for (int i = 0; i < Scheduler.s.getAllEmployees().size(); i++) {
                employeeNameComboBox.addItem(Scheduler.s.getAllEmployees().get(i).getFullName());
            }

            // Button to go back to menu
            menuButton = new JButton("Menu");
            menuButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    // Open up the worker editing panel
                    switchPanel(new MainPanel());
                }
            });

            nextButton = new JButton("Next");
            nextButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    // Open up the worker editing panel
                    Scheduler.s.removeEmployee(employeeNameComboBox.getSelectedIndex());
                    switchPanel(new MainPanel());
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