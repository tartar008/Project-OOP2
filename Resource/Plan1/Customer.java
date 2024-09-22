public class Customer extends Person {
    private String customerType;

    public Customer(String name, String id, String contactInfo, String customerType) {
        super(name, id, contactInfo);
        this.customerType = customerType;
    }

    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    @Override
    public String toString() {
        return super.toString() + ", Customer Type: " + customerType;
    }
}
