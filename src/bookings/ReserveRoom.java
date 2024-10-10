package src.bookings;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import src.rooms.TransectionRoom;

public class ReserveRoom {
    private ArrayList<TransectionRoom> transectionRooms;

    public ReserveRoom() {
        this.transectionRooms = new ArrayList<>();
    }

    public void AddTransectionRoom(TransectionRoom transectionRoom) {
        transectionRooms.add(transectionRoom);
    }

    public ArrayList<TransectionRoom> getTransectionRoom() {
        return transectionRooms;
    }

    // คืนค่าห้องพักทั้งหมดที่ว่างทุกประเภทห้อง
    public List<TransectionRoom> getAvailableRooms(LocalDate startDate, LocalDate endDate) {
        List<TransectionRoom> availableRooms = new ArrayList<>();
        for (TransectionRoom room : transectionRooms) {
            if (room.bookRoom(startDate, endDate)) {
                availableRooms.add(room);
            }
        }
        return availableRooms;
    }

    // คืนค่าห้องพักทั้งหมดที่ว่างตามประเภทห้องที่เลือก
    public List<TransectionRoom> getAvailableRoomsByType(String roomType, LocalDate checkInDate, LocalDate checkOutDate) {
        List<TransectionRoom> availableRooms = new ArrayList<>();
        for (TransectionRoom room : transectionRooms) {
            if (room.getRoom().getType().equals(roomType) && room.bookRoom(checkInDate, checkOutDate)) {
                availableRooms.add(room);
            }
        }
        return availableRooms;
    }

    // บันทึกข้อมูลห้องจองลงไฟล์ JSON
    @SuppressWarnings("unchecked")
    public void WriteJsonBooking(String filePath) {
        JSONObject jsonObject = new JSONObject();
        JSONArray roomsArray = new JSONArray();

        for (TransectionRoom room : transectionRooms) {
            JSONObject roomObject = new JSONObject();
            roomObject.put("roomNumber", room.getRoom().getRoomNumber());
            roomObject.put("type", room.getRoom().getType());
            roomObject.put("price", room.getRoom().getPrice());

            // Add unavailable dates
            JSONArray unavailableDates = new JSONArray();
            for (Entry<LocalDate, Boolean> date : room.getAvailable().entrySet()) {
                unavailableDates.add(date.getKey().toString());
            }
            
            roomObject.put("unavailableDates", unavailableDates);

            roomsArray.add(roomObject);
        }

        jsonObject.put("rooms", roomsArray);

        try (FileWriter fileWriter = new FileWriter(filePath)) {
            fileWriter.write(jsonObject.toJSONString());
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readJsonBooking(String filePath) throws org.json.simple.parser.ParseException {
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader(filePath)) {
            Object obj = jsonParser.parse(reader);
            JSONObject jsonObject = (JSONObject) obj;

            JSONArray roomsArray = (JSONArray) jsonObject.get("rooms");

            for (Object roomObj : roomsArray) {
                JSONObject roomJson = (JSONObject) roomObj;
                String type = (String) roomJson.get("type");
                Double price = (Double) roomJson.get("price");

                TransectionRoom transectionRoom = new TransectionRoom();
                transectionRoom.getRoom().setRoomNumber(((Long) roomJson.get("roomNumber")).intValue());
                transectionRoom.getRoom().setType(type);
                transectionRoom.getRoom().setPrice(price);

                JSONArray unavailableDates = (JSONArray) roomJson.get("unavailableDates");
                for (Object dateObj : unavailableDates) {
                    LocalDate date = LocalDate.parse((String) dateObj);
                    transectionRoom.setAvailability(date, false);
                }

                AddTransectionRoom(transectionRoom);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
