import java.util.ArrayList;
import java.util.List;

public class Hotel {
    private List<Room> rooms;

    public Hotel() {
        rooms = new ArrayList<>();
        // เพิ่มห้องตัวอย่าง
        rooms.add(new Room(101, "Single", 100.0));
        rooms.add(new Room(102, "Double", 150.0));
        rooms.add(new Room(103, "Suite", 250.0));
    }

    public void displayRooms() {
        for (Room room : rooms) {
            System.out.println(room.getRoomInfo());
        }
    }

    public boolean bookRoom(int roomNumber, Customer customer) {
        for (Room room : rooms) {
            if (roomNumber == room.getRoomNumber() && !room.isBooked()) { // ใช้ getter
                room.bookRoom();
                System.out.println("Room " + roomNumber + " booked successfully for " + customer.getName());
                return true;
            }
        }
        System.out.println("Room " + roomNumber + " is already booked or does not exist.");
        return false;
    }

    public void cancelRoom(int roomNumber) {
        for (Room room : rooms) {
            if (roomNumber == room.getRoomNumber() && room.isBooked()) { // ใช้ getter
                room.cancelBooking();
                System.out.println("Room " + roomNumber + " booking canceled.");
                return;
            }
        }
        System.out.println("Room " + roomNumber + " is not booked or does not exist.");
    }
}