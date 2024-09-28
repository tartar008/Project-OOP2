public class Booker extends Customer{
    private String bookingID;
    
    public Booker(String firstName, String lastName, String phoneNumber, String email, String bookingID){
        super(firstName, lastName, phoneNumber, email);
        this.bookingID = bookingID;
    }
    public void setBookingID(String bookingID) {
        this.bookingID = bookingID;
    }
}
