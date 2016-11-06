/*
 Scheduler.java
 This is the main program

 Created by Bill Li on 2016-10-30.
 */

import java.io.FileNotFoundException;
import java.util.Scanner;

public class Scheduler {

    // Creating static variables
    static GUI g;
    static Schedule s;

    public static void main(String[] args) throws FileNotFoundException {

        // Setting up variables
        Scanner input = new Scanner(System.in);
        String response = "0";

        // Selecting options
        System.out.println("Welcome to the McDonlads Scheduling System\n");

        // Initializing program
        s = new Schedule();

        // Main program
        g = new GUI(s);
    }

    public static GUI getGUI() {
        return g;
    }

}
