import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class Booking {
    private String bookingID;
    private Customer customer;
    private Guest guest;
    private Booker booker;
    private List<TransectionRoom> rooms;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private double totalPrice;
    
    public Booking(Guest guest, List<TransectionRoom> rooms, LocalDate checkInDate, LocalDate checkOutDate) {
        this.bookingID = generateBookingID(); // Generate booking ID ตามปีเดือนวันเวลา
        this.guest = guest;
        this.rooms = rooms;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.totalPrice = calculateTotalPrice(); // Calculate total price at the time of booking
    }
    
    // with booker
    public Booking(Booker booker, List<TransectionRoom> rooms, LocalDate checkInDate, LocalDate checkOutDate) {
        this.bookingID = generateBookingID(); // Generate booking ID ตามปีเดือนวันเวลา
        this.booker = booker;
        this.rooms = rooms;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.totalPrice = calculateTotalPrice(); // Calculate total price at the time of booking
    }

    private String generateBookingID() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        return "BKG-" + LocalDateTime.now().format(formatter); // Add BKG- prefix
    }

    public String getBookingID() {
        return bookingID;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<TransectionRoom> getRooms() {
        return rooms;
    }
    
    public void setRooms(List<TransectionRoom> rooms) {
        this.rooms = rooms;
    }

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(LocalDate checkInDate) {
        this.checkInDate = checkInDate;
        this.totalPrice = calculateTotalPrice(); // Recalculate total price if dates change
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(LocalDate checkOutDate) {
        this.checkOutDate = checkOutDate;
        this.totalPrice = calculateTotalPrice(); // Recalculate total price if dates change
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    // Calculate the total price based on room rate and the number of days
    private double calculateTotalPrice() {
        long numberOfDays = ChronoUnit.DAYS.between(checkInDate, checkOutDate);
        double totalPrice = 0;
        for(TransectionRoom runRooms : rooms){
            totalPrice += runRooms.getRoom().getPrice() * numberOfDays;
        }
        // System.out.println("rooms(0): " + rooms.get(0).getRoom().getPrice());
        return totalPrice;
    }

    public void cancelBooking() {
        System.out.println("Booking " + bookingID + " has been canceled.");
        // Additional cancellation logic, such as refunding or updating system records, can be added here.
    }

    public void noBookerBookingInfo() {
        System.out.println("Booking Information:");
        System.out.println("Booking ID: " + bookingID);
        System.out.print("Customer: " + guest.getFirstName());
        System.out.println(" " + guest.getLastName());
        System.out.println("Room: " + rooms.get(0).getRoom().getType());
        System.out.println("Check-in Date: " + checkInDate);
        System.out.println("Check-out Date: " + checkOutDate);
        System.out.println("Total Price: " + totalPrice);
    }

    public void bookerBookingInfo() {
        System.out.println("Booking Information:");
        System.out.println("Booking ID: " + bookingID);
        System.out.print("Customer: " + booker.getFirstName());
        System.out.println(" " + booker.getLastName());
        System.out.println("Room: " + rooms.get(0).getRoom().getType());
        System.out.println("Check-in Date: " + checkInDate);
        System.out.println("Check-out Date: " + checkOutDate);
        System.out.println("Total Price: " + totalPrice);
    }
}