
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;

// import javax.sql.rowset.serial.SerialStruct;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        ArrayList<MasterRoom> rooms = SETROOM();

        // ReserveRoom reserveRooms = new ReserveRoom();
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
        System.out.print(">>> ");
        int ChooseRole = scanner.nextInt();

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
        System.out.println("[ Customer ]");
        System.out.print("Enter Firstname: ");
        String firstName = scanner.nextLine();
        System.out.print("Enter Lastname: ");
        String lastName = scanner.nextLine();
        System.out.print("Enter Phone number: ");
        String phoneNumber = scanner.nextLine();
        System.out.print("Enter Email: ");
        String email = scanner.nextLine();

        Customer customer1 = new Customer(firstName, lastName, phoneNumber, email);
        // customer1.customerInfo();
        System.out.println("\nWelcome khun " + firstName);
        System.out.println();
        System.out.println("Choose your booking method:");
        System.out.println("1. Walk-in");
        System.out.println("2. Online");
        System.out.println("3. Exit");
        System.out.print("Enter your method: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

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
        System.out.println("You chose Online booking.");

        // สมมติแสดงปฏิทิน (ใช้เป็น boolean array สำหรับความง่าย)
        boolean[] roomAvailability = new boolean[30];
        for (int i = 0; i < 30; i++) {
            roomAvailability[i] = true; // Assume all rooms are available
        }

        reserveRooms.displayCalendar();

        // รับวันที่เช็คอินและเช็คเอาท์จากผู้ใช้
        System.out.print("Enter check-in date (day): ");
        int startDay = scanner.nextInt();
        System.out.print("Enter check-out date (day): ");
        int endDay = scanner.nextInt();

        // ======================================================================================================

        // สร้าง LocalDate สำหรับวันที่เช็คอินและเช็คเอาท์
        LocalDate checkInDate = LocalDate.of(2024, 9, startDay);
        LocalDate checkOutDate = LocalDate.of(2024, 9, endDay);

        // ตรวจสอบห้องที่ว่างตามจำนวนวันแล้วเก็บเข้า availableRooms
        List<TransectionRoom> availableRooms = reserveRooms.getAvailableRooms(checkInDate, checkOutDate);

        System.out.println("....Start Debug....");
        System.out.print("AvailableRooms : [ ");
        for (TransectionRoom TSroom : availableRooms) {
            System.out.print(TSroom.getRoom().getRoomNumber() + ", ");

        }
        System.out.println("]");
        System.out.println("....End Debug....");

        // ตรวจสอบห้องที่ว่างใน availableRooms
        for (TransectionRoom TSroom : availableRooms) {
            if (TSroom.isAvailable(checkInDate)) {
                System.out.println("Room " + TSroom.getRoom().getRoomNumber() + " is available today.");
            } else {
                System.out.println("Room " + TSroom.getRoom().getRoomNumber() + " is not available today.");
            }
        }

        // ======================================================================================================
        // คัดกรองให้เลือกเป็นประเภทห้อง

        int type1Count = 0; // ห้องประเภท 100-199
        int type2Count = 0; // ห้องประเภท 200-299
        int type3Count = 0; // ห้องประเภท 300-399

        for (TransectionRoom tsRoom : availableRooms) {
            int roomNumber = tsRoom.getRoom().getRoomNumber();

            if (roomNumber >= 100 && roomNumber < 200) {
                type1Count++; // ห้องประเภท 100-199
            } else if (roomNumber >= 200 && roomNumber < 300) {
                type2Count++; // ห้องประเภท 200-299
            } else if (roomNumber >= 300 && roomNumber < 400) {
                type3Count++; // ห้องประเภท 300-399
            }
        }

        System.out.println("Rooms in type 1 (100-199): " + type1Count);
        System.out.println("Rooms in type 2 (200-299): " + type2Count);
        System.out.println("Rooms in type 3 (300-399): " + type3Count);

        //สร้าง Array เก็บชื่อปรเภทห้อง
        ArrayList<String> TypeRoomRemaining = new ArrayList<>();

        TypeRoomRemaining.add()

        // if(type1Count > 0){
        //     TypeRoomRemaining.add("Standard Room");
        // }
        // else if(type2Count > 0){
        //     TypeRoomRemaining.add("Superior Room");
        // }
        // else if(type3Count > 0){
        //     TypeRoomRemaining.add("Family Room");
        // }

        //เลือกประเภทห้องที่ต้องการ

        if (TypeRoomRemaining.isEmpty()) {
            System.out.println("Sorry, no rooms are available for the selected dates.");
        } else {
            System.out.println("Available rooms:");
            for (String TRR : TypeRoomRemaining) {
                System.out.println("Room " + TRR + ": " +  );
            }
        }



        // ======================================================================================================
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
        System.out.println("Enter Check-In Date: ");
        int checkInDate = scanner.nextInt();
        System.out.println("Enter Check-Out Date: ");
        int checkOutDate = scanner.nextInt();

        // เลือกจำนวนห้อง

    }

    // =====================================================================================

    public static ArrayList<MasterRoom> SETROOM() {
        // ArrayList<Room> rooms = Room.loadRoomsFromJson("rooms.json");
        ArrayList<MasterRoom> rooms = new ArrayList<>();
        // สร้างห้อง
        if (rooms.isEmpty()) {
            rooms.add(new MasterRoom(101, "Standard", 1000));
            rooms.add(new MasterRoom(102, "Deluxe", 2000));
            rooms.add(new MasterRoom(103, "Suite", 3000));
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
        int ChooseRole = scanner.nextInt();

        if (ChooseRole == 1) {

        } else if (ChooseRole == 2) {
            // สร้างห้อง
        } else {
            main(null);
        }
    }

}
