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


    public Customer (String firstName, String lastName, String phoneNumber, String email){
        super(firstName, lastName, phoneNumber, email);
    }
    

}
