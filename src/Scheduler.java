/*
 Scheduler.java
 McDonalds Scheduling System
 This is the main program, it creates a new schedule object and initialites the GUI
 @author Bill Li, Robbie Zhuang
 @date 2016-11-06
 @version 1.0
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
        g = new GUI();
    }
}
