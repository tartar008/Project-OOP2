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
            handleWalkInBooking(reserveRoom, customer1);
        } else if (choice == 2) {
            handleOnlineBooking(reserveRoom, customer1);
        } else if (choice == 3) {

        } else {

        }
    }

    private static void handleOnlineBooking(ReserveRoom reserveRooms, Customer customer1) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(ANSI_GREEN + "You chose Online booking." + ANSI_RESET);

        // สมมติแสดงปฏิทิน (ใช้เป็น boolean array สำหรับความง่าย)
        // boolean[] roomAvailability = new boolean[30];
        // for (int i = 0; i < 30; i++) {
        //     roomAvailability[i] = true; // Assume all rooms are available
        // }

        reserveRooms.displayCalendar();

        // รับวันที่เช็คอินและเช็คเอาท์จากผู้ใช้
        System.out.print("Enter check-in date (day): " + ANSI_GREEN + "3\n" + ANSI_RESET);
        int startDay = 3;
        System.out.print("Enter check-out date (day): " + ANSI_GREEN + "7\n" + ANSI_RESET);
        int endDay = 7;

        // ======================================================================================================

        // สร้าง LocalDate สำหรับวันที่เช็คอินและเช็คเอาท์
        LocalDate checkInDate = LocalDate.of(2024, 9, startDay);
        LocalDate checkOutDate = LocalDate.of(2024, 9, endDay);

        // ตรวจสอบห้องที่ว่างตามจำนวนวันแล้วเก็บเข้า availableRooms
        List<TransectionRoom> availableRooms = reserveRooms.getAvailableRooms(checkInDate, checkOutDate);
        
        System.out.println("....Start Debug....");
        System.out.print("AvailableRooms : " + ANSI_GREEN + "[ ");
        for (TransectionRoom TSroom : availableRooms) {
            System.out.print(ANSI_GREEN + TSroom.getRoom().getRoomNumber() + ", " + ANSI_RESET);

        }
        System.out.println(ANSI_GREEN + "]" + ANSI_RESET);
        System.out.println("....End Debug....");

        // ตรวจสอบห้องที่ว่างใน availableRooms
        // for (TransectionRoom TSroom : availableRooms) {
        //     if (TSroom.isAvailable(checkInDate)) {
                
        //         System.out.println(ANSI_GREEN + "Room " + TSroom.getRoom().getRoomNumber() + " is available today." + ANSI_RESET);
        //     } else {
        //         System.out.println(ANSI_RED + "Room " + TSroom.getRoom().getRoomNumber() + " is not available today." + ANSI_RESET);
        //     }
        // }

        // ======================================================================================================
        // คัดกรองให้เลือกเป็นประเภทห้อง

        int type1Count = 0; // ห้องประเภท 100-199
        int type2Count = 0; // ห้องประเภท 200-299
        int type3Count = 0; // ห้องประเภท 300-399
        System.out.println(ANSI_YELLOW + "> Available Type Room" + ANSI_RESET );
        for (TransectionRoom tsRoom : availableRooms) {
            int roomNumber = tsRoom.getRoom().getRoomNumber();

            if (tsRoom.getRoom().getRoomNumber()>=101 && tsRoom.getRoom().getRoomNumber()<=105) {
                type1Count++; // ห้องประเภท 100-199
            } else if (tsRoom.getRoom().getRoomNumber()>=201 && tsRoom.getRoom().getRoomNumber()<=205) {
                type2Count++; // ห้องประเภท 200-299
            } else if (tsRoom.getRoom().getRoomNumber()>=301 && tsRoom.getRoom().getRoomNumber()<=305) {
                type3Count++; // ห้องประเภท 300-399
            }
        }

        System.out.println("  Standard Room" + ANSI_RED + " ( " + "Last " + type1Count + " Rooms )" + ANSI_RESET + ", THB 1,728");
        System.out.println("  Family Room" + ANSI_RED + " ( " + "Last " + type2Count + " Rooms )" + ANSI_RESET + ", THB 3690");
        System.out.println("  Honeymoon Suite" + ANSI_RED + " ( " + "Last " + type3Count + " Rooms )" + ANSI_RESET + ", THB 5,364");
        


        // สร้าง HashMap เก็บชื่อประเภทห้องและจำนวนห้องที่เหลือ
        HashMap<String, Integer> TypeRoomRemaining = new HashMap<>();

        if (type1Count > 0) {
            TypeRoomRemaining.put("Standard", type1Count); // ประเภทห้อง 101-105
        }
        if (type2Count > 0) {
            TypeRoomRemaining.put("family", type2Count); // ประเภทห้อง 201-205
        }
        if (type3Count > 0) {
            TypeRoomRemaining.put("honeymoon", type3Count); // ประเภทห้อง 301-305
        }

        // แสดงข้อมูลประเภทห้องและจำนวนห้องที่เหลือ
        // if (TypeRoomRemaining.isEmpty()) {
        //     System.out.println("Sorry, no rooms are available for the selected dates.");
        // } else {
        //     System.out.println("Available rooms:");
        //     for (Map.Entry<String, Integer> entry : TypeRoomRemaining.entrySet()) {
        //         System.out.println("Room Type: " + entry.getKey() + ", Remaining: " + entry.getValue());
        //     }
        // }


        //เลือกประเภทห้องที่ต้องการ

        System.out.print("Enter your Type Room: "+ ANSI_GREEN + "1\n" + ANSI_RESET);
        int ChooseTypeRoom1 = 1;
        System.out.print("Enter number of room(s): "+ ANSI_GREEN + "2\n" + ANSI_RESET);
        int numOfRoom1 = 1;
        System.out.print("Complete Booking(Y/N): "+ ANSI_GREEN + "N\n" + ANSI_RESET);

        System.out.print("---\nEnter your Type Room: "+ ANSI_GREEN + "1\n" + ANSI_RESET);
        int ChooseTypeRoom2 = 2;
        System.out.print("Enter number of room(s): "+ ANSI_GREEN + "2\n" + ANSI_RESET);
        int numOfRoom2 = 1;
        System.out.print("Complete Booking(Y/N): "+ ANSI_GREEN + "Y\n" + ANSI_RESET);
        
        System.out.print(ANSI_BLUE + "--\nComfirm Booking(Y/N): "+ ANSI_GREEN + "Y\n" + ANSI_RESET); // N = cencle booking
        
        // สรุปรายการที่ลูกค้าเลือก
        System.out.println(ANSI_YELLOW + "> Booking Details" + ANSI_RESET);
        
        //ชำระเงิน

        //สร้างใบเสร็จ

        //ทำให้ห้องพักที่ถูกเลือกมีค่าเป็น false
        
        
    }

    private static void handleWalkInBooking(ReserveRoom reserveRooms, Customer customer) {
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
            rooms.add(new MasterRoom(101, "Standard Room", 1728.0));
            rooms.add(new MasterRoom(102, "Standard Room", 1728.0));
            rooms.add(new MasterRoom(103, "Standard Room", 1728.0));
            rooms.add(new MasterRoom(104, "Standard Room", 1728.0));
            rooms.add(new MasterRoom(105, "Standard Room", 1728.0));

            rooms.add(new MasterRoom(201, "Family Room", 3960.0));
            rooms.add(new MasterRoom(202, "Family Room", 3960.0));
            rooms.add(new MasterRoom(203, "Family Room", 3960.0));
            rooms.add(new MasterRoom(204, "Family Room", 3960.0));
            rooms.add(new MasterRoom(205, "Family Room", 3960.0));

            rooms.add(new MasterRoom(301, "Honeymoon Suite", 5364.0));
            rooms.add(new MasterRoom(302, "Honeymoon Suite", 5364.0));
            rooms.add(new MasterRoom(303, "Honeymoon Suite", 5364.0));
            rooms.add(new MasterRoom(304, "Honeymoon Suite", 5364.0));
            rooms.add(new MasterRoom(305, "Honeymoon Suite", 5364.0));
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
            //handleWalkInBooking(null);
        } else if (employeeRole == 2) {

        } else if (employeeRole == 3) {
            main(null);
        }
    }
}
