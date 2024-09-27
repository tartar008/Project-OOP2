import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;

public class UnitTest {
    // ANSI escape codes for colors
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public static void main(String[] args) {
        // Scanner scanner = new Scanner(System.in);
        ArrayList<MasterRoom> rooms = SETROOM();
        ReserveRoom reserveRoom = new ReserveRoom();

        for (MasterRoom Room : rooms) {
            Room.printInfo();
        }
        for (MasterRoom runRoom : rooms) {
            TransectionRoom transectionRoom = new TransectionRoom(runRoom);
            reserveRoom.AddTransectionRoom(transectionRoom);
        }
        System.out.println("===========================================================");
        for (TransectionRoom runTSRoom : reserveRoom.getTransectionRoom()) {
            runTSRoom.displayRoomInfo();
        }

        // เข้าสู่ Hotel

        System.out.println("\nWelcome to the Hotel Booking System!");
        System.out.println("Choose your role:");
        System.out.println("1. Customer");
        System.out.println("2. Employee\t [log]");
        System.out.println("3. Exit \t [log]");
        System.out.print(ANSI_GREEN + ">>> 1\n" + ANSI_RESET);
        int ChooseRole = 1;

        if (ChooseRole == 1) {
            User(reserveRoom);

        }
        // else if (ChooseRole == 2) {
        // Employee();

        // } else {
        // main(args);
        // }
    }

    // =====================================================================================
    public static void User(ReserveRoom reserveRoom) {
        Scanner scanner = new Scanner(System.in);
        // สร้าง customer
        System.out.println(ANSI_GREEN + "[ Customer ]" + ANSI_RESET);
        System.out.print("Enter Firstname: " + ANSI_GREEN + "Piyachai\n" + ANSI_RESET);
        String firstName = "Piyachai";
        System.out.print("Enter Lastname: " + ANSI_GREEN + "Narongsab\n" + ANSI_RESET);
        String lastName = "Narongsab";
        System.out.print("Enter Phone number: " + ANSI_GREEN + "081XXXXXXX\n" + ANSI_RESET);
        String phoneNumber = "081XXXXXXX";
        System.out.print("Enter Email: " + ANSI_GREEN + "tartar0081@gmail.com\n" + ANSI_RESET);
        String email = "tartar0081@gmail.com";

        Customer customer1 = new Customer(firstName, lastName, phoneNumber, email);
        // customer1.customerInfo();
        System.out.println("\nWelcome khun " + ANSI_GREEN + firstName + ANSI_RESET);
        System.out.println();
        System.out.println("Choose your booking method:");
        System.out.println("1. Walk-in");
        System.out.println("2. Online");
        System.out.println("3. Exit");
        System.out.print("Enter your method: " + ANSI_GREEN + "2\n" + ANSI_RESET);
        int choice = 2;

        // Customer customer = new Customer(); // เติม Constractor เพิ่ม

        if (choice == 1) {
            System.out.print(">> WALK IN <<");
            handleWalkInBooking(reserveRoom);
        } else if (choice == 2) {
            handleOnlineBooking(reserveRoom);
        } else if (choice == 3) {

        } else {

        }
    }

    private static void handleOnlineBooking(ReserveRoom reserveRooms) {
        Scanner scanner = new Scanner(System.in);

        System.out.println(ANSI_GREEN + "You chose Online booking." + ANSI_RESET);

        // แสดงปฏิทิน
        boolean[] roomAvailability = new boolean[30];
        for (int i = 0; i < 30; i++) {
            roomAvailability[i] = true; // Assume all rooms are available
        }

        reserveRooms.displayCalendar();

        // รับวันที่เช็คอินและเช็คเอาท์จากผู้ใช้
        System.out.println();
        System.out.print("Enter check-in date (day): " + ANSI_GREEN + "3\n" + ANSI_RESET);
        int startDay = 3;

        System.out.print("Enter check-out date (day): " + ANSI_GREEN + "7\n" + ANSI_RESET);
        int endDay = 7;

        // สร้าง LocalDate สำหรับวันที่เช็คอินและเช็คเอาท์
        LocalDate checkInDate = LocalDate.of(2024, 9, startDay);
        LocalDate checkOutDate = LocalDate.of(2024, 9, endDay);

        // ตรวจสอบห้องว่าง
        List<TransectionRoom> availableRooms = reserveRooms.getAvailableRooms(checkInDate, checkOutDate);

        System.out.println("....Start Debug....");
        System.out.print("AvailableRooms : " + ANSI_GREEN + "[ ");
        for (TransectionRoom TSroom : availableRooms) {
            System.out.print(ANSI_GREEN + TSroom.getRoom().getRoomNumber() + ", " + ANSI_RESET);
        }
        System.out.println(ANSI_GREEN + "]" + ANSI_RESET);
        System.out.println("....End Debug....");

        // แสดงห้องที่ว่าง
        System.out.println();
        for (TransectionRoom TSroom : availableRooms) {
            if (TSroom.isAvailable(checkInDate)) {
                System.out.println(ANSI_GREEN + "Room " + TSroom.getRoom().getRoomNumber() + " is available." + ANSI_RESET);
            } else {
                System.out.println(ANSI_RED + "Room " + TSroom.getRoom().getRoomNumber() + " is not available." + ANSI_RESET);
            }
        }

        // คัดกรองประเภทห้อง
        System.out.println();
        int type1Count = 0;
        int type2Count = 0;
        int type3Count = 0;

        for (TransectionRoom tsRoom : availableRooms) {
            int roomNumber = tsRoom.getRoom().getRoomNumber();
            if (roomNumber >= 100 && roomNumber < 200) {
                type1Count++;
            } else if (roomNumber >= 200 && roomNumber < 300) {
                type2Count++;
            } else if (roomNumber >= 300 && roomNumber < 400) {
                type3Count++;
            }
        }

        System.out.println("Rooms in type 1 (100-199): " + ANSI_GREEN + type1Count + ANSI_RESET);
        System.out.println("Rooms in type 2 (200-299): " + ANSI_GREEN + type2Count + ANSI_RESET);
        System.out.println("Rooms in type 3 (300-399): " + ANSI_GREEN + type3Count + ANSI_RESET);

        // แสดงข้อมูลประเภทห้องที่ว่าง
        HashMap<String, Integer> TypeRoomRemaining = new HashMap<>();
        if (type1Count > 0) {
            TypeRoomRemaining.put("Standard", type1Count);
        }
        if (type2Count > 0) {
            TypeRoomRemaining.put("Paeior", type2Count);
        }
        if (type3Count > 0) {
            TypeRoomRemaining.put("Family", type3Count);
        }

        System.out.println();
        if (TypeRoomRemaining.isEmpty()) {
            System.out.println("Sorry, no rooms are available for the selected dates.");
        } else {
            System.out.println("Available rooms:");
            for (Map.Entry<String, Integer> entry : TypeRoomRemaining.entrySet()) {
                System.out.println("Room Type: " + entry.getKey() + ", Remaining: " + entry.getValue());
            }
        }

        // เลือกประเภทห้องและจำนวนห้องที่ต้องการจอง
        System.out.print("\nEnter your Type Room: ");
        int roomType = 1;
        System.out.print("Enter number of room(s): ");
        int numberOfRooms = 2;

        // สรุปการจองและชำระเงิน
        String selectedRoomType = (roomType == 1) ? "Standard" : (roomType == 2) ? "Family" : "Honeymoon";
        System.out.print("\nComplete Booking (Y/N): ");
        String completeBooking = "Y";

        double totalAmount = 0;
        if (completeBooking.equalsIgnoreCase("Y")) {
            if (selectedRoomType.equals("Standard")) {
                totalAmount = 1728 * numberOfRooms;
            } else if (selectedRoomType.equals("Family")) {
                totalAmount = 3690 * numberOfRooms;
            } else if (selectedRoomType.equals("Honeymoon")) {
                totalAmount = 5364 * numberOfRooms;
            }

            System.out.println("\n-- Payment Process --");
            System.out.println("Total Amount: THB " + totalAmount);
            System.out.print("Enter payment method (Cash/Card): ");
            String paymentMethod = "Cash";

            System.out.println("\n-- Receipt --");
            System.out.println("Customer: Piyachai Narongsab");
            System.out.println("Phone: 081XXXXXXX");
            System.out.println("Email: tartar0081@gmail.com");
            System.out.println("Total Amount: THB " + totalAmount);
            System.out.println("Payment Method: " + paymentMethod);

            System.out.println("\nBooking Completed. Thank you for your reservation!");
        } else {
            System.out.println("Booking canceled.");
        }

        //=========================================================================
        //เปลี่ยนบทบาท
        System.out.println("\n");

        System.out.println("\nWelcome to the Hotel Booking System!");
        System.out.println("Choose your role:");
        System.out.println("1. Employee\t [log]");
        System.out.println("2. Exit \t [log]");
        System.out.print(ANSI_GREEN + ">>> 1\n" + ANSI_RESET);
        int ChooseRole = 1;

        if (ChooseRole == 1) {
            //Employee().Check-in
            // Employee();
            System.out.println();
            System.out.println("Search in progress......");
            System.out.println("Found booking number!!!");
            
            
            
            //แสดงหมายเลขห้องที่ได้จากการจองไว้
            System.out.println("101 : <ชื่อคนอยู่ >");
            System.out.println("102 : <ชื่อคนอยู่ >");
            
            
            System.out.println("Successful");

        }
    }

    private static void handleWalkInBooking(ReserveRoom reserveRooms) {
        Scanner scanner = new Scanner(System.in);

        // System.out.println("Test handle walk in");
        // เลือกวันจอง
        boolean[] roomAvailability = new boolean[30];
        for (int i = 0; i < 30; i++) {
            roomAvailability[i] = true; // Assume all rooms are available
        }
        // reserveRoom.displayCalendar(roomAvailability);
        System.out.print(ANSI_GREEN + "Enter Check-In Date: " + ANSI_RESET);
        int checkInDate = scanner.nextInt();
        System.out.print(ANSI_GREEN + "Enter Check-Out Date: " + ANSI_RESET);
        int checkOutDate = scanner.nextInt();

        // เลือกจำนวนห้อง

    }

    // =====================================================================================

    public static ArrayList<MasterRoom> SETROOM() {
        // ArrayList<Room> rooms = Room.loadRoomsFromJson("rooms.json");
        ArrayList<MasterRoom> rooms = new ArrayList<>();
        // สร้างห้อง
        if (rooms.isEmpty()) {
            rooms.add(new MasterRoom(101, "Standard", 1000.0));
            rooms.add(new MasterRoom(102, "Deluxe", 2000.0));
            rooms.add(new MasterRoom(103, "Suite", 3000.0));
            // Room.saveRoomsToJson(rooms, "rooms.json");
        }

        return rooms;
    }

    public static void Employee() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Choose your role:");
        System.out.println("1. Receptionist  [log]");
        System.out.println("2. Manager\t [log]");
        System.out.println("3. Exit \t [log]");
        System.out.print("Enter your role: ");
        int employeeRole = scanner.nextInt();
        if (employeeRole == 1) {
            System.out.println(">> Receptionist <<");
            handleWalkInBooking(null);
        } else if (employeeRole == 2) {

        } else if (employeeRole == 3) {
            main(null);
        }
    }
}
