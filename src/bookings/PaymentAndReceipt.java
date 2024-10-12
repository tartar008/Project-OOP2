package src.bookings;

import java.time.LocalDate;
import src.rooms.TransectionRoom;

public class PaymentAndReceipt {

    // Constructor
    public PaymentAndReceipt() {
    }

    // Method to process payment
    public boolean processPayment(Booking booking, double amount, String paymentMethod) {
        // ตรวจสอบว่า amount ถูกต้องหรือไม่
        if (amount <= 0) {
            System.out.println("Invalid payment amount. Payment failed.");
            return false;  // ชำระเงินไม่สำเร็จ
        }

        LocalDate date = LocalDate.now();  // วันที่ปัจจุบัน

        System.out.println("Processing payment of " + amount + " using " + paymentMethod);

        // พิมพ์ใบเสร็จ
        printFormattedReceipt(booking, amount, paymentMethod, date);
        return true; // คืนค่า true ถ้าการชำระเงินสำเร็จ
    }

    public void printFormattedReceipt(Booking booking, double amount, String paymentMethod, LocalDate date) {
        System.out.println("====================================");
        System.out.println("            Receipt                 ");
        System.out.println("====================================");
        System.out.println("Customer: " + booking.getAgent().getFirstName() + " " + booking.getAgent().getLastName());
        System.out.println("Phone: " + booking.getAgent().getPhoneNumber());
        System.out.println("Email: " + booking.getAgent().getEmail());
        System.out.println("Check-in Date: " + booking.getCheckInDate());
        System.out.println("Check-out Date: " + booking.getCheckOutDate());
        System.out.println("Rooms booked:");
    
        // ดึงข้อมูลห้องที่จองจาก Booking
        for (TransectionRoom room : booking.getRooms()) {
            System.out.println(" - Room #" + room.getRoom().getRoomNumber() + " (" + room.getRoom().getType() + "), Price per night: " + room.getRoom().getPrice());
        }
    
        System.out.println("Total amount: " + amount + " THB");
        System.out.println("Payment Method: " + paymentMethod);
        System.out.println("Payment Date: " + date);
        System.out.println("====================================");
    }
    
}