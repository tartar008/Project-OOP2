import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import netscape.javascript.JSObject;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Manager {

    // AddRoom
    public void AddRoom(int roomNumber, String roomType, double price) {
        JSONObject existingData = ReadJSonMaterRoom();
        JSONArray materRooms = (JSONArray) existingData.get("MaterRoom");

        if (materRooms == null) {
            materRooms = new JSONArray();
        }

        // ตรวจสอบว่าหมายเลขห้องไม่ซ้ำ
        boolean isRoomNumberUnique = true;
        for (Object roomObject : materRooms) {
            JSONObject room = (JSONObject) roomObject;
            int currentRoomNumber = ((Long) room.get("roomNumber")).intValue();
            if (currentRoomNumber == roomNumber) {
                isRoomNumberUnique = false;
                break;
            }
        }

        if (isRoomNumberUnique) {
            JSONObject newRoom = new JSONObject();
            newRoom.put("roomNumber", roomNumber);
            newRoom.put("roomType", roomType);
            newRoom.put("price", price);

            materRooms.add(newRoom);
            existingData.put("MaterRoom", materRooms);

            WriteJson(existingData);
            System.out.println("Room " + roomNumber + " has been added.");
        } else {
            System.out.println("Room number " + roomNumber + " already exists.");
        }
    }

    // RemoveRoom
    public void RemoveRoom(int roomNumber) {
        JSONObject existingData = ReadJSonMaterRoom(); // อ่านข้อมูลทั้งหมด
        JSONArray materRooms = (JSONArray) existingData.get("MaterRoom"); // ดึงข้อมูล array ของห้อง

        if (materRooms != null) {
            Iterator<JSONObject> iterator = materRooms.iterator();
            boolean roomFound = false;

            while (iterator.hasNext()) {
                JSONObject room = iterator.next();
                int currentRoomNumber = ((Long) room.get("roomNumber")).intValue(); // แปลงค่า roomNumber จาก Long เป็น
                                                                                    // int

                if (currentRoomNumber == roomNumber) {
                    iterator.remove(); // ลบห้องที่มีหมายเลขตรงกับ roomNumber ที่ต้องการ
                    roomFound = true;
                    break;
                }
            }

            if (roomFound) {
                existingData.put("MaterRoom", materRooms); // อัปเดต JSON ด้วยรายการห้องที่ถูกลบ
                WriteJson(existingData); // เขียนข้อมูลที่อัปเดตกลับไปที่ไฟล์
                System.out.println("Room " + roomNumber + " has been removed.");
            } else {
                System.out.println("Room " + roomNumber + " not found.");
            }
        } else {
            System.out.println("No rooms available to remove.");
        }
    }

    // EditRoom
    public void EditRoom(int roomNumber, String newRoomType, double newPrice) {
        JSONObject existingData = ReadJSonMaterRoom();
        JSONArray materRooms = (JSONArray) existingData.get("MaterRoom");

        if (materRooms != null) {
            boolean roomFound = false;

            for (Object roomObject : materRooms) {
                JSONObject room = (JSONObject) roomObject;
                int currentRoomNumber = ((Long) room.get("roomNumber")).intValue();

                if (currentRoomNumber == roomNumber) {
                    room.put("roomType", newRoomType); // แก้ไขข้อมูลห้อง
                    room.put("price", newPrice);
                    roomFound = true;
                    break;
                }
            }

            if (roomFound) {
                existingData.put("MaterRoom", materRooms); // เขียนข้อมูลที่แก้ไข
                WriteJson(existingData);
                System.out.println("Room " + roomNumber + " has been updated.");
            } else {
                System.out.println("Room " + roomNumber + " not found.");
            }
        } else {
            System.out.println("No rooms available to edit.");
        }
    }

    // DisplayAllRooms
    public void DisplayAllRooms() {
        JSONObject existingData = ReadJSonMaterRoom();
        JSONArray materRooms = (JSONArray) existingData.get("MaterRoom");

        if (materRooms != null && !materRooms.isEmpty()) {
            System.out.println("Displaying all rooms:");
            for (Object roomObject : materRooms) {
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

    // ReadJSonMaterRoom
    private static JSONObject ReadJSonMaterRoom() {
        JSONObject existingData = new JSONObject();
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader("./JSON_MaterRoom.json")) {
            existingData = (JSONObject) jsonParser.parse(reader);

            // ตรวจสอบว่า key "MaterRoom" มีอยู่และเป็น JSONArray
            if (!existingData.containsKey("MaterRoom") || !(existingData.get("MaterRoom") instanceof JSONArray)) {
                existingData.put("MaterRoom", new JSONArray()); // ถ้าไม่มีหรือไม่ใช่ JSONArray สร้างใหม่
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found. Creating a new JSON file.");
            existingData.put("MaterRoom", new JSONArray());
            WriteJson(existingData); // สร้างไฟล์ใหม่
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        return existingData;
    }

    // WriteJSonMaterRoom
    private static void WriteJson(JSONObject existingData) {
        try (FileWriter file = new FileWriter("./JSON_MaterRoom.json")) {
            file.write(existingData.toJSONString());
            System.out.println("Data has been updated");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // DisplayReserveRoom
    public void DisplayReserveRoom() {
        JSONArray reservedRooms = ReadJsonReserveRoom(); // อ่านข้อมูลห้องที่จองจากไฟล์ JSON

        if (reservedRooms != null && !reservedRooms.isEmpty()) {
            System.out.println("Displaying all reserved rooms:");

            for (Object roomObject : reservedRooms) {
                JSONObject room = (JSONObject) roomObject; // แปลงเป็น JSONObject
                JSONObject guest = (JSONObject) room.get("guest"); // ดึงข้อมูล guest

                // ตรวจสอบว่า guest ไม่เป็น null (ห้องนั้นมีการจอง)
                if (guest != null) {
                    System.out.println("Room Number: " + room.get("roomNumber"));
                    System.out.println("Reserved By: " + guest.get("name"));
                    System.out.println("Check-in Date: " + guest.get("checkInDate"));
                    System.out.println("Check-out Date: " + guest.get("checkOutDate"));
                    System.out.println("---------------------------");
                } else {
                    System.out.println("Room Number: " + room.get("roomNumber") + " is not reserved.");
                    System.out.println("---------------------------");
                }
            }
        } else {
            System.out.println("No reserved rooms available.");
        }
    }

    // ReadJSonReserveRoom
    private static JSONArray ReadJsonReserveRoom() {
        JSONObject existingData = new JSONObject();
        JSONParser jsonParser = new JSONParser();
        JSONArray rooms = new JSONArray(); // เปลี่ยนจาก JSONObject เป็น JSONArray เนื่องจาก 'rooms' เป็นลิสต์ของห้อง

        try (FileReader reader = new FileReader("./JSON_ReserveRoom.json")) {
            existingData = (JSONObject) jsonParser.parse(reader); // พาร์สข้อมูล JSON
            rooms = (JSONArray) existingData.get("rooms"); // แคสต์เป็น JSONArray เนื่องจาก 'rooms' เป็นลิสต์ของห้อง

        } catch (FileNotFoundException e) {
            System.out.println("File not found. Creating a new JSON file.");
            existingData.put("rooms", new JSONArray()); // สร้าง key "rooms" ที่เป็นลิสต์ว่าง
            WriteJson(existingData); // สร้างไฟล์ JSON ใหม่
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        return rooms; // ส่งคืน rooms เป็น JSONArray
    }

    // HotelIncome method
    public void HotelIncome() {
        JSONObject existingData = new JSONObject();
        JSONParser jsonParser = new JSONParser();
        JSONArray bookings = new JSONArray(); // ตัวแปรเก็บข้อมูลการจอง

        // อ่านไฟล์ JSON และดึงข้อมูล
        try (FileReader reader = new FileReader("./JSON_Booking.json")) {
            existingData = (JSONObject) jsonParser.parse(reader); // พาร์สข้อมูล JSON
            bookings = (JSONArray) existingData.get("bookings"); // แคสต์เป็น JSONArray ของการจอง
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        // ตัวแปรเก็บรายได้ของแต่ละประเภทห้อง และรวมจำนวนห้อง
        Map<String, Double> roomTypeIncome = new HashMap<>();
        double totalIncome = 0.0;
        int totalRooms = 0;

        // วนลูปผ่านการจองทั้งหมด
        for (Object bookingObject : bookings) {
            JSONObject booking = (JSONObject) bookingObject;
            JSONObject room = (JSONObject) booking.get("room");

            String roomType = (String) room.get("roomType"); // ดึงประเภทห้อง
            double totalPrice = (double) booking.get("totalPrice"); // ดึงราคาการจอง

            // อัปเดตรายได้ของประเภทห้องใน Map
            roomTypeIncome.put(roomType, roomTypeIncome.getOrDefault(roomType, 0.0) + totalPrice);

            totalIncome += totalPrice; // รวมรายได้ทั้งหมดของโรงแรม
            totalRooms++; // นับจำนวนห้องที่ถูกจอง
        }

        // แสดงผล
        System.out.println("Total number of rooms booked: " + totalRooms);
        for (Map.Entry<String, Double> entry : roomTypeIncome.entrySet()) {
            System.out.println("Room Type: " + entry.getKey() + " | Total Income: " + entry.getValue());
        }
        System.out.println("Total Income of the Hotel: " + totalIncome);
    }
}