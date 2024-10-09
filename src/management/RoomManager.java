package src.management;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import src.rooms.MasterRoom;

import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RoomManager {
    private static final String ROOM_FILE = "./resources/rooms.json";
    private ArrayList<MasterRoom> rooms;

    public RoomManager() {
        rooms = new ArrayList<>();
        loadRooms();
        if(rooms.isEmpty()){
            addDefaultRooms();
        }
    }

    public void loadRooms() {
        JSONParser parser = new JSONParser();
        try(FileReader reader = new FileReader(ROOM_FILE)) {
            JSONArray roomArray = (JSONArray) parser.parse(reader);
            for(Object obj : roomArray){
                JSONObject roomObject = (JSONObject) obj;
                int roomNumber = ((Long) roomObject.get("roomNumber")).intValue();
                String type = (String) roomObject.get("type");
                double price = ((Number) roomObject.get("price")).doubleValue();

                MasterRoom room = new MasterRoom(roomNumber, type, price);
                rooms.add(room);
            }
            System.out.println("rooms size: " +rooms.size());
        }
        catch(IOException | ParseException e){
            e.printStackTrace();
        }
    }
    
    
    public ArrayList<MasterRoom> getRooms() {
        return rooms;
    }
    
    public void saveRooms(){
        JSONArray roomArray = new JSONArray();
        for (MasterRoom room : rooms){
            JSONObject roomObject = new JSONObject();
            roomObject.put("roomNumber", room.getRoomNumber());
            roomObject.put("type", room.getType());
            roomObject.put("price", room.getPrice());

            roomArray.add(roomObject);
        }
        try(FileWriter file = new FileWriter(ROOM_FILE)) {
            file.write(roomArray.toJSONString());
            file.flush();

        } catch (IOException e){
            e.printStackTrace();;
        }
    }
    public void addDefaultRooms(){
        if(rooms.isEmpty()){
            rooms.add(new MasterRoom(101, "Standard", 1000));
            rooms.add(new MasterRoom(102, "Standard", 1000));
            rooms.add(new MasterRoom(103, "Standard", 1000));
            rooms.add(new MasterRoom(104, "Standard", 1000));
            rooms.add(new MasterRoom(105, "Standard", 1000));

            rooms.add(new MasterRoom(201, "Family", 2000));
            rooms.add(new MasterRoom(202, "Family", 2000));
            rooms.add(new MasterRoom(203, "Family", 2000));

            rooms.add(new MasterRoom(301, "Honeymoon", 3000));
            rooms.add(new MasterRoom(302, "Honeymoon", 3000));
            saveRooms();
        }
    }
   
}
