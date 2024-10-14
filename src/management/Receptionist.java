package src.management;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.time.LocalDate;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.util.ArrayList;

import src.bookings.Booking;
import src.customers.Customer;
import src.customers.Person;
import src.rooms.TransectionRoom;

public class Receptionist extends Person {
    private String staffId;

    String BOOKING_FILE = "./resources/JSON_ReserveRoom.json";

    public Receptionist(String firstName, String lastName, String phoneNumber, String email, String staffId) {
        super(firstName, lastName, phoneNumber, email);
        this.staffId = staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getStaffId() {
        return staffId;
    }

    public boolean findBookingById(String bookingID) {
        JSONArray bookingsArray = readJsonBooking();
        for (Object bookingObj : bookingsArray) {
            JSONObject bookingJson = (JSONObject) bookingObj;
            String id = (String) bookingJson.get("bookingID");
            if (id.equals(bookingID)) {
                return true;
            }
        }
        return false;
    }
    
    public void updateRoomStatus(String bookingId, boolean status) {
        JSONArray roomsArray = readReserveRoom(); // อ่านห้องจาก JSON ReserveRoom
        if (roomsArray == null) {
            return;
        }
    
        ArrayList<Long> roomNumbers = findRoomByBookingId(bookingId);
        if (roomNumbers == null || roomNumbers.isEmpty()) {
            return;
        }
    
        boolean roomFound = false; // สำหรับการตรวจสอบว่าห้องมีอยู่หรือไม่
        for (Long roomNumber : roomNumbers) {
            for (Object roomObj : roomsArray) {
                JSONObject roomJson = (JSONObject) roomObj;
                Long currentRoomNumber = (Long) roomJson.get("roomNumber");
    
                if (currentRoomNumber.equals(roomNumber)) {
                    roomJson.put("isOccupied", status);
                    roomFound = true;
                }
            }
        }
    
        if (roomFound) {
            writeJsonReserveRoom(roomsArray); // เรียกใช้เมธอดในการเขียน JSON กลับ
            System.out.println("Room status updated for booking ID: " + bookingId);
        } else {
            System.out.println("Room number(s) " + roomNumbers + " not found in the reserve room data.");
        }
    }

    public void checkOut(String bookingId) {
        JSONArray bookingsArray = readJsonBooking(); 
        JSONObject bookingToCheckOut = null;
   
        for (Object bookingObj : bookingsArray) {
            JSONObject bookingJson = (JSONObject) bookingObj;
            String id = (String) bookingJson.get("bookingID");
            if (id.equals(bookingId)) {
                bookingToCheckOut = bookingJson;
                break;
            }
        }
    
        if (bookingToCheckOut == null) {
            System.out.println("Booking ID: " + bookingId + " not found.");
            return;
        }

        String checkInDateStr = (String) bookingToCheckOut.get("checkInDate");
        String checkOutDateStr = (String) bookingToCheckOut.get("checkOutDate");

        if (checkInDateStr == null || checkOutDateStr == null) {
            System.out.println("Check-in or Check-out date is missing for booking ID: " + bookingId);
            return;
        }

        LocalDate checkInDate = LocalDate.parse(checkInDateStr);
        LocalDate checkOutDate = LocalDate.parse(checkOutDateStr);
    
        ArrayList<Long> roomNumbers = findRoomByBookingId(bookingId);

        JSONArray roomsArray = readReserveRoom();
        
        if (roomsArray == null) {
            System.out.println("Unable to read JSON_ReserveRoom.json.");
            return;
        }
    
        boolean roomFound = false;
        for (Long roomNumber : roomNumbers) {
            for (Object roomObj : roomsArray) {
                JSONObject roomJson = (JSONObject) roomObj;
                Long currentRoomNumber = (Long) roomJson.get("roomNumber");
    
                if (currentRoomNumber.equals(roomNumber)) {
                    JSONArray unavailableDates = (JSONArray) roomJson.get("unavailableDates");
    
                    // ลบวันที่ checkInDate ถึง checkOutDate ออกจาก unavailableDates
                    for (LocalDate date = checkInDate; !date.isAfter(checkOutDate); date = date.plusDays(1)) {
                        unavailableDates.remove(date.toString());
                    }
    
                    roomFound = true;
                    break;
                }
            }
        }
    
        if (roomFound) {
            writeJsonReserveRoom(roomsArray); // เขียนไฟล์กลับ
            System.out.println("Check-out successful for booking ID: " + bookingId);
        } else {
            System.out.println("Room number(s) " + roomNumbers + " not found in the reserve room data.");
        }
    }
    
    public void checkIn(String bookinIdInput){
        boolean isbooking = findBookingById(bookinIdInput);
        JSONArray roomArray = readReserveRoom();
        if(isbooking) {
            ArrayList

        }
    }

    public ArrayList<Long> findRoomByBookingId(String bookingId) {
        JSONArray bookingArray = readJsonBooking(); // อ่านเพื่อจะดึงหมายเลขห้องที่มี bookingId ของ customer
        ArrayList<Long> roomNumbers = new ArrayList<>();
        
        for (Object bookingObj : bookingArray) {
            JSONObject bookingJson = (JSONObject) bookingObj;
            String id = (String) bookingJson.get("bookingID");
            if (id.equals(bookingId)) {
                JSONArray rooms = (JSONArray) bookingJson.get("room");
                if (rooms != null && !rooms.isEmpty()) {
                    for (Object roomObj : rooms) {
                        JSONObject roomJson = (JSONObject) roomObj;
                        roomNumbers.add((Long) roomJson.get("roomNumber"));
                    }
                }
                break;
            }
        }
        return roomNumbers; // ถ้าไม่พบ bookingId จะส่งคืน empty list
    }

    public void displayCustomerRoom(String bookingIdInput){
        JSONArray bookingArray = readJsonBooking();
        boolean foundId = true;
        boolean isbookingId = findBookingById(bookingIdInput);
        if(!isbookingId) {
            return;
        }
        else {
            for(Object bookingObj : bookingArray){
                JSONObject bookingJson = (JSONObject) bookingObj;
                String id = (String) bookingJson.get("bookingID");
                if(id.equals(bookingIdInput)){
                    int totalWidth = 50; // ความกว้างทั้งหมด
                    int paddingSize = totalWidth / 2;
                    String padding = " ".repeat(paddingSize);//ช่องไฟ
                    System.out.println("\n" + "-".repeat(totalWidth));
                    System.out.printf("Room Details");
                    System.out.println("\n" + "-".repeat(totalWidth));
                    JSONArray rooms = (JSONArray) bookingJson.get("room");
                    if(!rooms.isEmpty()){
                        for(Object roomObj : rooms) {
                            JSONObject roomJson = (JSONObject) roomObj;
                            Long roomNumber = (Long)  roomJson.get("roomNumber");
                            String roomType = (String) roomJson.get("roomType");
                            String name = (String) roomJson.get("guestFirstName");
                            String surname = (String) roomJson.get("guestLastName");
                            System.out.println("Room Type    : " + roomType);
                            System.out.println("Room Number  : " + roomNumber);
                            System.out.println("Guest Name   : " + name + " " + surname);
                            System.out.println();
                        }
                    }
                    String checkInDate = (String) bookingJson.get("checkInDate");
                    String checkOutDate = (String) bookingJson.get("checkOutDate");
                    System.out.println("Check In     : " + checkInDate);
                    System.out.println("Check Out    : " + checkOutDate);
                    System.out.println("-".repeat(totalWidth));
                    
                    foundId = true;
                }
                if(!foundId){
                    break;
                }
            }
        }
    }
    // ========================= about json file

    public JSONArray readJsonBooking() {
        JSONParser jsonParser = new JSONParser();
        JSONArray bookingsArray = new JSONArray();

        try (FileReader reader = new FileReader("./resources/JSON_Booking.json")) {
            // แปลง JSON จากไฟล์เป็น JSONObject
            Object obj = jsonParser.parse(reader);
            JSONObject jsonObject = (JSONObject) obj;

            // ดึงข้อมูล JSONArray ของการจองจาก JSONObject
            bookingsArray = (JSONArray) jsonObject.get("bookings");

        } catch (IOException e) {
            System.out.println("Error reading the file: " + e.getMessage());
            e.printStackTrace();
        } catch (ParseException e) {
            System.out.println("Error parsing JSON data: " + e.getMessage());
            e.printStackTrace();
        }

        return bookingsArray;
    }

    public JSONArray readReserveRoom() {
        JSONParser jsonParser = new JSONParser();
        JSONArray roomsArray = new JSONArray(); // สร้าง JSONArray เพื่อเก็บข้อมูลห้อง

        try (FileReader reader = new FileReader("./resources/JSON_ReserveRoom.json")) {

            Object obj = jsonParser.parse(reader); // ParseException อาจเกิดขึ้นที่นี่
            JSONObject jsonObject = (JSONObject) obj;

            roomsArray = (JSONArray) jsonObject.get("rooms");
        } catch (IOException e) {
            System.out.println("Error reading the file: " + e.getMessage());
            e.printStackTrace();
        } catch (ParseException e) {
            System.out.println("Error parsing JSON data: " + e.getMessage());
            e.printStackTrace();
        }

        return roomsArray; // คืนค่าข้อมูลห้องทั้งหมด
    }// end read JSON_ReserveRoom

    public void writeJsonReserveRoom(JSONArray room) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("rooms", room); // สร้าง JSON Object และเพิ่ม roomsArray

        try (FileWriter fileWriter = new FileWriter("./resources/JSON_ReserveRoom.json")) {
            fileWriter.write(jsonObject.toJSONString()); // เขียน JSON Object ลงไฟล์
            fileWriter.flush(); // ทำการบังคับเขียนข้อมูลลงไฟล์
        } catch (IOException e) {
            System.out.println("Error writing to the file: " + e.getMessage());
            e.printStackTrace();
        }
    }

}