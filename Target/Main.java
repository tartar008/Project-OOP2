import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
<<<<<<< HEAD
        
        ArrayList<Room> rooms = SETROOM();

        ArrayList<ReserveRoom> reserveRoom = SETRESERVEROOM(rooms);
=======

        ArrayList<Room> rooms = SETALL();
>>>>>>> c6642ca4976c53ccbbb710921116186a3830d5ff

        System.out.println("\nWelcome to the Hotel Booking System!");
        System.out.println("Choose your role:");
        System.out.println("1. User");
        System.out.println("2. Employee [ll]");
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

    public static ArrayList<Room> SETROOM() {
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

    public static ArrayList<ReserveRoom> SETRESERVEROOM(ArrayList<Room> rooms) {
        ArrayList<ReserveRoom> reserveRooms = new ArrayList<>();


        //ให้มีการวนเพื่อนำ room มาเข้า reserveRoom ก่อน

        return reserveRooms;
    }

    public static void User() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Choose your booking method:");
        System.out.println("1. Walk-in");
        System.out.println("2. Online");
        System.out.println("3. Exit");
        int choice = scanner.nextInt();

        //สร้าง customer
        // Customer customer = new Customer(); // เติม Constractor เพิ่ม



        if (choice == 1) {
            handleWalkInBooking();

        } else if (choice == 2) {
            handleOnlineBooking();
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

    private static void handleWalkInBooking() {


        //เลือกวันจอง 

        //เลือกจำนวนห้อง

    }

    private static void handleOnlineBooking() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("You chose Online booking.");

        //เลือกวันจอง 

        // Display calendar (simplified as boolean array)
        boolean[] roomAvailability = new boolean[30];
        for (int i = 0; i < 30; i++) {
            roomAvailability[i] = true; // Assume all rooms are available
        }

        hotel.displayCalendar(roomAvailability);

        //เลือกจำนวนห้อง
    }


}

