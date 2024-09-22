import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

class Room {
    private String type;
    private int roomNumber;
    private Map<LocalDate, Boolean> availability = new HashMap<>();

    public Room(String type, int roomNumber) {
        this.type = type;
        this.roomNumber = roomNumber;
    }

    public String getType() {
        return type;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public boolean isAvailable(LocalDate date) {
        return availability.getOrDefault(date, true);  // true = available
    }

    public void book(LocalDate startDate, LocalDate endDate) {
        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            availability.put(date, false);  // ทำให้ห้องไม่ว่าง
        }
    }
}