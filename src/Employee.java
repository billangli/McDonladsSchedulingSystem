/*
 Employee.java
 This creates a general employee object

 Created by Bill Li on 2016-10-30.
 */

class Employee {

    // Setting up variables
    private String fullName;
    private String address;
    private String employeeNumber;

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
}
