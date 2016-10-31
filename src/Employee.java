/*
 Employee.java
 This creates a general employee object

 Created by Bill Li on 2016-10-30.
 */

class Employee {

    // I changed something

    // Setting up variables
    private String fullName;
    private String address;
    private String employeeNumber;
    private boolean[][] hoursAvailable = new boolean[7][24];
    private boolean[][] hoursWorking = new boolean[7][24];
    private int totalHours = 0;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public boolean[][] getHoursAvailable() {
        return hoursAvailable;
    }

    public void setHoursAvailable(boolean[][] hoursAvailable) {
        this.hoursAvailable = hoursAvailable;
    }

    public boolean[][] getHoursWorking() {
        return hoursWorking;
    }

    public void setHoursWorking(boolean[][] hoursWorking) {
        this.hoursWorking = hoursWorking;
    }

    public int getTotalHours() {
        return totalHours;
    }

    public void setTotalHours(int totalHours) {
        this.totalHours = totalHours;
    }
}
