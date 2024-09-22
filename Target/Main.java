// import java.lang.classfile.instruction.StackInstruction;
// import java.time.LocalDate;
import java.util.ArrayList;
// import java.util.List;
import java.util.Scanner;

// import javax.sql.rowset.serial.SerialStruct;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        ArrayList<MasterRoom> rooms = SETROOM();
        
        // ReserveRoom reserveRooms = new ReserveRoom();
        ArrayList<ReserveRoom> reserveRooms = new ArrayList<>();

        // แสดงผล MasterRoom ก่อนทำการ clone
        System.out.println("\nBefore modification in ReserveRoom:");
        for (MasterRoom masterRoom : rooms) {
            System.out.println("Master Room: " + masterRoom.getRoomNumber() + ", Type: " + masterRoom.getType());
        }

        System.out.println("Test ReserveRoom and TransectionRoom");

        for(MasterRoom runRoom : rooms){
            TransectionRoom transectionRoom = new TransectionRoom(runRoom);
            ReserveRoom reserveRoom = new ReserveRoom(transectionRoom); // สร้างอ็อบเจ็กต์ ReserveRoom
            reserveRooms.add(reserveRoom); // เพิ่มอ็อบเจ็กต์ลงในลิสต์ reserveRooms
        }
        reserveRooms.get(0).getTransectionRoom().getRoom().setRoomNumber("999");
        System.out.println("\n " + reserveRooms.get(0).getRoomNumber());
        // แสดงผลหลังการแก้ไข TransectionRoom เพื่อตรวจสอบผลกระทบต่อ MasterRoom
        System.out.println("\nAfter modification in ReserveRoom:");
        for (MasterRoom masterRoom : rooms) {
            System.out.println("Master Room: " + masterRoom.getRoomNumber() + ", Type: " + masterRoom.getType());
        }

       
        System.exit(1);

        //ตัดออกก่อน
        // for (Room runRoom : rooms) {
        //     // สร้างอ็อบเจ็กต์ ReserveRoom ใหม่จาก Room ที่เป็น master
        //     ReserveRoom reserveRoom = new ReserveRoom(runRoom);
        //     reserveRooms.add(reserveRoom);
        // }

          
        // ReserveRoom reserveRoom = SETRESERVEROOM(rooms);
        

        // System.out.println("\nWelcome to the Hotel Booking System!");
        // System.out.println("Choose your role:");
        // System.out.println("1. Customer");
        // System.out.println("2. Employee\t [log]");
        // System.out.println("3. Exit \t [log]");
        // System.out.print(">>> ");
        // // System.exit(1);
        // int ChooseRole = scanner.nextInt();

        // if (ChooseRole == 1) {
        //     User(reserveRooms);

        // } else if (ChooseRole == 2) {
        //     Employee();

        // } else {
        //     main(args);
        // }
    }

//=====================================================================================

    private static void handleOnlineBooking(ReserveRoom reserveRooms) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("You chose Online booking.");

        // เลือกวันจอง

        // Display calendar (simplified as boolean array)
        boolean[] roomAvailability = new boolean[30];
        for (int i = 0; i < 30; i++) {
            roomAvailability[i] = true; // Assume all rooms are available
        }

        reserveRooms.displayCalendar();
        // ReserveRoom reserveRoomObj = new ReserveRoom();

        System.out.print("Enter check-in date : ");
        int startDay = scanner.nextInt();
        System.out.print("Enter check-out date : ");
        int EndDay = scanner.nextInt();

        // LocalDate checkInDate = LocalDate.of(2024, 9, startDay); // วันที่เข้าพัก
        // LocalDate checkOutDate = LocalDate.of(2024, 9, EndDay); // วันที่ออก

        // reserveRoomObj.getAvailableRooms(LocalDate checkInDate, LocalDate checkOutDate);

        // for (ReserveRoom reserveRoom : reserveRooms) {
        //     if (reserveRoom.isAvailable(checkInDate)) {
        //         System.out.println("Room " + reserveRoom.getRoomNumber() + " is available today.");
        //     } else {
        //         System.out.println("Room " + reserveRoom.getRoomNumber() + " is not available today.");
        //     }
        // }


        // เลือกจำนวนห้อง
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


    public static void User(ReserveRoom reserveRooms) {
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
            handleWalkInBooking(reserveRooms);
        } else if (choice == 2) {
            handleOnlineBooking(reserveRooms);
        } else if (choice == 3) {

        } else {

        }
    }


//=====================================================================================

    public static ArrayList<MasterRoom> SETROOM() {
        // ArrayList<Room> rooms = Room.loadRoomsFromJson("rooms.json");
        ArrayList<MasterRoom> rooms = new ArrayList<>();
        //สร้างห้อง
        if (rooms.isEmpty()) {
            rooms.add(new MasterRoom("101", "Standard", 1000));
            rooms.add(new MasterRoom("102", "Deluxe", 2000));
            rooms.add(new MasterRoom("103", "Suite", 3000));
            // Room.saveRoomsToJson(rooms, "rooms.json");
        }
        
        return rooms;
    }

    
    // public static ReserveRoom SETRESERVEROOM(ArrayList<ReserveRoom> rooms) {
    //     // ReserveRoom reserveRooms = new ReserveRoom();

    //     // ArrayList<Room> copyRooms = new ArrayList<>();

    //     // for (Room runRoom : rooms) {
    //     //     // reserveRooms.setRooms(copyRooms);
    //     // }

    //     // // ให้มีการวนเพื่อนำ room มาเข้า reserveRoom ก่อน

    //     // return reserveRooms;
    // }


    public static void Employee() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Choose your role:");
        System.out.println("1. Receptionist  [log]");
        System.out.println("2. Manager\t [log]");
        System.out.println("3. Exit \t [log]");
        int ChooseRole = scanner.nextInt();

        if (ChooseRole == 1) {

        } else if (ChooseRole == 2) {
            //สร้างห้อง
        } else {
            main(null);
        }
    }



}

// ทำให้ถึง check-out-date
//
//
