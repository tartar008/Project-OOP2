package src.bookings;

public class ReceiptBooking {
    private Booking booking;

    public ReceiptBooking(Booking booking){
        this.booking = booking;
    }

    public Booking getBooking(){
        return booking;
    }
}
