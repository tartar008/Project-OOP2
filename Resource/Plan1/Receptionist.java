public class Receptionist extends Employee {

    public Receptionist(String name, String id, String contactInfo) {
        super(name, id, contactInfo, "Receptionist");
    }

    public void greetCustomer(Customer customer) {
        System.out.println("Greeting customer: " + customer.getName());
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
