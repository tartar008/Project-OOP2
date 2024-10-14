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
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private double totalPrice;
    private static final String BOOKING_FILE = "./resources/JSON_Booking.json";

    public Booking() {

    }

    public Booking(Customer agent, List<Customer> guests, List<TransectionRoom> rooms, LocalDate checkInDate, LocalDate checkOutDate) {
        this.bookingID = generateBookingID();
        this.agent = agent;
        this.guests = guests;
        this.rooms = rooms;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.totalPrice = calculateTotalPrice();
    }

    public Booking(String bookingID, Customer agent, List<Customer> guests, List<TransectionRoom> rooms,
            LocalDate checkInDate, LocalDate checkOutDate, double totalPrice) {
        this.bookingID = bookingID;
        this.agent = agent;
        this.guests = guests;
        this.rooms = rooms;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.totalPrice = totalPrice;
    }

    public Booking(String bookingID, LocalDate checkInDate, LocalDate checkOutDate, List<TransectionRoom> rooms,
            Customer customer) {
        this.bookingID = bookingID;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.rooms = rooms;
        this.agent = customer;
    }

    private String generateBookingID() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddss");
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

    // =============== end set/get methods

    public static void addBooking(Booking booking) {

    }

    private double calculateTotalPrice() {
        long numberOfDays = ChronoUnit.DAYS.between(checkInDate, checkOutDate);
        double totalPrice = 0;
        for (TransectionRoom runRooms : rooms) {
            if(numberOfDays == 0){
                totalPrice += runRooms.getRoom().getPrice() * (numberOfDays+1);
            }
            totalPrice += runRooms.getRoom().getPrice() * numberOfDays;
        }

        return totalPrice;
    }

    public void confirmBooking() {
        List<Booking> bookings = readJsonBooking();

        bookings.add(this);

        WriteJsonBooking(bookings);
    }

    public void cancelBooking() {
        System.out.println("Booking Id Status: " + bookingID + " has been canceled.");
    }

    public void bookingInfo() {
        String line = "-".repeat(50);
        System.out.println(line);
        System.out.println("|                  Booking Details              |");
        System.out.println(line);
        System.out.printf("| Booking ID     :  %s                           \n", bookingID);
        System.out.printf("| Customer       :  %s %s                        \n",
                agent.getFirstName(), agent.getLastName());

        // แสดงรายการห้องพักที่จอง
        for (TransectionRoom room : rooms) {
            System.out.printf("| Room Type  :    %s   %.2f                \n",
                    room.getRoom().getType(), room.getRoom().getPrice());
        }

        System.out.printf("| Check-in Date  :   %s                            \n", checkInDate);
        System.out.printf("| Check-out Date :   %s                            \n", checkOutDate);
        System.out.printf("| Total Price    :   %.2f                          \n", totalPrice);
        System.out.println(line);
    }

    // ====================================================================================================================

    public List<Booking> readJsonBooking() {
        List<Booking> bookings = new ArrayList<>(); // สร้างรายการ bookings เพื่อเก็บข้อมูลที่อ่านได้
        JSONParser parser = new JSONParser();
        try (FileReader reader = new FileReader(BOOKING_FILE)) {
            // อ่าน object ใหญ่
            JSONObject jsonObject = (JSONObject) parser.parse(reader);
            // อ่าน Array ใน bookings
            JSONArray bookingArray = (JSONArray) jsonObject.get("bookings");

            // ดึงแต่ละ object ใน Array นั้น
            for (Object obj : bookingArray) {
                JSONObject bookingObject = (JSONObject) obj;

                // ข้อมูลการจอง
                String bookingID = (String) bookingObject.get("bookingID");

                // ข้อมูลลูกค้า
                JSONObject customerObject = (JSONObject) bookingObject.get("customer");
                Customer customer = new Customer();
                customer.setCustomerID((String) customerObject.get("customerID"));
                customer.setFirstName((String) customerObject.get("name"));
                customer.setLastName((String) customerObject.get("surname"));
                customer.setEmail((String) customerObject.get("email"));
                customer.setPhoneNumber((String) customerObject.get("phone"));

                // ข้อมูลห้องพัก (JSONArray เพื่อรองรับหลายห้อง)
                JSONArray roomsArray = (JSONArray) bookingObject.get("room");
                List<TransectionRoom> rooms = new ArrayList<>();
                List<Customer> guests = new ArrayList<>();

                // วนลูปดึงข้อมูลแต่ละห้อง
                for (Object roomObj : roomsArray) {
                    JSONObject roomObject = (JSONObject) roomObj;

                    String customerID = (String) roomObject.get("customerID"); //
                    int roomNumber = ((Long) roomObject.get("roomNumber")).intValue();
                    String roomType = (String) roomObject.get("roomType");
                    double pricePerNight = ((Number) roomObject.get("pricePerNight")).doubleValue();

                    // ข้อมูลผู้อยู่ในห้อง
                    Customer guest = new Customer();
                    guest.setFirstName((String) roomObject.get("guestFirstName"));
                    guest.setLastName((String) roomObject.get("guestLastName"));
                    guests.add(guest);

                    // สร้าง TransectionRoom และเพิ่มเข้าไปในรายการห้อง
                    TransectionRoom room = new TransectionRoom();
                    room.getRoom().setRoomNumber(roomNumber);
                    room.getRoom().setType(roomType);
                    room.getRoom().setPrice(pricePerNight);
                    rooms.add(room); // เพิ่มห้องไปในรายการ
                }

                // ข้อมูลวัน check-in และ check-out
                LocalDate checkInDate = LocalDate.parse((String) bookingObject.get("checkInDate"),
                        DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                LocalDate checkOutDate = LocalDate.parse((String) bookingObject.get("checkOutDate"),
                        DateTimeFormatter.ofPattern("yyyy-MM-dd"));

                // ข้อมูลราคา
                double totalPrice = ((Number) bookingObject.get("totalPrice")).doubleValue();

                // สร้าง Booking ใหม่และเพิ่มเข้าไปในรายการ bookings
                Booking booking = new Booking(bookingID, customer, guests, rooms, checkInDate, checkOutDate,
                        totalPrice);
                bookings.add(booking);
            }

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return bookings; // คืนค่ารายการ bookings
    }

    // เขียนลง JSON Booking
    @SuppressWarnings("unchecked")
    public void WriteJsonBooking(List<Booking> bookings) {
        // สร้าง JSON object ที่จะเก็บข้อมูล booking ทั้งหมด
        JSONObject jsonObject = new JSONObject();
        JSONArray bookingArray = new JSONArray();

        for (Booking booking : bookings) {
            // สร้าง JSONObject สำหรับแต่ละ booking
            JSONObject bookingObject = new JSONObject();
            bookingObject.put("bookingID", booking.getBookingID());

            // ข้อมูลลูกค้า
            JSONObject customerObject = new JSONObject();
            customerObject.put("customerID", booking.getAgent().getCustomerID());
            customerObject.put("name", booking.getAgent().getFirstName());
            customerObject.put("surname", booking.getAgent().getLastName());
            customerObject.put("email", booking.getAgent().getEmail());
            customerObject.put("phone", booking.getAgent().getPhoneNumber());
            bookingObject.put("customer", customerObject);

            // ข้อมูลห้องพัก
            JSONArray roomArray = new JSONArray();
            for (TransectionRoom room : booking.getRooms()) {
                JSONObject roomObject = new JSONObject();
                roomObject.put("roomNumber", room.getRoom().getRoomNumber());
                roomObject.put("roomType", room.getRoom().getType());
                roomObject.put("pricePerNight", room.getRoom().getPrice());

                // ใส่ข้อมูล guest ของห้อง
                if (!booking.getGuest().isEmpty()) {
                    roomObject.put("guestFirstName", booking.getGuest().get(booking.getRooms().indexOf(room)).getFirstName());
                    roomObject.put("guestLastName", booking.getGuest().get(booking.getRooms().indexOf(room)).getLastName());
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


    public static Booking loadBookingById(String bookingId) {
        JSONParser parser = new JSONParser();
        try (FileReader reader = new FileReader(BOOKING_FILE)) {
            // Parse the JSON object
            JSONObject jsonObject = (JSONObject) parser.parse(reader);
            JSONArray bookingArray = (JSONArray) jsonObject.get("bookings");

            // Iterate through the bookings array to find the matching booking ID
            for (Object obj : bookingArray) {
                JSONObject bookingObj = (JSONObject) obj;
                String id = (String) bookingObj.get("bookingID");

                if (id.equals(bookingId)) {
                    // Extract agent details (Customer)
                    JSONObject agentObj = (JSONObject) bookingObj.get("customer");
                    Customer agent = new Customer(
                            (String) agentObj.get("customerID"),
                            (String) agentObj.get("name"),
                            (String) agentObj.get("phone"),
                            (String) agentObj.get("email"));

                    // Extract guest details
                    List<Customer> guests = new ArrayList<>();
                    JSONArray guestArray = (JSONArray) bookingObj.get("guests");

                    for (Object guestObj : guestArray) {
                        JSONObject guestJSON = (JSONObject) guestObj;
                        String guestName = (String) guestJSON.get("name");
                        String guestPhone = (String) guestJSON.get("phone");
                        String guestEmail = (String) guestJSON.get("email");
                        Customer guest = new Customer(guestName, "", guestPhone, guestEmail); // สร้าง Customer
                                                                                              // ให้กับแต่ละแขก
                        guests.add(guest);
                    }

                    // Extract room details
                    List<TransectionRoom> rooms = new ArrayList<>();
                    JSONArray roomArray = (JSONArray) bookingObj.get("rooms");

                    for (Object roomObj : roomArray) {
                        JSONObject roomJSON = (JSONObject) roomObj;
                        int roomNumber = ((Long) roomJSON.get("roomNumber")).intValue();
                        String roomType = (String) roomJSON.get("roomType");
                        double pricePerNight = ((Number) roomJSON.get("pricePerNight")).doubleValue();

                        // Create a Room object and assign it to TransectionRoom
                        MasterRoom masterRoom = new MasterRoom(roomNumber, roomType, pricePerNight);
                        TransectionRoom transectionRoom = new TransectionRoom(masterRoom);
                        rooms.add(transectionRoom);
                    }

                    // Extract check-in and check-out dates
                    LocalDate checkInDate = LocalDate.parse((String) bookingObj.get("checkInDate"),
                            DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    LocalDate checkOutDate = LocalDate.parse((String) bookingObj.get("checkOutDate"),
                            DateTimeFormatter.ofPattern("yyyy-MM-dd"));

                    // Extract total price
                    double totalPrice = ((Number) bookingObj.get("totalPrice")).doubleValue();

                    // Return the booking object
                    return new Booking(bookingId, agent, guests, rooms, checkInDate, checkOutDate, totalPrice);
                }
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return null; // Return null if the booking with the given ID is not found
    }

}