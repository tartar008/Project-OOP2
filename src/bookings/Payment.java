package src.bookings;

import java.time.LocalDate;
import src.rooms.TransectionRoom;

public class Payment {
    private String cardNumber;
    private String expirationDate;
    private String cvv;
    private String promptPayPhoneNumber;

    // Constructor
    public Payment() {
    }

    // Method to process payment
    public boolean processPayment(Booking booking, double amount, String paymentMethod) {
        // ตรวจสอบว่า amount ถูกต้องหรือไม่
        if (amount <= 0) {
            System.out.println("Invalid payment amount! Payment failed.");
            return false; // ชำระเงินไม่สำเร็จ
        }

        // ตรวจสอบว่า amount มากกว่าหรือเท่ากับราคาที่ต้องจ่าย
        if (amount < booking.getTotalPrice()) {
            System.out.println("Payment amount is less than the total price. Payment failed.");
            return false; // ชำระเงินไม่สำเร็จ
        }

        LocalDate date = LocalDate.now(); // วันที่ปัจจุบัน

        System.out.println("Processing payment of " + amount + " using " + paymentMethod);

        // ตรวจสอบวิธีการชำระเงินและทำการชำระเงิน
        if (paymentMethod.equals("Credit Card")) {
            // เพิ่มการตรวจสอบบัตรเครดิตก่อนประมวลผล (อาจมีการเชื่อมต่อกับ gateway ที่นี่)
            System.out.println("Credit Card Number: " + cardNumber);
            System.out.println("Expiration Date: " + expirationDate);
            System.out.println("CVV: " + cvv);
        } else if (paymentMethod.equals("Promptpay")) {
            // แสดงหมายเลข PromptPay ที่ใช้ชำระเงิน
            System.out.println("PromptPay Phone Number: " + promptPayPhoneNumber);
        }

        // พิมพ์ใบเสร็จ
        printFormattedReceipt(booking, amount, paymentMethod, date);
        return true; // คืนค่า true ถ้าการชำระเงินสำเร็จ
    }

    // Method to set credit card details
    public void setCreditCardDetails(String cardNumber, String expirationDate, String cvv) {
        this.cardNumber = cardNumber;
        this.expirationDate = expirationDate;
        this.cvv = cvv;
    }

    // Method to set PromptPay details
    public void setPromptPayDetails(String phoneNumber) {
        this.promptPayPhoneNumber = phoneNumber;
    }

    // Method to print formatted receipt
    public void printFormattedReceipt(Booking booking, double amount, String paymentMethod, LocalDate date) {
        String line = "=".repeat(60); // สร้างเส้นแบ่งด้วย '='
    
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


// package src.bookings;

// import java.time.LocalDate;
// import src.rooms.TransectionRoom;

// public class Payment {

//     // Constructor
//     public Payment() {
//     }

//     // Method to process payment
//     public boolean processPayment(Booking booking, double amount, String paymentMethod) {
//         // ตรวจสอบว่า amount ถูกต้องหรือไม่
//         if (amount <= 0) {
//             System.out.println("Invalid payment amount. Payment failed.");
//             return false; // ชำระเงินไม่สำเร็จ
//         }

//         // ตรวจสอบว่า amount มากกว่าหรือเท่ากับราคาที่ต้องจ่าย
//         if (amount < booking.getTotalPrice()) {
//             System.out.println("Payment amount is less than the total price. Payment failed.");
//             return false; // ชำระเงินไม่สำเร็จ
//         }

//         LocalDate date = LocalDate.now(); // วันที่ปัจจุบัน

//         System.out.println("Processing payment of " + amount + " using " + paymentMethod);

//         // พิมพ์ใบเสร็จ
//         printFormattedReceipt(booking, amount, paymentMethod, date);
//         return true; // คืนค่า true ถ้าการชำระเงินสำเร็จ
//     }

//     public void printFormattedReceipt(Booking booking, double amount, String paymentMethod, LocalDate date) {
//         System.out.println("====================================");
//         System.out.println("            Receipt                 ");
//         System.out.println("====================================");
//         System.out.println("Customer: " + booking.getAgent().getFirstName() + " " + booking.getAgent().getLastName());
//         System.out.println("Phone: " + booking.getAgent().getPhoneNumber());
//         System.out.println("Email: " + booking.getAgent().getEmail());
//         System.out.println("Check-in Date: " + booking.getCheckInDate());
//         System.out.println("Check-out Date: " + booking.getCheckOutDate());
//         System.out.println("Rooms booked:");

//         // ดึงข้อมูลห้องที่จองจาก Booking
//         for (TransectionRoom room : booking.getRooms()) {
//             System.out.println(" - Room #" + room.getRoom().getRoomNumber() + " (" + room.getRoom().getType()
//                     + "), Price per night: " + room.getRoom().getPrice());
//         }

//         System.out.println("Total amount: " + amount + " THB");
//         System.out.println("Payment Method: " + paymentMethod);
//         System.out.println("Payment Date: " + date);
//         System.out.println("====================================");
//     }

// }

