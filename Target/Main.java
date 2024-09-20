import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        ArrayList<Room> rooms = SETALL();

        

        System.out.println("\nWelcome to the Hotel Booking System!");
        System.out.println("Choose your role:");
        System.out.println("1. User");
        System.out.println("2. Employee [log]");
        System.out.println("3. Exit \t [log]");
        int ChooseRole = scanner.nextInt();

        if (ChooseRole == 1) {
            User();

        } else if (ChooseRole == 2) {
            Employee();

        } else {
            main(args);
        }
    }

    public static ArrayList<Room> SETALL() {
        // ArrayList<Room> rooms = Room.loadRoomsFromJson("rooms.json");
        ArrayList<Room> rooms = new ArrayList<>();

        if (rooms.isEmpty()) {
            rooms.add(new Room("101", "Standard", 1000));
            rooms.add(new Room("102", "Deluxe", 2000));
            rooms.add(new Room("103", "Suite", 3000));
            // Room.saveRoomsToJson(rooms, "rooms.json");
        }

        return rooms;
    }

    public static void User() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Choose your booking method:");
        System.out.println("1. Walk-in");
        System.out.println("2. Online");
        System.out.println("3. Exit");
        int choice = scanner.nextInt();

        if (choice == 1) {

        } else if (choice == 2) {

        } else if (choice == 3) {

        } else {

        }
    }

    public static void Employee() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Choose your role:");
        System.out.println("1. receptionist  [log]");
        System.out.println("2. Manager\t [log]");
        System.out.println("3. Exit \t [log]");
        int ChooseRole = scanner.nextInt();

        if (ChooseRole == 1) {

        } else if (ChooseRole == 2) {

        } else {
            main(null);
        }
    }

}
