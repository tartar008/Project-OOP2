import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ReserveRoom  {
    private ArrayList<Room> rooms;
    private Map<LocalDate, Boolean> availability = new HashMap<>();

    public ReserveRoom(String type, int roomNumber) {
        this.type = type;
        this.roomNumber = roomNumber;
    }


    public int getRoomNumber() {
        return roomNumber;
    }

    public ArrayList<Room> getRooms() {
        return rooms;
    }


    public void setRooms(ArrayList<Room> rooms) {
        this.rooms = rooms;
    }

    public boolean isAvailable(LocalDate date) {
        return availability.getOrDefault(date, true);  // true = available
    }

    public void book(LocalDate startDate, LocalDate endDate) {
        for (LocalDate date = startDate; date.isBefore(endDate) || date.equals(endDate); date = date.plusDays(1)) {
            availability.put(date, false);
        }
    }
    
    

    public void displayCalendar(boolean[] availability) {}
}
