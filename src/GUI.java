/*
 GUI.java
 @version 1.0
 @author Robbie Zhuang
 @date 2016-11-06
 This creates a the JFrame for all of GUI
 */

// Imports

import javax.swing.*;

class GUI extends JFrame {

    /**
     * Default constructor for making a GUI Frame
     */
    GUI() {
        this.setName("McDonlads Scheduling System");
        this.setTitle("McDonlads Scheduling System");
        this.add(new GUIMainPanel());
        // Maximizes JFrame window
        this.setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    /**
     * switchPanel
     * This method removes the old JPanel and adds a new one in
     *
     * @param old
     * @param next
     */
    public void switchPanel(JPanel old, JPanel next) {
        this.remove(old);
        this.add(next);
        this.setVisible(true);
    }

}
