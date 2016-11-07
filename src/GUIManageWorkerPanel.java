/*
 

 Created by Bill Li on 2016-11-06.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class GUIManageWorkerPanel extends JPanel {
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

    GUIManageWorkerPanel() {
        titleLabel = new JLabel("                    Manage Worker                    ");
        titleLabel.setFont(titleLabel.getFont().deriveFont(52.0f));
        this.add(titleLabel);


        addButton = new JButton("Add");
        addButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Switch to the add employee panel
                switchPanel(new AddEmployeePanel());
            }
        });

        editButton = new JButton("Edit");
        editButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        editButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Switch to the remove employee panel
                switchPanel(new EditEmployeePanel());
            }
        });

        removeButton = new JButton("Remove");
        removeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        removeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Switch to the remove worker panel
                switchPanel(new RemoveEmployeePanel());
            }
        });

        // Button to go back to menu
        menuButton = new JButton("Menu");
        menuButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        menuButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Open up the worker editing panel
                switchPanel(new GUIMainPanel());
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

    class AddEmployeePanel extends JPanel {
        private JLabel typeLabel = new JLabel("Employee Type");
        private JLabel nameLabel = new JLabel("Name");
        private JLabel addressLabel = new JLabel("Address");
        private JLabel payLabel = new JLabel("Salary/Wage");
        private JComboBox typeComboBox = new JComboBox();
        private JTextField nameTextField = new JTextField(20);
        private JTextField addressTextField = new JTextField(20);
        private JTextField payTextField = new JTextField(20);
        private JPanel titlePanel = new JPanel();
        private JPanel typePanel = new JPanel();
        private JPanel namePanel = new JPanel();
        private JPanel addressPanel = new JPanel();
        private JPanel payPanel = new JPanel();
        private JPanel buttonPanel = new JPanel();
        private JButton addEmployeeButton = new JButton("Add Employee");


        AddEmployeePanel() {
            titleLabel = new JLabel("Edit Employee Information");
            titleLabel.setFont(titleLabel.getFont().deriveFont(52.0f));


            typeComboBox.addItem("Manager");
            typeComboBox.addItem("Worker");


            addEmployeeButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    // Adding the employee
                    if (typeComboBox.getSelectedIndex() == 0) {
                        Scheduler.s.addManager(nameTextField.getText(), addressTextField.getText(), payTextField.getText());
                        switchPanel(new EditHoursPanel(Scheduler.s.getAllEmployees().size() - 1));
                    } else {
                        Scheduler.s.addWorker(nameTextField.getText(), addressTextField.getText(), payTextField.getText());
                        switchPanel(new EditHoursPanel(Scheduler.s.getAllEmployees().size() - 1));
                    }
                }
            });

            titlePanel.add(titleLabel);
            typePanel.add(typeLabel);
            typePanel.add(typeComboBox);
            namePanel.add(nameLabel);
            namePanel.add(nameTextField);
            addressPanel.add(addressLabel);
            addressPanel.add(addressTextField);
            payPanel.add(payLabel);
            payPanel.add(payTextField);
            buttonPanel.add(BorderLayout.WEST, menuButton);
            buttonPanel.add(BorderLayout.EAST, addEmployeeButton);

            this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            this.add(titlePanel);
            this.add(typePanel);
            this.add(namePanel);
            this.add(addressPanel);
            this.add(payPanel);
            this.add(buttonPanel);
        }

        void switchPanel(JPanel p) {
            Scheduler.g.switchPanel(this, p);
        }
    }

    class EditHoursPanel extends JPanel {
        private JLabel titleLabel = new JLabel("Edit Hours Available");
        private JLabel[] dayLabel = new JLabel[8];
        private JLabel[] timeLabel = new JLabel[24];
        private String[] columnNames = {"Time", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
        private JButton[][] timeButton = new JButton[24][7];
        private JPanel dayPanel = new JPanel();
        private JPanel[] timePanel = new JPanel[24];
        private JButton menuButton;
        private JButton saveChangesButton;

        EditHoursPanel(int employeeNumber) {
            titleLabel.setFont(titleLabel.getFont().deriveFont(52.0f));

            this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

            for (int i = 0; i < 8; i++) {
                dayLabel[i] = new JLabel(columnNames[i]);
            }
            for (int i = 0; i < 24; i++) {
                timePanel[i] = new JPanel(new FlowLayout());
                timeLabel[i] = new JLabel(i + ":00 - " + (i + 1) + ":00");
                timePanel[i].add(timeLabel[i]);

                for (int j = 0; j < 7; j++) {
                    if (Scheduler.s.getAllEmployees().get(employeeNumber).getHoursAvailable()[j][i]) {
                        timeButton[i][j] = new JButton("Available");
                        timeButton[i][j].setFont(new Font("Mono", Font.PLAIN, 10));
                        int finalI = i;
                        int finalJ = j;
                        timeButton[i][j].addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                // Switching to not working
                                timeButton[finalI][finalJ].setText("   Off   ");
                                timeButton[finalI][finalJ].setFont(new Font("Mono", Font.PLAIN, 10));
                            }
                        });
                    } else {
                        timeButton[i][j] = new JButton("   Off   ");
                        timeButton[i][j].setFont(new Font("Mono", Font.PLAIN, 10));
                        int finalI = i;
                        int finalJ1 = j;
                        timeButton[i][j].addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                // Switching to working
                                timeButton[finalI][finalJ1].setText("Available");
                                timeButton[finalI][finalJ1].setFont(new Font("Mono", Font.PLAIN, 10));
                            }
                        });
                    }
                    timePanel[i].add(timeButton[i][j]);
                }
            }


            // Button to go back to menu
            menuButton = new JButton("Menu");
            menuButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    // Open up the menu
                    switchPanel(new GUIMainPanel());
                }
            });

            // Button to save changes
            saveChangesButton = new JButton("Save Changes");
            saveChangesButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    // Go back to menu and save changes
                    for (int i = 0; i < 24; i++) {
                        for (int j = 0; j < 7; j++) {
                            if (timeButton[i][j].getText().contains("Available")) {
                                Scheduler.s.getAllEmployees().get(employeeNumber).setHoursAvailable(j, i, true);
                            }
                        }
                    }
                    Scheduler.s.updateEmployeeFile();
                    switchPanel(new GUIMainPanel());
                }
            });

            System.out.println("ok");

            this.add(titleLabel);
            for (int i = 0; i < 7; i++) {
                dayPanel.add(dayLabel[i]);
            }
            this.add(dayPanel);
            for (int i = 0; i < 24; i++) {
                this.add(timePanel[i]);
            }
            this.add(menuButton);
        }
    }

    class EditEmployeePanel extends JPanel {
        private JLabel employeeNameLabel = new JLabel("Employee Name");
        private JLabel changeLabel = new JLabel("Change");
        private JPanel topPanel = new JPanel();
        private JPanel bottomPanel = new JPanel();
        private JComboBox employeeNameComboBox;
        private JComboBox optionsComboBox;
        private JButton editButton = new JButton("Edit");

        EditEmployeePanel() {
            this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            titleLabel = new JLabel("Edit Worker");
            titleLabel.setFont(titleLabel.getFont().deriveFont(52.0f));

            employeeNameComboBox = new JComboBox();
            for (int i = 0; i < Scheduler.s.getAllEmployees().size(); i++) {
                employeeNameComboBox.addItem(Scheduler.s.getAllEmployees().get(i).getFullName());
            }

            optionsComboBox = new JComboBox();
            optionsComboBox.addItem("Employee Information");
            optionsComboBox.addItem("Availability");


            editButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    // Open up the edit information panel
                    if (optionsComboBox.getSelectedIndex() == 0) {
                        switchPanel(new EditInfoPanel(employeeNameComboBox.getSelectedIndex()));
                    } else {
                        switchPanel(new EditHoursPanel(employeeNameComboBox.getSelectedIndex()));
                    }
                }
            });


            topPanel.add(titleLabel);
            topPanel.add(employeeNameLabel);
            topPanel.add(employeeNameComboBox);
            topPanel.add(changeLabel);
            topPanel.add(optionsComboBox);

            bottomPanel.setLayout(new BorderLayout());
            bottomPanel.add(BorderLayout.WEST, menuButton);
            bottomPanel.add(BorderLayout.EAST, editButton);

            this.add(topPanel);
            this.add(bottomPanel);
        }

        void switchPanel(JPanel p) {
            Scheduler.g.switchPanel(this, p);
        }

        class EditInfoPanel extends JPanel {
            private JLabel nameLabel = new JLabel("Name");
            private JLabel addressLabel = new JLabel("Address");
            private JLabel payLabel = new JLabel("Salary/Wage");
            private JTextField nameTextField;
            private JTextField addressTextField;
            private JTextField payTextField;
            private JPanel titlePanel = new JPanel();
            private JPanel namePanel = new JPanel();
            private JPanel addressPanel = new JPanel();
            private JPanel payPanel = new JPanel();
            private JPanel buttonPanel = new JPanel();
            private JButton saveChangesButton = new JButton("Save Changes");

            EditInfoPanel(int employeeNumber) {
                titleLabel = new JLabel("Edit Employee Information");
                titleLabel.setFont(titleLabel.getFont().deriveFont(52.0f));

                nameTextField = new JTextField(Scheduler.s.getAllEmployees().get(employeeNumber).getFullName(), 20);
                addressTextField = new JTextField(Scheduler.s.getAllEmployees().get(employeeNumber).getAddress(), 20);
                payTextField = new JTextField(Double.toString(Scheduler.s.getAllEmployees().get(employeeNumber).getPay()), 20);

                saveChangesButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        // Save changes
                        Scheduler.s.editName(employeeNumber, nameTextField.getText());
                        Scheduler.s.editAddress(employeeNumber, addressTextField.getText());
                        Scheduler.s.editPay(employeeNumber, Double.parseDouble(payTextField.getText()));
                        switchPanel(new EditHoursPanel(employeeNumber));
                        // TODO fix the bug where the bottom panel disappear
                    }
                });

                titlePanel.add(titleLabel);
                namePanel.add(nameLabel);
                namePanel.add(nameTextField);
                addressPanel.add(addressLabel);
                addressPanel.add(addressTextField);
                payPanel.add(payLabel);
                payPanel.add(payTextField);
                buttonPanel.add(BorderLayout.WEST, menuButton);
                buttonPanel.add(BorderLayout.EAST, editButton);

                this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
                this.add(titlePanel);
                this.add(namePanel);
                this.add(addressPanel);
                this.add(payPanel);
                this.add(buttonPanel);
            }

            void switchPanel(JPanel p) {
                Scheduler.g.switchPanel(this, p);
            }
        }
    }

    class RemoveEmployeePanel extends JPanel {
        private JPanel topPanel = new JPanel();
        private JPanel bottomPanel = new JPanel();
        private JLabel titleLabel;
        private JLabel employeeNameLabel;
        private JComboBox employeeNameComboBox;
        private JButton removeButton = new JButton("Remove");

        RemoveEmployeePanel() {
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
                    switchPanel(new GUIMainPanel());
                }
            });


            removeButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    // Open up the worker editing panel
                    Scheduler.getS().removeEmployee(employeeNameComboBox.getSelectedIndex());
                    switchPanel(new GUIMainPanel());
                }
            });

            topPanel.add(titleLabel);
            topPanel.add(employeeNameLabel);
            topPanel.add(employeeNameComboBox);

            bottomPanel.add(BorderLayout.WEST, menuButton);
            bottomPanel.add(BorderLayout.EAST, removeButton);

            this.add(topPanel);
            this.add(bottomPanel);
        }

        void switchPanel(JPanel p) {
            Scheduler.g.switchPanel(this, p);
        }
    }
}