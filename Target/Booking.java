import java.time.LocalDate;

import java.util.ArrayList;

public class Booking {
    private String bookingID;
    private Customer customer;
    private Guest guest;
    private MasterRoom room;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private double totalPrice;
    
    public Booking(Customer customer, TransectionRoom room, LocalDate checkInDate, LocalDate checkOutDate) {
        this.bookingID = UUID.randomUUID().toString(); // Automatically generate a unique booking ID
        this.customer = customer;
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.totalPrice = calculateTotalPrice(); // Calculate total price at the time of booking
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

    public TransectionRoom getRoom() {
        return room;
    }

    public void setRoom(TransectionRoom room) {
        this.room = room;
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
        return room.getRoom().getPrice() * numberOfDays;
    }

    public void cancelBooking() {
        System.out.println("Booking " + bookingID + " has been canceled.");
        // Additional cancellation logic, such as refunding or updating system records, can be added here.
    }

    public void bookingInfo() {
        System.out.println("Booking Information:");
        System.out.println("Booking ID: " + bookingID);
        System.out.print("Customer: " + customer.getFirstName());
        System.out.println(" " + customer.getLastName());
        System.out.println("Room: " + room.getRoom().getType());
        System.out.println("Check-in Date: " + checkInDate);
        System.out.println("Check-out Date: " + checkOutDate);
        System.out.println("Total Price: " + totalPrice);
    }
}