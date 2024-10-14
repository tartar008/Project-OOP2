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
        String line = "=".repeat(50); // สร้างเส้นแบ่งด้วย '='
    
        System.out.println(line);
        System.out.println("                     Receipt                     ");
        System.out.println(line);
        
        // แสดงข้อมูลลูกค้า
        System.out.printf("Customer             : %s %s%n", booking.getAgent().getFirstName(), booking.getAgent().getLastName());
        System.out.printf("Phone                : %s%n", booking.getAgent().getPhoneNumber());
        System.out.printf("Email                : %s%n", booking.getAgent().getEmail());
        System.out.printf("Check-in Date        : %s%n", booking.getCheckInDate());
        System.out.printf("Check-out Date       : %s%n", booking.getCheckOutDate());
        System.out.println("Rooms booked:");
    
        // ดึงข้อมูลห้องที่จองจาก Booking
        for (TransectionRoom room : booking.getRooms()) {
            System.out.printf(" - Room #%d (%s), Price per night: %.2f THB%n", 
                room.getRoom().getRoomNumber(), 
                room.getRoom().getType(), 
                room.getRoom().getPrice());
        }
    
        System.out.println(line); // เส้นแบ่งก่อนแสดงยอดรวม
        System.out.printf("Total amount: %.2f THB%n", amount);
        System.out.printf("Payment Method: %s%n", paymentMethod);
        System.out.printf("Payment Date: %s%n", date);
        System.out.println(line);
    }
    
    
    
}