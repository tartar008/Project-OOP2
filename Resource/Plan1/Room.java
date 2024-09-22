import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Room {
    private String roomNumber;
    private String type;
    private double price;
    private boolean available;

    public Room(String roomNumber, String type, double price) {
        this.roomNumber = roomNumber;
        this.type = type;
        this.price = price;
        this.available = true;  // ตั้งค่าให้ห้องว่างเริ่มต้น
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public String getType() {
        return type;
    }

    public double getPrice() {
        return price;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    // บันทึกข้อมูลห้องลงไฟล์ JSON
    public static void saveRoomsToJson(ArrayList<MasterRoom> rooms, String filename) {
        JSONArray roomList = new JSONArray();

        for (MasterRoom room : rooms) {
            JSONObject roomDetails = new JSONObject();
            roomDetails.put("roomNumber", room.getRoomNumber());
            roomDetails.put("type", room.getType());
            roomDetails.put("price", room.getPrice());
            roomDetails.put("available", room.isAvailable());

            roomList.add(roomDetails);
        }

        try (FileWriter file = new FileWriter(filename)) {
            file.write(roomList.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // โหลดข้อมูลห้องจากไฟล์ JSON
    public static ArrayList<MasterRoom> loadRoomsFromJson(String filename) {
        ArrayList<MasterRoom> rooms = new ArrayList<>();
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader(filename)) {
            Object obj = jsonParser.parse(reader);
            JSONArray roomList = (JSONArray) obj;

            for (Object roomObject : roomList) {
                if (roomObject instanceof JSONObject) {
                    parseRoomObject((JSONObject) roomObject, rooms);
                }
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        return rooms;
    }

    private static void parseRoomObject(JSONObject room, ArrayList<MasterRoom> rooms) {
        String roomNumber = (String) room.get("roomNumber");
        String type = (String) room.get("type");
        double price = ((Number) room.get("price")).doubleValue(); // ใช้ Number เพื่อให้มั่นใจในการแปลง
        boolean available = (boolean) room.get("available");

        rooms.add(new MasterRoom(roomNumber, type, price));
    }

    @Override
    public String toString() {
        return "Room [roomNumber=" + roomNumber + ", type=" + type + ", price=" + price + ", available=" + available + "]";
    }
}
