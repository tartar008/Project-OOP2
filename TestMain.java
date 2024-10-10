

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import src.bookings.Booking;
import src.customers.Customer;
import src.management.Manager;
import src.rooms.*;



public class TestMain {
    public static void main(String[] args) {
        // สร้าง Customer agent และ guests
        Customer agent = new Customer("John", "Doe", "123456789", "john.doe@example.com");
        
        Customer guest1 = new Customer();
        guest1.setFirstName("Jane");
        guest1.setLastName("Smith");
        
        Customer guest2 = new Customer();
        guest2.setFirstName("Emily");
        guest2.setLastName("Johnson");
        
        List<Customer> guests = new ArrayList<>();
        guests.add(guest1);
        guests.add(guest2);
        
        // สร้าง TransectionRoom ห้องพัก
        MasterRoom masterRoom1 = new MasterRoom(103, "Standard", 1500.0);
        TransectionRoom room1 = new TransectionRoom();
        room1.setRoom(masterRoom1);


        MasterRoom masterRoom2 = new MasterRoom(104, "Standard", 2500.0);
        TransectionRoom room2 = new TransectionRoom();
        room2.setRoom(masterRoom2);


        List<TransectionRoom> Transectionrooms = new ArrayList<>();
        Transectionrooms.add(room1);
        Transectionrooms.add(room2);

        // กำหนดวันเช็คอินและเช็คเอาท์
        LocalDate checkInDate = LocalDate.of(2024, 10, 20);
        LocalDate checkOutDate = LocalDate.of(2024, 10, 25);

        // สร้าง Booking
        Booking booking = new Booking(agent, guests, Transectionrooms, 2, 1, checkInDate, checkOutDate);

        // แสดงข้อมูลการจอง
        booking.bookingInfo();

        // ยืนยันการจอง
        booking.confirmBooking();

        // ทดสอบการเขียนข้อมูลลงไฟล์ JSON
        List<Booking> bookingList = new ArrayList<>();
        bookingList.add(booking);
        booking.WriteJsonBooking(bookingList);

        // ทดสอบการอ่านข้อมูลจากไฟล์ JSON
        List<Booking> readBookings = booking.readJsonBooking("./JSON_Booking.json");
        for (Booking bkg : readBookings) {
            bkg.bookingInfo();
        }
    }
}
