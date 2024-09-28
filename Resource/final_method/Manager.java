
public class Manager extends Employee {
    public Manager(String employeeId, String name, double salary, String shift, String contactInfo) {
        super(employeeId, name, "Manager", salary, shift, contactInfo);
    }

    // Manager can call updateContactInfo()
    public void updateInfo(String newContactInfo) {
        super.updateContactInfo(newContactInfo);
    }
}