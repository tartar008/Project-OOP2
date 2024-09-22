import java.util.ArrayList;
import java.util.Date;

public class ReserveRoom {
    private ArrayList<Room> rooms;
    private ArrayList<Booking> bookings;

    public ReserveRoom() {
        rooms = new ArrayList<>();
        bookings = new ArrayList<>();
    }

    public void addRoom(Room room) {
        rooms.add(room);
    }

    public void makeBooking(Customer customer, Room room, Date checkIn, Date checkOut) {
        if (room.isAvailable()) {
            Booking booking = new Booking(customer, room, checkIn, checkOut);
            bookings.add(booking);
            System.out.println("Booking successful!");
        } else {
            System.out.println("Room is not available.");
        }
    }

    public void showAvailableRooms() {
        for (Room room : rooms) {
            if (room.isAvailable()) {
                System.out.println(room);
            }
        }
    }

    public void showAllBookings() {
        for (Booking booking : bookings) {
            System.out.println(booking);
        }
    }
}
