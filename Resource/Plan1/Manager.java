public class Manager extends Employee {

    public Manager(String name, String id, String contactInfo) {
        super(name, id, contactInfo, "Manager");
    }

    public void manageEmployee(Employee employee) {
        System.out.println("Managing employee: " + employee.getName());
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
