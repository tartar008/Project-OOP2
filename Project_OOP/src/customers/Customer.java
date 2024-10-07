package src.customers;

import src.bookings.*;;

public class Customer extends Person{
    private boolean isBookingAgent;  
    private String bookingId;
    private Confirmation confirmation;
    public Customer (String firstName, String lastName, String phoneNumber, String email){
        super(firstName, lastName, phoneNumber, email);
    }
    public Customer (String firstName, String lastName, String phoneNumber, String email, boolean isBookingAgent){
        super(firstName, lastName, phoneNumber, email);
        this.isBookingAgent = isBookingAgent;
    }
    
    public Customer (String firstName, String lastName, boolean isBookingAgent){
        super(firstName, lastName);
        this.isBookingAgent = isBookingAgent;
    }
    
    public void setAgent(boolean isBookingAgent) {
        this.isBookingAgent = isBookingAgent;
    }
    public boolean getAgent() {
       return isBookingAgent;
    }

    public void setBookingId(String bookingId){
        this.bookingId = bookingId;
    }
    
    public String getBookingId(){
       return bookingId;
    }

    public void setConfirmation(Confirmation confirmation){
        this.confirmation = confirmation;
    }

}
