package src.bookings;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException; // นำเข้า ParseException

import java.util.Random;

import src.rooms.TransectionRoom;

public class ReserveRoom {
    private ArrayList<TransectionRoom> transectionRooms;
    private static final String RESERVEROOM_FILE = "./resources/JSON_ReserveRoom.json";

    public ReserveRoom() {
        this.transectionRooms = new ArrayList<>();
    }

    public void AddTransectionRoom(TransectionRoom transectionRoom) {
        transectionRooms.add(transectionRoom);

    }

    public ArrayList<TransectionRoom> getTransectionRoom() {
        return transectionRooms;
    }

    public List<TransectionRoom> getAvailableRooms(LocalDate checkInDate, LocalDate checkOutDate) {
        List<TransectionRoom> availableRooms = new ArrayList<>();
        List<Integer> occupiedRoomNumbers = new ArrayList<>(); // เก็บหมายเลขห้องที่ถูกจองจาก JSON

        // อ่านข้อมูลห้องจากไฟล์ JSON
        JSONArray roomsArray = readJsonBooking();

        // ตรวจสอบห้องที่มีใน roomsArray (ซึ่งหมายถึงห้องที่ไม่ว่าง)
        for (Object roomObj : roomsArray) {
            JSONObject roomJson = (JSONObject) roomObj;

            // ดึงหมายเลขห้องที่ไม่ว่าง
            int roomNumber = ((Long) roomJson.get("roomNumber")).intValue();
            JSONArray unavailableDatesArray = (JSONArray) roomJson.get("unavailableDates");

            // ถ้าห้องมี unavailableDates ว่าง
            // หรือไม่มีวันที่ไม่ว่างตรงกับวันที่รับพารามิเตอร์
            if (unavailableDatesArray.isEmpty() ||
                    !isDateInRange(unavailableDatesArray, checkInDate, checkOutDate)) {
                availableRooms.add(getRoomFromJson(roomJson)); // เพิ่มห้องที่ว่างลงใน availableRooms
            } else {
                occupiedRoomNumbers.add(roomNumber); // ห้องที่มี unavailableDates ไม่ว่าง
            }
        }

        // วนลูปผ่าน transectionRooms ทั้งหมดเพื่อตรวจสอบห้องที่ไม่อยู่ใน
        // occupiedRoomNumbers
        for (TransectionRoom room : transectionRooms) {
            if (!occupiedRoomNumbers.contains(room.getRoom().getRoomNumber())) {
                // ตรวจสอบว่าห้องว่างในช่วงวันที่ที่ระบุ
                if (room.isRoomAvailableForPeriod(checkInDate, checkOutDate)) {
                    availableRooms.add(room); // เพิ่มห้องที่ว่างลงใน availableRooms
                }
            }
        }
        return availableRooms;
    }

    // ฟังก์ชันช่วยเพื่อตรวจสอบว่ามีวันที่ใดใน unavailableDates
    // ตรงกับวันที่รับพารามิเตอร์หรือไม่
    private boolean isDateInRange(JSONArray unavailableDatesArray, LocalDate checkInDate, LocalDate checkOutDate) {
        for (Object dateObj : unavailableDatesArray) {
            LocalDate unavailableDate = LocalDate.parse((String) dateObj);
            // ตรวจสอบว่าวันที่ไม่ว่างอยู่ในช่วงที่ลูกค้าจอง
            if ((unavailableDate.isEqual(checkInDate) || unavailableDate.isEqual(checkOutDate)) ||
                    (unavailableDate.isAfter(checkInDate) && unavailableDate.isBefore(checkOutDate))) {
                return true; // หากพบวันที่ไม่ว่าง ตรงกับช่วงที่จอง
            }
        }
        return false; // ไม่พบวันที่ไม่ว่างในช่วงที่จอง
    }

    // ฟังก์ชันช่วยในการสร้าง TransectionRoom จาก JSONObject
    private TransectionRoom getRoomFromJson(JSONObject roomJson) {
        // สร้าง TransectionRoom จากข้อมูลใน roomJson
        // ตัวอย่างโค้ดอาจต้องมีการปรับให้เหมาะสมกับโครงสร้างของ TransectionRoom
        TransectionRoom room = new TransectionRoom();
        room.getRoom().setRoomNumber(((Long) roomJson.get("roomNumber")).intValue());
        room.getRoom().setPrice((Double) roomJson.get("price"));
        room.getRoom().setType((String) roomJson.get("roomType"));
        // ค่าต่างๆ ตามที่ TransectionRoom ต้องการ
        return room;
    }

    public List<TransectionRoom> getAvailableRoomsByType(String roomType, LocalDate checkInDate,
            LocalDate checkOutDate) {
        List<TransectionRoom> availableRooms = new ArrayList<>(); // ใช้ List
        List<Integer> addedRoomNumbers = new ArrayList<>(); // เก็บหมายเลขห้องที่ถูกเพิ่มลงใน availableRooms
        JSONArray roomsArray = readJsonBooking();

        if (roomsArray != null) {
            for (TransectionRoom room : transectionRooms) {
                if (room.getRoom().getType().equals(roomType)) {
                    boolean isRoomAvailable = true;

                    // ตรวจสอบใน JSON ว่าหมายเลขห้องนี้ไม่ว่างในวันที่จองหรือไม่
                    for (Object roomObj : roomsArray) {
                        JSONObject roomJson = (JSONObject) roomObj;
                        int roomNumber = ((Long) roomJson.get("roomNumber")).intValue();

                        if (room.getRoom().getRoomNumber() == roomNumber) {
                            // ห้องนี้อยู่ในรายการห้องที่ถูกจอง ต้องตรวจสอบวันที่ไม่ว่าง
                            JSONArray unavailableDatesArray = (JSONArray) roomJson.get("unavailableDates");

                            for (Object dateObj : unavailableDatesArray) {
                                LocalDate unavailableDate = LocalDate.parse((String) dateObj);

                                // ถ้าวันที่ไม่ว่างตรงกับช่วงวันที่ที่กำหนด ก็แสดงว่าห้องนี้ไม่ว่าง
                                if ((unavailableDate.isEqual(checkInDate) || unavailableDate.isEqual(checkOutDate)) ||
                                        (unavailableDate.isAfter(checkInDate)
                                                && unavailableDate.isBefore(checkOutDate))) {
                                    isRoomAvailable = false; // ห้องไม่ว่างในช่วงวันที่นี้
                                    break;
                                }
                            }
                        }
                    }

                    // ถ้าห้องยังว่างในช่วงวันที่ที่กำหนด
                    if (isRoomAvailable && room.isRoomAvailableForPeriod(checkInDate, checkOutDate)) {
                        // ตรวจสอบก่อนที่จะเพิ่มห้องซ้ำ
                        if (!addedRoomNumbers.contains(room.getRoom().getRoomNumber())) {
                            availableRooms.add(room); // เพิ่มห้องที่ว่างลงใน availableRooms
                            addedRoomNumbers.add(room.getRoom().getRoomNumber()); // บันทึกหมายเลขห้องที่ถูกเพิ่ม
                        }
                    }
                }
            }
        } else {
            // กรณีไม่มีข้อมูล JSON จะตรวจสอบห้องใน transectionRooms
            for (TransectionRoom room : transectionRooms) {
                if (room.getRoom().getType().equals(roomType)
                        && room.isRoomAvailableForPeriod(checkInDate, checkOutDate)) {
                    // ตรวจสอบก่อนที่จะเพิ่มห้องซ้ำ
                    if (!addedRoomNumbers.contains(room.getRoom().getRoomNumber())) {
                        availableRooms.add(room); // เพิ่มห้องที่ว่างลงใน availableRooms
                        addedRoomNumbers.add(room.getRoom().getRoomNumber()); // บันทึกหมายเลขห้องที่ถูกเพิ่ม
                    }
                }
            }
        }

        return availableRooms; // คืนค่าเป็น List ของห้องที่ว่าง
    }

    // สุ่มเลขห้อง
    public TransectionRoom assignRoom(List<TransectionRoom> rooms) {
        Random random = new Random();
        // สุ่มเลือกห้องจากรายการที่มีอยู่
        int randomIndex = random.nextInt(rooms.size());
        TransectionRoom selectedRoom = rooms.get(randomIndex);

        // นำห้องมี่สุ่มได้ไปเซตสถานะ isReserve : true
        return selectedRoom;
    }

    // =======================================================
    // บันทึกข้อมูลห้องจองลงไฟล์ JSON
    @SuppressWarnings("unchecked")
    public void WriteJsonBooking() {
        JSONObject jsonObject = new JSONObject();
        JSONArray roomsArray = new JSONArray();

        for (TransectionRoom room : transectionRooms) {
            JSONObject roomObject = new JSONObject();
            roomObject.put("roomNumber", room.getRoom().getRoomNumber());
            roomObject.put("roomType", room.getRoom().getType());
            roomObject.put("price", room.getRoom().getPrice());
            roomObject.put("isOccupied", room.getIsOccupied());

            // Add unavailable dates
            JSONArray unavailableDates = new JSONArray();

            roomObject.put("unavailableDates", unavailableDates);

            roomsArray.add(roomObject);
        }

        jsonObject.put("rooms", roomsArray);

        try (FileWriter fileWriter = new FileWriter(RESERVEROOM_FILE)) {
            fileWriter.write(jsonObject.toJSONString());
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void UpdateJsonBookingDates(ArrayList<TransectionRoom> listBookRoom, LocalDate startDate,
            LocalDate endDate) {
        // อ่านข้อมูลห้องทั้งหมดจากไฟล์ JSON
        JSONArray readReserveRoom = readJsonBooking();

        // วนลูปตรวจสอบห้องที่ต้องการอัปเดต
        for (TransectionRoom room : listBookRoom) {
            int targetRoomNumber = room.getRoom().getRoomNumber();

            // วนลูปใน JSONArray เพื่อหาห้องที่มีเลขห้องตรงกับ room ที่ต้องการอัปเดต
            for (int i = 0; i < readReserveRoom.size(); i++) {
                JSONObject existingRoom = (JSONObject) readReserveRoom.get(i);
                int existingRoomNumber = ((Long) existingRoom.get("roomNumber")).intValue(); // แปลงเป็น int

                // ถ้าเลขห้องตรงกัน ให้ทำการอัปเดต
                if (existingRoomNumber == targetRoomNumber) {
                    // อัปเดต unavailableDates
                    JSONArray unavailableDates = (JSONArray) existingRoom.get("unavailableDates");

                    // เพิ่มวันที่ใหม่ในช่วงที่จอง
                    LocalDate currentDate = startDate;
                    while (!currentDate.isAfter(endDate)) {
                        String dateString = currentDate.toString(); // แปลง LocalDate เป็น String
                        if (!unavailableDates.contains(dateString)) { // ตรวจสอบว่ามีวันที่นี้อยู่แล้วหรือไม่
                            unavailableDates.add(dateString); // เพิ่มวันที่ใหม่
                        }
                        currentDate = currentDate.plusDays(1);
                    }

                    // อัปเดตข้อมูลกลับใน JSONArray
                    existingRoom.put("unavailableDates", unavailableDates);
                    readReserveRoom.set(i, existingRoom); // แก้ไขค่าใน JSONArray
                    break; // เมื่อเจอห้องแล้วก็ไม่ต้องวนลูปต่อ
                }
            }
        }

        // เขียนข้อมูล JSON ที่อัปเดตกลับลงไฟล์
        try (FileWriter fileWriter = new FileWriter(RESERVEROOM_FILE)) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("rooms", readReserveRoom);
            fileWriter.write(jsonObject.toJSONString());
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public JSONArray readJsonBooking() {
        JSONParser jsonParser = new JSONParser();
        JSONArray roomsArray = new JSONArray(); // สร้าง JSONArray เพื่อเก็บข้อมูลห้อง
        File reserveRoomfile = new File(RESERVEROOM_FILE);

        if (reserveRoomfile.length() == 0) {
            return null;
        }

        try (FileReader reader = new FileReader(RESERVEROOM_FILE)) {

            Object obj = jsonParser.parse(reader); // ParseException อาจเกิดขึ้นที่นี่
            JSONObject jsonObject = (JSONObject) obj;

            roomsArray = (JSONArray) jsonObject.get("rooms");

            // ตรวจสอบห้องทั้งหมดจาก JSON
            for (Object roomObj : roomsArray) {
                JSONObject roomJson = (JSONObject) roomObj;
                String type = (String) roomJson.get("roomType");
                Double price = (Double) roomJson.get("price");

                TransectionRoom transectionRoom = new TransectionRoom();
                transectionRoom.getRoom().setRoomNumber(((Long) roomJson.get("roomNumber")).intValue());
                transectionRoom.getRoom().setType(type);
                transectionRoom.getRoom().setPrice(price);

                // อ่านวันที่ไม่ว่าง
                JSONArray unavailableDates = (JSONArray) roomJson.get("unavailableDates");
                for (Object dateObj : unavailableDates) {
                    LocalDate date = LocalDate.parse((String) dateObj);
                    transectionRoom.setAvailability(date, false);
                }

                // เพิ่ม TransectionRoom ที่สร้างไปยัง transectionRooms
                transectionRooms.add(transectionRoom); // เพิ่มเข้าที่นี่แทน
            }
        } catch (IOException e) {
            System.out.println("Error reading the file: " + e.getMessage());
            e.printStackTrace();
        } catch (ParseException e) {
            System.out.println("Error parsing JSON data: " + e.getMessage());
            e.printStackTrace();
        }

        return roomsArray; // คืนค่าข้อมูลห้องทั้งหมด
    }

    public void printInfo() {
        System.out.println("Reserved Room Information:");

        for (TransectionRoom room : transectionRooms) {
            System.out.println("Room Number: " + room.getRoom().getRoomNumber());
            System.out.println("Room Type: " + room.getRoom().getType());
            System.out.println("Price per Night: " + room.getRoom().getPrice());

            System.out.print("Unavailable Dates: ");
            for (Entry<LocalDate, Boolean> date : room.getAvailable().entrySet()) {
                if (!date.getValue()) {
                    System.out.print(date.getKey() + " ");
                }
            }
            System.out.println("--------------------------");

            for (TransectionRoom tsRoom : transectionRooms) {
                Map<LocalDate, Boolean> availabilityMap = tsRoom.getAvailable(); // ดึงแผนที่การว่าง

                // วนลูปผ่านแต่ละวันในแผนที่
                for (Map.Entry<LocalDate, Boolean> entry : availabilityMap.entrySet()) {
                    LocalDate date = entry.getKey(); // วันที่
                    Boolean isAvailable = entry.getValue(); // สถานะการว่าง

                    // ทำการแสดงผลหรือใช้งานตามต้องการ
                    System.out.println("Room available on " + date + ": " + isAvailable);
                }
            }

            System.out.println(); // For new line after each room's info
        }
    }

}