package src.customers;


public class Customer extends Person{
    private String customerID;
   
    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public Customer(){
        
    }

    public void setLastName(String lastName) {
        this.lastName = lastName; // ปรับค่าตัวแปร lastName
    }


    public Customer (String firstName, String lastName, String phoneNumber, String email){
        super(firstName, lastName, phoneNumber, email);
    }

    public Customer (String firstName, String lastName){
        super(firstName, lastName);
    }

    

}