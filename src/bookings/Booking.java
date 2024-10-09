package src.bookings;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import src.rooms.*;
import src.customers.*;

public class Booking {
    private static ArrayList<Booking> bookings = new ArrayList<>();
    private String bookingID;
    private Customer agent;
    private List<Customer> guests;
    private List<TransectionRoom> rooms;
    private int amountRoom;
    private int roomType;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private double totalPrice;
    private boolean isConfirmed;

    public Booking(Customer agent, List<Customer> guests, List<TransectionRoom> rooms, int amountRoom, int roomType,
            LocalDate checkInDate, LocalDate checkOutDate) {
        this.bookingID = generateBookingID();
        this.agent = agent;
        this.guests = guests;
        this.rooms = rooms;
        this.amountRoom = amountRoom;
        this.roomType = roomType;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.totalPrice = calculateTotalPrice();
        this.isConfirmed = false;
        addBooking(this); // เพิ่มการจองนี้ลงในลิสต์การจอง
    }

    private String generateBookingID() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        return "BKG-" + LocalDateTime.now().format(formatter);
    }

    // =============== start set/get methods
    public String getBookingID() {
        return bookingID;
    }

    public Customer getAgent() {
        return agent;
    }

    public void setCustomer(Customer agent) {
        this.agent = agent;
    }

    public void setGuest(List<Customer> guests) {
        this.guests = guests;
    }

    public List<Customer> getGuest() {
        return guests;
    }

    public List<TransectionRoom> getRooms() {
        return rooms;
    }

    public void setRooms(List<TransectionRoom> rooms) {
        this.rooms = rooms;
    }

    public int getAmountRoom() {
        return amountRoom;
    }

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(LocalDate checkInDate) {
        this.checkInDate = checkInDate;
        this.totalPrice = calculateTotalPrice();
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(LocalDate checkOutDate) {
        this.checkOutDate = checkOutDate;
        this.totalPrice = calculateTotalPrice();
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setIsConfirmed(boolean isConfirmed) {
        this.isConfirmed = isConfirmed;
    }

    public boolean getIsConfirmed() {
        return isConfirmed;
    }

    // =============== end set/get methods
    public static void addBooking(Booking booking) {
        bookings.add(booking);
    }

    private double calculateTotalPrice() {
        long numberOfDays = ChronoUnit.DAYS.between(checkInDate, checkOutDate);
        double totalPrice = 0;
        for (TransectionRoom runRooms : rooms) {
            totalPrice += runRooms.getRoom().getPrice() * numberOfDays;
        }

        return totalPrice;
    }

    public void confirmBooking() {
        isConfirmed = true;
        System.out.println("Booking Id Status: " + bookingID + " confirmed");
    }

    public void cancelBooking() {
        System.out.println("Booking Id Status: " + bookingID + " has been canceled.");
        isConfirmed = false;
    }

    public void bookingList() {
        for (Booking b : bookings) {
            // แสดงหัวข้อ
            System.out.println("==================================================");
            System.out.println("|                 Booking Status                 |");
            System.out.println("==================================================");
            System.out.printf("| Booking Id Status: %s confirmed |\n", b.getBookingID());
            System.out.printf("| Start Date:          %s                       |\n", b.getCheckInDate());
            System.out.printf("| End Date:            %s                       |\n", b.getCheckOutDate());
            System.out.printf("| Available rooms after booking: %d             |\n", 10); // เปลี่ยนเป็นจำนวนห้องที่ว่างจริง
            System.out.println("==================================================");
            System.out.println("|                  Booking Information            |");
            System.out.println("==================================================");
            System.out.printf("| Booking ID:        %s                         |\n", b.getBookingID());
            System.out.printf("| Customer:          %s %s                     |\n",
                    b.getAgent().getFirstName(), b.getAgent().getLastName());
            System.out.printf("| Room Type:         %s %d                      |\n",
                    b.getRooms().get(roomType).getRoom().getType(), b.getAmountRoom());
            System.out.printf("| Check-in Date:     %s                         |\n", b.getCheckInDate());
            System.out.printf("| Check-out Date:    %s                         |\n", b.getCheckOutDate());
            System.out.printf("| Total Price:       %.2f                       |\n", b.getTotalPrice());
            System.out.println("==================================================");
        }
    }

}