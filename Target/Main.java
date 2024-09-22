import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        ArrayList<Room> rooms = SETROOM();

        ReserveRoom reserveRoom = SETRESERVEROOM(rooms);

        System.out.println("\nWelcome to the Hotel Booking System!");
        System.out.println("Choose your role:");
        System.out.println("1. User");
        System.out.println("2. Employee [ll]");
        System.out.println("3. Exit \t [log]");
        int ChooseRole = scanner.nextInt();

        if (ChooseRole == 1) {
            User(reserveRoom);

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

    public static ReserveRoom SETRESERVEROOM(ArrayList<Room> rooms) {
        ReserveRoom reserveRooms = new ReserveRoom(null, 0)

        for(Room runRoom : rooms){
            reserveRooms.setRooms(rooms);
        }

        //ให้มีการวนเพื่อนำ room มาเข้า reserveRoom ก่อน

        return reserveRooms;
    }

    public static void User(ArrayList<ReserveRoom> reserveRoom) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Choose your booking method:");
        System.out.println("1. Walk-in");
        System.out.println("2. Online");
        System.out.println("3. Exit");
        int choice = scanner.nextInt();

        //สร้าง customer
        // Customer customer = new Customer(); // เติม Constractor เพิ่ม



        if (choice == 1) {
            handleWalkInBooking(reserveRoom);

        } else if (choice == 2) {
            handleOnlineBooking(reserveRoom);
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


        reserveRoom.displayCalendar(roomAvailability);

        System.out.println("Enter check-in date : ");
        int startDay = scanner.nextInt();
        System.out.println("Enter check-out date : ");
        int EndDay = scanner.nextInt();

        //เลือกจำนวนห้อง
    }


}

