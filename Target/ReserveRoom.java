import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ReserveRoom  {
    private ArrayList<Room> rooms;
    private Map<LocalDate, Boolean> availability = new HashMap<>();

    public ReserveRoom() {
        rooms = new ArrayList<>();
        availability = new HashMap<>();
    }


    public ArrayList<Room> getRooms() {
        return rooms;
    }


    public void setRooms(Room room) {
        this.rooms.add(room);
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
