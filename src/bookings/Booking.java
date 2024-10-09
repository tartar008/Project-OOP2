package src.bookings;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import src.rooms.*;
import src.customers.*;

public class Booking {
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
    private static final String BOOKING_FILE = "./JSON_Booking.json";

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

    // ====================================================================================================================
    // Read and Write JSON

    public void ReadJsonBooking() {
        JSONParser parser = new JSONParser();
        try (FileReader reader = new FileReader(BOOKING_FILE)) {
            // อ่าน object ใหญ่
            JSONObject jsonObject = (JSONObject) parser.parse(reader);
            // อ่าน Array ใน bookings
            JSONArray bookingArray = (JSONArray) jsonObject.get("bookings");

            // ดึงแต่ละ object ใน Array นั้น
            for (Object obj : bookingArray) {
                JSONObject bookingObject = (JSONObject) obj;

                bookingID = (String) bookingObject.get("bookingID");
                // ข้อมูลลูกค้า

                JSONObject customerObject = (JSONObject) bookingObject.get("customer");
                agent.setCustomerID((String) customerObject.get("customerID"));
                agent.setFirstName((String) customerObject.get("name"));
                agent.setEmail((String) customerObject.get("email"));
                agent.setPhoneNumber((String) customerObject.get("phone"));

                // ข้อมูลห้องและผู้อยู่
                JSONArray roomArray = (JSONArray) bookingObject.get("room");

                for (Object roomObj : roomArray) {
                    JSONObject roomObject = (JSONObject) roomObj;
                    int roomNumber = ((Long) roomObject.get("roomNumber")).intValue();
                    String roomType = (String) roomObject.get("roomType");
                    double pricePerNight = ((Number) roomObject.get("pricePerNight")).doubleValue();

                    Customer guest = new Customer();
                    guest.setFirstName((String) roomObject.get("guest"));

                    // สร้าง TransectionRoom และเพิ่มเข้าไปในรายการห้อง
                    TransectionRoom room = new TransectionRoom();
                    room.getRoom().setRoomNumber(roomNumber);
                    room.getRoom().setType(roomType);
                    room.getRoom().setPrice(pricePerNight);
                    this.guests.add(guest);
                    this.rooms.add(room);
                }

                this.totalPrice = ((Number) bookingObject.get("totalPrice")).doubleValue();
                // แปลง String เป็น LocalDate
                checkInDate = LocalDate.parse((String) bookingObject.get("checkInDate"),
                        DateTimeFormatter.ofPattern("yyyy-MM-dd"));

                // แก้ให้ตัวแปร checkOutDate ใช้ข้อมูลจาก bookingObject
                checkOutDate = LocalDate.parse((String) bookingObject.get("checkOutDate"),
                        DateTimeFormatter.ofPattern("yyyy-MM-dd"));

                // เพิ่ม booking เข้าไปในรายการ bookings
            }

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    public void WriteJsonBooking() {
        // สร้าง JSON object ที่จะเก็บข้อมูล booking ทั้งหมด
        JSONObject jsonObject = new JSONObject();
        JSONArray bookingArray = new JSONArray();

        for (Booking booking : bookings) {
            // สร้าง JSONObject สำหรับแต่ละ booking
            JSONObject bookingObject = new JSONObject();
            bookingObject.put("bookingID", booking.getBookingID());

            // ข้อมูลลูกค้า
            JSONObject customerObject = new JSONObject();
            customerObject.put("customerID", booking.getCustomer().getCustomerID());
            customerObject.put("name", booking.getCustomer().getFirstName());
            customerObject.put("email", booking.getCustomer().getEmail());
            customerObject.put("phone", booking.getCustomer().getPhoneNumber());
            bookingObject.put("customer", customerObject);

            // ข้อมูลห้องพัก
            JSONArray roomArray = new JSONArray();
            for (TransectionRoom room : booking.getRooms()) {
                JSONObject roomObject = new JSONObject();
                roomObject.put("roomNumber", room.getRoom().getRoomNumber());
                roomObject.put("roomType", room.getRoom().getType());
                roomObject.put("pricePerNight", room.getRoom().getPrice());

                // ใส่ข้อมูล guest ของห้อง
                if (!guests.isEmpty()) {
                    roomObject.put("guest", guests.get(booking.getRooms().indexOf(room)).getFirstName());
                } else {
                    roomObject.put("guest", " ");
                }

                roomArray.add(roomObject);
            }
            bookingObject.put("room", roomArray);

            // ข้อมูลวันที่
            bookingObject.put("checkInDate", booking.getCheckInDate().toString());
            bookingObject.put("checkOutDate", booking.getCheckOutDate().toString());

            // ข้อมูลราคา
            bookingObject.put("totalPrice", booking.getTotalPrice());

            // เพิ่ม bookingObject ลงใน bookingArray
            bookingArray.add(bookingObject);
        }

        // เพิ่ม bookingArray ลงใน jsonObject
        jsonObject.put("bookings", bookingArray);

        // เขียนข้อมูลลงไฟล์
        try (FileWriter file = new FileWriter(BOOKING_FILE)) {
            file.write(jsonObject.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}