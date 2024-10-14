package src.management;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import src.rooms.MasterRoom;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Manager {
    private static final String ROOM_FILE = "./resources/JSON_MaterRoom.json";
    private static final String BOOKING_FILE = "./resources/JSON_Booking.json";
    private static final String RESERVEROOM_FILE = "./resources/JSON_ReserveRoom.json";

    public Manager() {
    }

    public ArrayList<MasterRoom> loadRooms() {
        ArrayList<MasterRoom> rooms = new ArrayList<>();
        JSONParser jsonParser = new JSONParser();

        File roomFile = new File(ROOM_FILE);

        // ตรวจสอบว่าไฟล์มีอยู่และว่างหรือไม่
        if (!roomFile.exists() || roomFile.length() == 0) {
            System.out.println("File does not exist or is empty. Creating default room data.");
            JSONArray roomArray = addDefaultRooms(); // สร้างข้อมูลห้องพื้นฐาน
            WriteJson(roomArray); // เขียนข้อมูลกลับไปที่ไฟล์
        }

        try (FileReader reader = new FileReader(ROOM_FILE)) {
            // อ่านข้อมูล JSON
            Object obj = jsonParser.parse(reader);
            JSONArray roomList = (JSONArray) obj;

            // ประมวลผลข้อมูลแต่ละห้อง
            for (Object roomObject : roomList) {
                JSONObject roomJson = (JSONObject) roomObject;
                int roomNumber = ((Long) roomJson.get("roomNumber")).intValue();
                double price = (Double) roomJson.get("price");
                String type = (String) roomJson.get("type");

                rooms.add(new MasterRoom(roomNumber, type, price));
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return rooms;
    }

    // ส่วนที่เกี่ยวข้องกับการจัดการห้อง
    private static JSONArray addDefaultRooms() {
        JSONArray defaultRooms = new JSONArray();

        // เพิ่มห้องมาตรฐานถ้ายังไม่มี
        if (defaultRooms.isEmpty()) {
            defaultRooms.add(createRoomJson(101, "Standard", 1000));
            defaultRooms.add(createRoomJson(102, "Standard", 1000));
            defaultRooms.add(createRoomJson(103, "Standard", 1000));
            defaultRooms.add(createRoomJson(104, "Standard", 1000));
            defaultRooms.add(createRoomJson(105, "Standard", 1000));
            defaultRooms.add(createRoomJson(201, "Family", 2000));
            defaultRooms.add(createRoomJson(202, "Family", 2000));
            defaultRooms.add(createRoomJson(203, "Family", 2000));
            defaultRooms.add(createRoomJson(301, "Honeymoon", 3000));
            defaultRooms.add(createRoomJson(302, "Honeymoon", 3000));
        }

        return defaultRooms; // ส่งคืน array ของห้องมาตรฐาน
    }

    // สร้าง JSONObject สำหรับห้อง
    private static JSONObject createRoomJson(int roomNumber, String roomType, double price) {
        JSONObject roomJson = new JSONObject();
        roomJson.put("roomNumber", roomNumber);
        roomJson.put("roomType", roomType);
        roomJson.put("price", price);
        return roomJson;
    }

    // =============================================================================================

    // เพิ่มห้องใหม่
    public void AddRoom(int roomNumber, String roomType, double price) {
        // รับข้อมูลห้องที่ได้จากการอ่าน
        JSONArray existingData = ReadJsonMasterRoom();

        // เช็คว่าห้องมีอยู่แล้วรึเปล่า
        boolean isRoomNumberUnique = true;
        for (Object roomObject : existingData) {
            JSONObject room = (JSONObject) roomObject;

            System.out.println("==========START DEBUG ==============");
            System.out.println("roomNumber : " + roomNumber);

            int currentRoomNumber = ((Long) room.get("roomNumber")).intValue();

            System.out.println("currentRoomNumber : " + currentRoomNumber);
            System.out.println("==========END DEBUG ==============");

            if (currentRoomNumber == roomNumber) {
                isRoomNumberUnique = false; // Duplicate room number found
                break;
            }
        }

        // เพิ่มห้อง
        if (isRoomNumberUnique) {
            JSONObject newRoom = new JSONObject();
            newRoom.put("roomNumber", roomNumber);
            newRoom.put("roomType", roomType);
            newRoom.put("price", price);

            // เพิ่มห้องใหม่
            existingData.add(newRoom);

            // เขียนกลับลงไป
            WriteJson(existingData);
            System.out.println("Room " + roomNumber + " has been added.");
        } else {
            System.out.println("Room number " + roomNumber + " already exists.");
        }
    }

    // ลบห้อง
    public void RemoveRoom(int roomNumber) {
        // Read all room data
        JSONArray existingData = ReadJsonMasterRoom(); // Assuming this now returns JSONArray directly

        if (existingData != null) {
            Iterator<JSONObject> iterator = existingData.iterator();
            boolean roomFound = false;

            while (iterator.hasNext()) {
                JSONObject room = iterator.next();
                int currentRoomNumber = ((Long) room.get("roomNumber")).intValue(); // Convert roomNumber from Long to

                if (currentRoomNumber == roomNumber) {
                    iterator.remove(); // Remove the room with the matching roomNumber
                    roomFound = true;
                    break;
                }
            }

            if (roomFound) {
                WriteJson(existingData); // Write the updated data back to the file
                System.out.println("Room " + roomNumber + " has been removed.");
            } else {
                System.out.println("Room " + roomNumber + " not found.");
            }
        } else {
            System.out.println("No rooms available to remove.");
        }
    }

    // แก้ไขห้อง
    public void EditRoom(int roomNumber, String newRoomType, double newPrice) {
        JSONArray existingData = ReadJsonMasterRoom(); // Read existing room data

        if (existingData != null) {
            boolean roomFound = false;

            for (Object roomObject : existingData) {
                JSONObject room = (JSONObject) roomObject;
                int currentRoomNumber = ((Long) room.get("roomNumber")).intValue();

                if (currentRoomNumber == roomNumber) {
                    room.put("roomType", newRoomType); // Update room type
                    room.put("price", newPrice); // Update room price
                    roomFound = true;
                    break;
                }
            }

            if (roomFound) {
                WriteJson(existingData); // Write the updated data back to the file
                System.out.println("Room " + roomNumber + " has been updated.");
            } else {
                System.out.println("Room " + roomNumber + " not found.");
            }
        } else {
            System.out.println("No rooms available to edit.");
        }
    }

    // แสดงห้อง
    public void DisplayAllRooms() {
        JSONArray existingData = ReadJsonMasterRoom(); // Read existing room data

        if (existingData != null && !existingData.isEmpty()) {
            System.out.println("Displaying all rooms:");
            for (Object roomObject : existingData) {
                JSONObject room = (JSONObject) roomObject;
                System.out.println("Room Number: " + room.get("roomNumber"));
                System.out.println("Room Type: " + room.get("roomType"));
                System.out.println("Price: " + room.get("price"));
                System.out.println("---------------------------");
            }
        } else {
            System.out.println("No rooms available.");
        }
    }

    // // ส่วนที่เกี่ยวข้องกับการแสดงข้อมูลการจองห้อง
    // public void DisplayReserveRoom() {
    // JSONArray reservedRooms = ReadJsonReserveRoom(); //
    // อ่านข้อมูลห้องที่จองจากไฟล์ JSON

    // if (reservedRooms != null && !reservedRooms.isEmpty()) {
    // System.out.println("Displaying all reserved rooms:");

    // for (Object roomObject : reservedRooms) {
    // JSONObject room = (JSONObject) roomObject; // แปลงเป็น JSONObject
    // JSONObject guest = (JSONObject) room.get("guest"); // ดึงข้อมูล guest

    // // ตรวจสอบว่า guest ไม่เป็น null (ห้องนั้นมีการจอง)
    // if (guest != null) {
    // System.out.println("Room Number: " + room.get("roomNumber"));
    // System.out.println("Reserved By: " + guest.get("name"));
    // System.out.println("Check-in Date: " + guest.get("checkInDate"));
    // System.out.println("Check-out Date: " + guest.get("checkOutDate"));
    // System.out.println("---------------------------");
    // } else {
    // System.out.println("Room Number: " + room.get("roomNumber") + " is not
    // reserved.");
    // System.out.println("---------------------------");
    // }
    // }
    // } else {
    // System.out.println("No reserved rooms available.");
    // }
    // }

    // =============================================================================================

    // ส่วนที่เกี่ยวข้องกับการอ่านและเขียน JSON
    private static JSONArray ReadJsonMasterRoom() {
        JSONArray roomArray = new JSONArray();
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader(ROOM_FILE)) {
            // Read JSON file and parse it as JSONArray
            roomArray = (JSONArray) jsonParser.parse(reader);

            // ตรวจสอบว่า roomArray ว่างหรือไม่
            if (roomArray.isEmpty()) {
                System.out.println("File is empty. Creating default room data.");
                roomArray = addDefaultRooms(); // สร้างข้อมูลห้องพื้นฐาน
                WriteJson(roomArray); // เขียนข้อมูลกลับไปที่ไฟล์
            }

            // สร้างข้อมูลห้องใหม่และบันทึกลงในไฟล์
            // roomArray = addDefaultRooms();

        } catch (FileNotFoundException e) {
            System.out.println("File not found. Creating a new JSON file.");

            // Create new room data and save it to the file
            roomArray = addDefaultRooms(); // Method to generate the default room data
            WriteJson(roomArray); // Write to file
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        return roomArray;
    }

    // ฟังก์ชันสำหรับเขียน JSON กลับลงไฟล์
    public static void WriteJson(JSONArray roomArray) {
        try (FileWriter file = new FileWriter(ROOM_FILE)) {
            file.write(roomArray.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // =============================================================================================

    private static JSONArray ReadJsonBookings() {
        JSONObject existingData = new JSONObject();
        JSONParser jsonParser = new JSONParser();
        JSONArray bookings = new JSONArray();

        try (FileReader reader = new FileReader(BOOKING_FILE)) {
            existingData = (JSONObject) jsonParser.parse(reader);
            bookings = (JSONArray) existingData.get("bookings");
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return bookings;
    }

    public void displayAllBookingHistories() {
        JSONArray bookings = ReadJsonBookings();

        System.out.println("All Booking Histories:");

        if (bookings.isEmpty()) {
            System.out.println("No bookings found.");
            return;
        }

        // พิมพ์หัวตาราง
        System.out.printf("%-15s %-15s %-15s %-15s %-15s %-15s\n", "Booking ID", "Room Number", "Room Type",
                "Check-in Date", "Check-out Date", "Total Price");
        System.out.println(
                "------------------------------------------------------------------------------------------------");

        for (Object booking : bookings) {
            JSONObject bookingDetails = (JSONObject) booking;
            String bookingID = (String) bookingDetails.get("bookingID");
            String checkInDate = (String) bookingDetails.get("checkInDate");
            String checkOutDate = (String) bookingDetails.get("checkOutDate");
            double totalPrice = (double) bookingDetails.get("totalPrice");

            // ดึงข้อมูลห้อง
            JSONArray rooms = (JSONArray) bookingDetails.get("room");

            for (Object room : rooms) {
                JSONObject roomDetails = (JSONObject) room;
                int roomNumber = ((Long) roomDetails.get("roomNumber")).intValue();
                String roomType = (String) roomDetails.get("roomType");

                // พิมพ์ข้อมูลในรูปแบบตาราง
                System.out.printf("%-15s %-15d %-15s %-15s %-15s %-15.2f\n", bookingID, roomNumber, roomType,
                        checkInDate, checkOutDate, totalPrice);
            }
        }
    }

    // =============================================================================================

    //
    public void HotelIncome() {
        JSONObject existingData = new JSONObject();
        JSONParser jsonParser = new JSONParser();
        JSONArray bookings = new JSONArray(); // ตัวแปรเก็บข้อมูลการจอง

        // อ่านไฟล์ JSON และดึงข้อมูล
        try (FileReader reader = new FileReader(BOOKING_FILE)) {
            existingData = (JSONObject) jsonParser.parse(reader); // พาร์สข้อมูล JSON
            bookings = (JSONArray) existingData.get("bookings"); // แคสต์เป็น JSONArray ของการจอง
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
            return; // ออกจากฟังก์ชันหากไม่พบไฟล์
        } catch (IOException | ParseException e) {
            e.printStackTrace();
            return; // ออกจากฟังก์ชันหากเกิดข้อผิดพลาด
        }

        // ตัวแปรเก็บรายได้ของแต่ละประเภทห้อง และรวมรายได้ทั้งหมด
        Map<String, Double> roomTypeIncome = new HashMap<>();
        double totalIncome = 0.0;

        // วนลูปผ่านการจองทั้งหมด
        for (Object bookingObject : bookings) {
            JSONObject booking = (JSONObject) bookingObject;
            JSONArray rooms = (JSONArray) booking.get("room"); // ดึงห้องทั้งหมดในการจอง

            // ตรวจสอบห้องในแต่ละการจอง
            for (Object roomObject : rooms) {
                JSONObject room = (JSONObject) roomObject;

                String roomType = (String) room.get("roomType"); // ดึงประเภทห้อง
                double totalPrice = (double) booking.get("totalPrice"); // ดึงราคาการจอง

                // อัปเดตรายได้ของประเภทห้องใน Map
                roomTypeIncome.put(roomType, roomTypeIncome.getOrDefault(roomType, 0.0) + totalPrice);
            }

            totalIncome += (double) booking.get("totalPrice"); // รวมรายได้ทั้งหมดของโรงแรม
        }

        // แสดงผล
        System.out.println("Income by Room Type:");
        for (Map.Entry<String, Double> entry : roomTypeIncome.entrySet()) {
            System.out.printf("Room Type: %s | Total Income: %.2f%n", entry.getKey(), entry.getValue());
        }
        System.out.printf("Total Income of the Hotel: %.2f%n", totalIncome);
    }

}