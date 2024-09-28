public class Employee {
    // Attributes
    private String employeeId;
    private String name;
    private String position;
    private double salary;
    private String shift;
    private String contactInfo;

    // Constructor
    public Employee(String employeeId, String name, String position, double salary, String shift, String contactInfo) {
        this.employeeId = employeeId;
        this.name = name;
        this.position = position;
        this.salary = salary;
        this.shift = shift;
        this.contactInfo = contactInfo;
    }

    // Getter for contactInfo
    public String getContactInfo() {
        return contactInfo;
    }

    // Other methods
    public void checkIn() {
    }

    public void checkOut() {
    }

    public void viewReservation() {
    }

    // Update Contact Info: Only accessible to Manager (use protected or final)
    protected void updateContactInfo(String newContactInfo) {
        this.contactInfo = newContactInfo;
    }
}
