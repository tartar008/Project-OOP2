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

    // หา bookingID
    public boolean findBookingById(String bookingID) {
        JSONArray bookingsArray = readJsonBooking();
        Boolean found = false;
        String bookingId = "";
        for (Object bookingObj : bookingsArray) {
            JSONObject bookingJson = (JSONObject) bookingObj;
            String id = (String) bookingJson.get("bookingID");
            if (id.equals(bookingID)) {
                found = true;
                break;
            }
        }
        if (!found) {
            return false;
        }
        return true;
    }

    public void updateRoomStatus(String bookingId, boolean status) {
        JSONArray roomsArray = readReserveRoom(); // อ่านห้องจาก JSON ReserveRoom
        if (roomsArray == null) {
            System.out.println("Unable to read JSON_ReserveRoom.json.");
            return;
        }

        Long roomNumber = findRoomByBookingId(bookingId);
        boolean roomFound = false; // สำหรับการตรวจสอบว่าห้องมีอยู่หรือไม่

        for (Object roomObj : roomsArray) {
            JSONObject roomJson = (JSONObject) roomObj;
            Long currentRoomNumber = (Long) roomJson.get("roomNumber");

            if (currentRoomNumber.equals(roomNumber)) {
                roomJson.put("isOccupied", status);
                roomFound = true;

                // เขียนข้อมูลกลับลงไฟล์ JSON_ReserveRoom.json
                writeJsonReserveRoom(roomsArray); // เรียกใช้เมธอดในการเขียน JSON กลับ
                System.out.println("Room " + roomNumber + " status updated.");
                break; // ออกจากลูปเมื่อพบห้อง
            }
        }

        if (!roomFound) {
            System.out.println("Room number " + roomNumber + " not found in the reserve room data.");
        }
    }

    public Long findRoomByBookingId(String bookingId) {
        JSONArray bookingArray = readJsonBooking(); // อ่านเพื่อจะดึงหมายเลขห้องที่มี bookingId ของ customer
        boolean found = false;
        Long roomNumber = null;
        for (Object bookingObj : bookingArray) {
            JSONObject bookingJson = (JSONObject) bookingObj;
            String id = (String) bookingJson.get("bookingID");
            if (id.equals(bookingId)) {
                JSONArray rooms = (JSONArray) bookingJson.get("room");
                if (rooms != null && !rooms.isEmpty()) {
                    for (Object roomObj : rooms) {
                        JSONObject roomJson = (JSONObject) roomObj;
                        roomNumber = (Long) roomJson.get("roomNumber");
                    }
                }
                found = true;
                break;
            }
        }
        if (!found) {
            return null;
        }
        return roomNumber;
    }



    // ========================= about json file

    public JSONArray readJsonBooking() {
        JSONParser jsonParser = new JSONParser();
        JSONArray bookingsArray = new JSONArray();
        // File bookingFile = new File(BOOKING_FILE);

        // if (bookingFile.length() == 0) {
        // System.out.println("The booking file is empty.");
        // return null;
        // }

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
            System.out.println("Updated room data successfully.");
        } catch (IOException e) {
            System.out.println("Error writing to the file: " + e.getMessage());
            e.printStackTrace();
        }
    }

}