package src.bookings;

//สำหรับสร้างใบยืนยันการจอง
public class Confirmation {
    public Booking booking;

    public Confirmation(){

    }
    public Booking getBooking(){
        return booking;
    }

    public void setBooking(Booking booking){
        this.booking = booking;
    }

    public void confirmInfo(){
        System.out.println("Revervation Details");
        System.out.println("Confirmation Number: " + booking.getBookingID());
    }
}
