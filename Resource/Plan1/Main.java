import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Load room data from JSON file
        ArrayList<Room> rooms = Room.loadRoomsFromJson("rooms.json");

        // If no rooms in the JSON file, add sample rooms
        if (rooms.isEmpty()) {
            rooms.add(new Room("101", "Standard", 1000));
            rooms.add(new Room("102", "Deluxe", 2000));
            rooms.add(new Room("103", "Suite", 3000));
            Room.saveRoomsToJson(rooms, "rooms.json");
        }

        // Main menu
        while (true) {
            System.out.println("=== Hotel Booking System ===");
            System.out.println("1. Show all rooms");
            System.out.println("2. Filter available rooms");
            System.out.println("3. Book a room");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    // Show all rooms
                    showAllRooms(rooms);
                    break;
                case 2:
                    // Filter available rooms
                    showAvailableRooms(rooms);
                    break;
                case 3:
                    // Book a room
                    bookRoom(rooms, scanner);
                    break;
                case 4:
                    // Exit the system
                    System.out.println("Exiting...");
                    Room.saveRoomsToJson(rooms, "rooms.json"); // Save room data before exiting
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    // Function to show all rooms
    public static void showAllRooms(ArrayList<Room> rooms) {
        System.out.println("=== All Rooms ===");
        for (Room room : rooms) {
            System.out.println(room);
        }
    }

    // Function to filter available rooms
    public static void showAvailableRooms(ArrayList<Room> rooms) {
        System.out.println("=== Available Rooms ===");
        for (Room room : rooms) {
            if (room.isAvailable()) {
                System.out.println(room);
            }
        }
    }

    // Function to book a room
    public static void bookRoom(ArrayList<Room> rooms, Scanner scanner) {
        System.out.print("Please enter the room number you want to book: ");
        String roomNumber = scanner.next();

        for (Room room : rooms) {
            if (room.getRoomNumber().equals(roomNumber)) {
                if (room.isAvailable()) {
                    room.setAvailable(false);  // Set room status to booked
                    System.out.println("Room number " + roomNumber + " has been successfully booked!");
                } else {
                    System.out.println("Room number " + roomNumber + " is already booked.");
                }
                return;
            }
        }
        System.out.println("Room number " + roomNumber + " not found.");
    }
}
