import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class MainWalkIn {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Set up master rooms
        ArrayList<MasterRoom> masterRooms = setMasterRooms();

        // Create ReserveRoom object
        ReserveRoom reserveRooms = new ReserveRoom();

        for (MasterRoom masterRoom : masterRooms) {
            ReserveRoom reserveRoom = new ReserveRoom(masterRoom);
            reserveRooms.add(reserveRoom);
        }

        System.out.println("\nWelcome to the Hotel Walk-In Booking System!");
        handleWalkInBooking(reserveRooms);
    }

    // Handle walk-in booking process
    private static void handleWalkInBooking(ReserveRoom reserveRooms) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("You chose Walk-in booking.");
        
        boolean[] roomAvailability = new boolean[30]; // Simplified availability
        for (int i = 0; i < 30; i++) {
            roomAvailability[i] = true;
        }

        reserveRooms.displayCalendar(roomAvailability);

        System.out.print("Enter check-in date (day): ");
        int checkInDay = scanner.nextInt();
        System.out.print("Enter check-out date (day): ");
        int checkOutDay = scanner.nextInt();

        LocalDate checkInDate = LocalDate.of(2024, 9, checkInDay); 
        LocalDate checkOutDate = LocalDate.of(2024, 9, checkOutDay);

        // Show available rooms for the chosen dates
        reserveRooms.getAvailableRooms(checkInDate, checkOutDate);

        for (ReserveRoom reserveRoom : reserveRooms) {
            if (reserveRoom.isAvailable(checkInDate)) {
                System.out.println("Room " + reserveRoom.getRoomNumber() + " is available.");
            } else {
                System.out.println("Room " + reserveRoom.getRoomNumber() + " is not available.");
            }
        }
    }

    // Initialize master rooms
    public static ArrayList<MasterRoom> setMasterRooms() {
        ArrayList<MasterRoom> masterRooms = new ArrayList<>();
        masterRooms.add(new MasterRoom("101", "Standard", 1000));
        masterRooms.add(new MasterRoom("102", "Deluxe", 2000));
        masterRooms.add(new MasterRoom("103", "Suite", 3000));
        return masterRooms;
    }
}
