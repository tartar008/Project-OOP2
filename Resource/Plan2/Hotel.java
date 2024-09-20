import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Hotel {
    private List<Room> rooms = new ArrayList<>();

    

    public Hotel() {
        // Add rooms to the hotel
        addRooms("Standard", 5);
        addRooms("Superior", 3);
        addRooms("Family", 2);
        addRooms("Honeymoon", 2);
    }

    private void addRooms(String type, int count) {
        for (int i = 1; i <= count; i++) {
            rooms.add(new Room(type, rooms.size() + 1));
        }
    }

    public List<Room> getAvailableRooms(LocalDate startDate, LocalDate endDate) {
        List<Room> availableRooms = new ArrayList<>();
        for (Room room : rooms) {
            boolean isAvailable = true;
            for (LocalDate date = startDate; date.isBefore(endDate) || date.equals(endDate); date = date.plusDays(1)) {
                if (!room.isAvailable(date)) {
                    isAvailable = false;
                    break;
                }
            }
            if (isAvailable) {
                availableRooms.add(room);
            }
        }
        return availableRooms;
    }

    // เมธอดสำหรับคำนวณวันที่ที่ลูกค้าสามารถพักได้สูงสุด
    public LocalDate getMaxStayDate(LocalDate checkInDate) {
        LocalDate currentDate = checkInDate;
        while (true) {
            boolean allRoomsAvailable = true;
            for (Room room : rooms) {
                if (!room.isAvailable(currentDate)) {
                    allRoomsAvailable = false;
                    break;
                }
            }
            if (!allRoomsAvailable) {
                return currentDate.minusDays(1); // วันที่สุดท้ายที่สามารถพักได้คือก่อนวันที่เต็ม
            }
            currentDate = currentDate.plusDays(1);
        }
    }

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
