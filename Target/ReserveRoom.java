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
    
    

    //แสดงปฎิทิน
    public void displayCalendar(boolean[] availability) {
        String[][] calendar = new String[5][7]; // Simplified for a 30-day month
        int day = 1;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 7; j++) {
                if (day <= 30) {
                    if (availability[day - 1]) {
                        calendar[i][j] = String.format("%02d", day); // Available day
                    } else {
                        calendar[i][j] = "XX"; // Booked day
                    }
                    day++;
                } else {
                    calendar[i][j] = "  ";
                }
            }
        }

        // Print calendar
        System.out.println("Mon Tue Wed Thu Fri Sat Sun");
        for (String[] week : calendar) {
            for (String dayStr : week) {
                System.out.print(dayStr + "  ");
            }
            System.out.println();
        }
    }
}
