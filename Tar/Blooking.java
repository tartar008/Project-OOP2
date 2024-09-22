import java.time.LocalDate;

public class Blooking {
    private String bookingID;
    private Customer customer;
    private MasterRoom room;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private double totalPrice;

    public class Booking {
        private String bookingID;
        private Customer customer;
        private MasterRoom room;
        private LocalDate checkInDate;
        private LocalDate checkOutDate;
        private double totalPrice;
    
        public Booking(Customer customer, MasterRoom room, LocalDate checkInDate, LocalDate checkOutDate) {
            this.customer = customer;
            this.room = room;
            this.checkInDate = checkInDate;
            this.checkOutDate = checkOutDate;
        }
    
        public String getBookingID() {
            return bookingID;
        }
    
        public void setBookingID(String bookingID) {
            this.bookingID = bookingID;
        }
    
        public Customer getCustomer() {
            return customer;
        }
    
        public void setCustomer(Customer customer) {
            this.customer = customer;
        }
    
        public MasterRoom getRoom() {
            return room;
        }
    
        public void setRoom(MasterRoom room) {
            this.room = room;
        }
    
        public LocalDate getCheckInDate() {
            return checkInDate;
        }
    
        public void setCheckInDate(LocalDate checkInDate) {
            this.checkInDate = checkInDate;
        }
    
        public LocalDate getCheckOutDate() {
            return checkOutDate;
        }
    
        public void setCheckOutDate(LocalDate checkOutDate) {
            this.checkOutDate = checkOutDate;
        }
    
        public double getTotalPrice() {
            return totalPrice;
        }
    
        public void setTotalPrice(double totalPrice) {
            this.totalPrice = totalPrice;
        }
    
        public void cancelBooking() {
            
        }
    
        public void bookingInfo() {
            
        }
    
    
    
    }
    
}
