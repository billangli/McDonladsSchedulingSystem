/*
 GUIManageEmployeePanel.java
 @version 1.0
 @author Bill Li
 @date 7/11/2016
 This is the employee managing panel
 TODO Bill, Comment This Stuff
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class GUIManageEmployeePanel extends JPanel {
    // Creating variables
    private JButton menuButton;
    private JButton addButton;
    private JButton editButton;
    private JButton removeButton;
    private JLabel titleLabel;
    private JPanel optionsPanel = new JPanel();

    /**
     * This is the default constructor for creating a GUIManageEmployeePanel
     */
    GUIManageEmployeePanel() {
        // This is the title label
        this.setLayout(new BorderLayout());
        titleLabel = new JLabel("Manage Employee", SwingConstants.CENTER);
        titleLabel.setFont(titleLabel.getFont().deriveFont(52.0f));

        // This button adds an employee
        addButton = new JButton("Add");
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Switch to the add employee panel
                switchPanel(new AddEmployeePanel());
            }
        });

        // This button edits the employee
        editButton = new JButton("Edit");
        editButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Switch to the remove employee panel
                switchPanel(new EditEmployeePanel());
            }
        });

        // This button brings the user to remove an employee
        removeButton = new JButton("Remove");
        removeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Switch to the remove worker panel
                switchPanel(new RemoveEmployeePanel());
            }
        });

        // Button to go back to menu
        menuButton = new JButton("Menu");
        menuButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Open up the worker editing panel
                switchPanel(new GUIMainPanel());
            }
        });


        // Putting everything together in the panel
        optionsPanel.add(addButton);
        optionsPanel.add(editButton);
        optionsPanel.add(removeButton);
        optionsPanel.add(menuButton);

        this.add(titleLabel, BorderLayout.NORTH);
        this.add(optionsPanel, BorderLayout.CENTER);
    }

    /**
     * switchPanel
     * This switches to another panel
     *
     * @param p is the new panel to switch to
     */
    void switchPanel(JPanel p) {
        Scheduler.g.switchPanel(this, p);
    }

    /**
     * AddEmployeePanel
     * This panel allows the user to edit the employee
     */
    class AddEmployeePanel extends JPanel {
        // Creating variables
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

        // Default constructor for AddEmployeePanel
        AddEmployeePanel() {
            // This is the title
            titleLabel = new JLabel("Edit Employee Information");
            titleLabel.setFont(titleLabel.getFont().deriveFont(52.0f));


            // These are the combo boxes for selecting manager and worker
            typeComboBox.addItem("Manager");
            typeComboBox.addItem("Worker");


            // Button to go back to menu
            menuButton = new JButton("Menu");
            menuButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    // Open up the menu
                    switchPanel(new GUIMainPanel());
                }
            });

            // Adding a button listener to the add employee button
            addEmployeeButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    // Adding the employee
                    if (typeComboBox.getSelectedIndex() == 0) {
                        switchPanel(new EditHoursPanel(Scheduler.s.getAllEmployees().size() - 1));
                        Scheduler.s.addManager(nameTextField.getText(), addressTextField.getText(), payTextField.getText());
                    } else {
                        switchPanel(new EditHoursPanel(Scheduler.s.getAllEmployees().size() - 1));
                        Scheduler.s.addWorker(nameTextField.getText(), addressTextField.getText(), payTextField.getText());
                    }
                }
            });

            // Putting everything together on the panel
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

        /**
         * switchPanel
         * This switches to another panel
         *
         * @param p is the new panel to switch to
         */
        void switchPanel(JPanel p) {
            System.out.println("Switching");
            Scheduler.g.switchPanel(this, p);
        }
    }

    /**
     * EditHoursPanel
     * This panel allows the user to edit employee info
     */
    class EditHoursPanel extends JPanel {
        // Creating variables
        private JLabel titleLabel = new JLabel("Edit Hours Available");
        private JLabel[] dayLabel = new JLabel[8];
        private JLabel[] timeLabel = new JLabel[24];
        private String[] columnNames = {"   Time", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
        private JButton[][] timeButton = new JButton[24][7];
        private JPanel dayPanel = new JPanel();
        private JPanel[] timePanel = new JPanel[24];
        private JPanel buttonPanel = new JPanel(new BorderLayout());
        private JButton menuButton;
        private JButton saveChangesButton;

        // The is the default constructor for the panel
        EditHoursPanel(int employeeNumber) {
            // This is the title
            titleLabel.setFont(titleLabel.getFont().deriveFont(52.0f));


            // This displays the button array for the hours available
            for (int i = 0; i < 8; i++) {
                dayLabel[i] = new JLabel(String.format("%1$17s", columnNames[i]));
            }
            for (int i = 0; i < 24; i++) {
                timePanel[i] = new JPanel(new FlowLayout());
                timeLabel[i] = new JLabel(i + ":00 - " + (i + 1) + ":00");
                timePanel[i].add(timeLabel[i]);

                for (int j = 0; j < 7; j++) {
                    if (Scheduler.s.getAllEmployees().get(employeeNumber).getHoursAvailable()[j][i]) {
                        timeButton[i][j] = new JButton("Available");
                    } else {
                        timeButton[i][j] = new JButton("Not  Free");
                    }
                    timeButton[i][j].setFont(new Font("Mono", Font.PLAIN, 10));
                    int finalI = i;
                    int finalJ = j;
                    timeButton[i][j].addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            if (timeButton[finalI][finalJ].getText().contains("Available")) {
                                // Switching to not working
                                timeButton[finalI][finalJ].setText("Not  Free");
                                timeButton[finalI][finalJ].setFont(new Font("Mono", Font.PLAIN, 10));
                            } else {
                                // Switching to working
                                timeButton[finalI][finalJ].setText("Available");
                                timeButton[finalI][finalJ].setFont(new Font("Mono", Font.PLAIN, 10));
                            }
                        }
                    });
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

            // Putting everything together on the panel
            this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            this.add(titleLabel);
            for (int i = 0; i < 8; i++) {
                dayPanel.add(dayLabel[i]);
            }
            this.add(dayPanel);
            for (int i = 0; i < 24; i++) {
                this.add(timePanel[i]);
            }
            buttonPanel.add(BorderLayout.WEST, menuButton);
            buttonPanel.add(BorderLayout.EAST, saveChangesButton);
            this.add(buttonPanel);
        }

        /**
         * This switches a panel to the new one
         *
         * @param p is the new panel
         */
        void switchPanel(JPanel p) {
            Scheduler.g.switchPanel(this, p);
        }
    }

    /**
     * EditEmployeePanel
     * This panel allows the user to edit employee info
     */
    class EditEmployeePanel extends JPanel {
        // Creating variables
        private JLabel employeeNameLabel = new JLabel("Employee Name");
        private JLabel changeLabel = new JLabel("Change");
        private JPanel titlePanel = new JPanel();
        private JPanel topPanel = new JPanel();
        private JPanel bottomPanel = new JPanel();
        private JComboBox employeeNameComboBox;
        private JComboBox optionsComboBox;
        private JButton editButton = new JButton("Edit");

        // This is the default constructor
        EditEmployeePanel() {
            this.setLayout(new BorderLayout());
            titleLabel = new JLabel("Edit Employee", SwingConstants.CENTER);
            titleLabel.setFont(titleLabel.getFont().deriveFont(52.0f));

            employeeNameComboBox = new JComboBox();
            for (int i = 0; i < Scheduler.s.getAllEmployees().size(); i++) {
                employeeNameComboBox.addItem(Scheduler.s.getAllEmployees().get(i).getFullName());
            }

            optionsComboBox = new JComboBox();
            optionsComboBox.addItem("Employee Information");
            optionsComboBox.addItem("Availability");


            // Button to go back to menu
            menuButton = new JButton("Menu");
            menuButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    // Open up the menu
                    switchPanel(new GUIMainPanel());
                }
            });

            // Adding button listener to the edit button
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


            // Putting everything together in the panel
            titlePanel.add(titleLabel);

            topPanel.add(employeeNameLabel);
            topPanel.add(employeeNameComboBox);
            topPanel.add(changeLabel);
            topPanel.add(optionsComboBox);

            bottomPanel.setLayout(new BorderLayout());
            bottomPanel.add(BorderLayout.WEST, menuButton);
            bottomPanel.add(BorderLayout.EAST, editButton);

            this.add(titleLabel, BorderLayout.NORTH);
            this.add(topPanel, BorderLayout.CENTER);
            this.add(bottomPanel, BorderLayout.SOUTH);
        }

        /**
         * This switches to the new panel p
         *
         * @param p is the new panel
         */
        void switchPanel(JPanel p) {
            Scheduler.g.switchPanel(this, p);
        }

        /**
         * EditInfoPanel
         * This panel allows the user to change an employee's info
         */
        class EditInfoPanel extends JPanel {
            // Creating variables
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
            private JButton menuButton = new JButton("Menu");
            private JButton saveChangesButton = new JButton("Save Changes");

            /**
             * This is the default constructor
             *
             * @param employeeNumber indicates which employee it is
             */
            EditInfoPanel(int employeeNumber) {
                // This is the title of the panel
                titleLabel = new JLabel("Edit Employee Information");
                titleLabel.setFont(titleLabel.getFont().deriveFont(52.0f));


                // Creating textfields
                nameTextField = new JTextField(Scheduler.s.getAllEmployees().get(employeeNumber).getFullName(), 20);
                addressTextField = new JTextField(Scheduler.s.getAllEmployees().get(employeeNumber).getAddress(), 20);
                payTextField = new JTextField(Double.toString(Scheduler.s.getAllEmployees().get(employeeNumber).getPay()), 20);


                // Button to go back to menu
                menuButton = new JButton("Menu");
                menuButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        // Open up the menu
                        switchPanel(new GUIMainPanel());
                    }
                });

                // Adding button listener to the save changes button
                saveChangesButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        // Save changes
                        Scheduler.s.editName(employeeNumber, nameTextField.getText());
                        Scheduler.s.editAddress(employeeNumber, addressTextField.getText());
                        Scheduler.s.editPay(employeeNumber, Double.parseDouble(payTextField.getText()));
                        System.out.println("You're here");
                        switchPanel(new GUIMainPanel());
                    }
                });


                // Putting everything together in the panel
                titlePanel.add(titleLabel);
                namePanel.add(nameLabel);
                namePanel.add(nameTextField);
                addressPanel.add(addressLabel);
                addressPanel.add(addressTextField);
                payPanel.add(payLabel);
                payPanel.add(payTextField);
                buttonPanel.add(BorderLayout.WEST, menuButton);
                buttonPanel.add(BorderLayout.EAST, saveChangesButton);

                this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
                this.add(titlePanel);
                this.add(namePanel);
                this.add(addressPanel);
                this.add(payPanel);
                this.add(buttonPanel);
            }

            /**
             * This switches to the new panel p
             *
             * @param p is the new panel
             */
            void switchPanel(JPanel p) {
                Scheduler.g.switchPanel(this, p);
            }
        }
    }

    /**
     * RemoveEmployeePanel
     * This removes an employee
     */
    class RemoveEmployeePanel extends JPanel {
        // Creating variables
        private JPanel topPanel = new JPanel();
        private JPanel bottomPanel = new JPanel();
        private JLabel titleLabel;
        private JLabel employeeNameLabel;
        private JComboBox employeeNameComboBox;
        private JButton removeButton = new JButton("Remove");

        // This is the default constructor
        RemoveEmployeePanel() {
            // Creating some labels
            titleLabel = new JLabel("Remove Worker");
            titleLabel.setFont(titleLabel.getFont().deriveFont(52.0f));

            employeeNameLabel = new JLabel("Employee Name");
            employeeNameLabel.setFont(titleLabel.getFont().deriveFont(24.0f));


            // Creating the combobox for selecting employee
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


            // Adding button listener to the remove button
            removeButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    // Open up the worker editing panel
                    Scheduler.getS().removeEmployee(employeeNameComboBox.getSelectedIndex());
                    switchPanel(new GUIMainPanel());
                }
            });

            // Putting everything together
            topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
            bottomPanel.setLayout(new BorderLayout());
            this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

            topPanel.add(titleLabel);
            topPanel.add(employeeNameLabel);
            topPanel.add(employeeNameComboBox);

            bottomPanel.add(BorderLayout.WEST, menuButton);
            bottomPanel.add(BorderLayout.EAST, removeButton);

            this.add(topPanel);
            this.add(bottomPanel);
        }

        /**
         * Switching to the new panel p
         *
         * @param p is the new panel
         */
        void switchPanel(JPanel p) {
            Scheduler.g.switchPanel(this, p);
        }
    }
}