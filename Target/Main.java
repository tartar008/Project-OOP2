
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
            handleOnlineBooking(reserveRoom, customer1);
        } else if (choice == 3) {

        } else {

        }
    }

    private static void handleOnlineBooking(ReserveRoom reserveRooms, Customer customer1) {
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
        // for (TransectionRoom TSroom : availableRooms) {
        // if (TSroom.isAvailable(checkInDate)) {
        // System.out.println("Room " + TSroom.getRoom().getRoomNumber() + " is
        // available today.");
        // } else {
        // System.out.println("Room " + TSroom.getRoom().getRoomNumber() + " is not
        // available today.");
        // }
        // }

        // ======================================================================================================
        // คัดกรองให้เลือกเป็นประเภทห้อง
        ArrayList<TransectionRoom> Standards = new ArrayList<>();
        ArrayList<TransectionRoom> Paeiors = new ArrayList<>();
        ArrayList<TransectionRoom> Familys = new ArrayList<>();

        for (TransectionRoom tsRoom : availableRooms) {
            if (tsRoom.getRoom().getRoomNumber() > 100 && tsRoom.getRoom().getRoomNumber() < 200) {
                Standards.add(tsRoom);
            } else if (tsRoom.getRoom().getRoomNumber() > 200 && tsRoom.getRoom().getRoomNumber() < 300) {
                Paeiors.add(tsRoom);
            } else if (tsRoom.getRoom().getRoomNumber() > 300 && tsRoom.getRoom().getRoomNumber() < 400) {
                Familys.add(tsRoom);
            }
        }

        int Index = 0;
        HashMap<Integer, String> ChooseRoomMap = new HashMap<>();

        if (!Standards.isEmpty()) {
            Index++;
            System.out.println("[" + Index + "] Room Type: Standard, Room Amount: " + Standards.size());
            ChooseRoomMap.put(Index, "Standard");
        }
        if (!Paeiors.isEmpty()) {
            Index++;
            System.out.println("[" + Index + "] Room Type: Paeior, Room Amount: " + Paeiors.size());
            ChooseRoomMap.put(Index, "Paeior");
        }
        if (!Familys.isEmpty()) {
            Index++;
            System.out.println("[" + Index + "] Room Type: Family, Room Amount: " + Familys.size());
            ChooseRoomMap.put(Index, "Family");
        }

        Index = 0;
        String ChooseRoomStringName = "";
        // เลือกประเภทห้องและจำนวนห้องที่ต้องการจอง
        // เลือกประเภทห้อง
        System.out.print("\nEnter your Type Room: ");
        int roomType = scanner.nextInt();
        for (Map.Entry<Integer, String> entry : ChooseRoomMap.entrySet()) {
            int key = entry.getKey();
            String value = entry.getValue();
            if (roomType == key) {
                ChooseRoomStringName = value;
            }
        }

        ArrayList<TransectionRoom> ChooseTypeRooms = new ArrayList<>();
        if (ChooseRoomStringName.equals("Standard")) {
            ChooseTypeRooms = Standards;
        } else if (ChooseRoomStringName.equals("Paeior")) {
            ChooseTypeRooms = Paeiors;
        } else if (ChooseRoomStringName.equals("Family")) {
            ChooseTypeRooms = Familys;
        }

        boolean LimitRoom = true;
        int numberOfRooms = 0;
        TransectionRoom ComfirmRoom = new TransectionRoom();
        while (LimitRoom) {
            System.out.print("Enter number of room(s): ");
            numberOfRooms = scanner.nextInt();

            System.out.println("============ Debug Start ============");

            System.out.println("numberOfRooms: " + numberOfRooms);
            System.out.println("ChooseTypeRooms: " + ChooseTypeRooms.size());

            System.out.println("============ Debug End ============");

            if (numberOfRooms <= ChooseTypeRooms.size()) {
                LimitRoom = false;
                ComfirmRoom = ChooseTypeRooms.get(0);
            } else {
                System.out.println("You choose more limit!!");
            }

        }

        // สุดท้ายก็ต้องใส่ Room เป็น Object อยู่ดี
        Booking booking = new Booking(customer1, ComfirmRoom, checkInDate, checkOutDate);

        System.out.print("\nComplete Booking (Y/N): ");
        String completeBooking = scanner.nextLine();

        // สรุปการจอง
        booking.bookingInfo();

        // ชำระเงิน

        // double totalAmount = 0;
        // if (completeBooking.equalsIgnoreCase("Y")) {
        // if (selectedRoomType.equals("Standard")) {
        // totalAmount = 1728 * numberOfRooms;
        // } else if (selectedRoomType.equals("Paeior")) {
        // totalAmount = 3690 * numberOfRooms;
        // } else if (selectedRoomType.equals("Family")) {
        // totalAmount = 5364 * numberOfRooms;
        // }

        // System.out.println("\nBooking Completed. Thank you for your reservation!");
        // } else {
        // System.out.println("Booking canceled.");
        // }

        // ======================================================================================================
    }

    // private static void Receipt(){

    // System.out.println("\n-- Payment Process --");
    // System.out.println("Total Amount: THB " + totalAmount);
    // System.out.print("Enter payment method (Cash/Card): ");
    // String paymentMethod = scanner.nextLine();

    // System.out.println("\n-- Receipt --");
    // System.out.println("Customer: Piyachai Narongsab");
    // System.out.println("Phone: 081XXXXXXX");
    // System.out.println("Email: tartar0081@gmail.com");
    // System.out.println("Total Amount: THB " + totalAmount);
    // System.out.println("Payment Method: " + paymentMethod);

    // }

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
            rooms.add(new MasterRoom(102, "Standard", 1000));
            rooms.add(new MasterRoom(201, "Paeior", 2000));
            rooms.add(new MasterRoom(301, "Family", 3000));
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
