import java.io.FileReader;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import src.management.Manager;
import src.rooms.MasterRoom;
import src.rooms.TransectionRoom;
import src.bookings.*;
import src.customers.*;

import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        File fileReserveRoom = new File("./resources/JSON_ReserveRoom.json");

        // สร้างผู้จัดการ และ สร้างห้องขึ้นมาผ่านผู้จัดการ
        Manager manager = new Manager();

        ArrayList<MasterRoom> rooms = manager.loadRooms();

        if (rooms.isEmpty()) {
            System.out.println("rooms is null");
        } else {
            System.out.println("yesss");

        }
        for (MasterRoom room : rooms) {
            System.out.println(" -" + room.getType() + " " + room.getRoomNumber());
        }

        // สร้าง TransectionRoom สำหรับเก็บ MasterRoom
        ReserveRoom reserveRoom = new ReserveRoom();
        
        for (MasterRoom runRoom : rooms) {
            TransectionRoom transectionRoom = new TransectionRoom(runRoom);
            reserveRoom.AddTransectionRoom(transectionRoom);
        }

        if(fileReserveRoom.exists() && fileReserveRoom.length()==0){
            reserveRoom.WriteJsonBooking();
        }

        // ตรวจสอบแล้วไม่เจอ username นี้อยู่ใน json
        if (!signIn()) {
            Customer agent = signUp();
            System.out.println(" Welcome to the Hotel Booking! " + agent.getFirstName());
            while (true) {
                System.out.println("[ 1 ] Booking a room");
                System.out.println("[ 2 ] Log out");
                System.out.print("Enter choice: ");
                int choiceMain = scanner.nextInt();
                scanner.nextLine();

                if (choiceMain == 2) {
                    return;
                }

                reservationOnline(reserveRoom, agent);
            }

        } else {
            
            System.out.println("emp/manager");
        }

    }// end main

    public static void reservationOnline(ReserveRoom reserveRoom, Customer agent) {
        Scanner scanner = new Scanner(System.in);

        reserveRoom.displayCalendar();
        LocalDate checkInDate, checkOutDate;

        while (true) {
            System.out.print("Enter check-in date (day): ");
            int startDay = scanner.nextInt();
            System.out.print("Enter check-out date (day): ");
            int endDay = scanner.nextInt();

            checkInDate = LocalDate.of(2024, 10, startDay);
            checkOutDate = LocalDate.of(2024, 10, endDay);

            if (checkInDate.isAfter(checkOutDate)) {
                System.out.println("Check-in date must be before check-out date. Please try again.");
                continue;
            }
            break;
        }

        List<src.rooms.TransectionRoom> availableRooms = reserveRoom.getAvailableRooms(checkInDate, checkOutDate);
        System.out.println(" --- " + availableRooms.size());

        if (availableRooms.isEmpty()) {
            System.out.println("No rooms available for the selected dates. Please choose another date.");
            return;
        }

        List<TransectionRoom> standards = reserveRoom.getAvailableRoomsByType("Standard", checkInDate, checkOutDate);
        List<TransectionRoom> family = reserveRoom.getAvailableRoomsByType("Family", checkInDate, checkOutDate);
        List<TransectionRoom> honeymoon = reserveRoom.getAvailableRoomsByType("Honeymoon", checkInDate, checkOutDate);

        List<Customer> guests = new ArrayList<>();
        ArrayList<TransectionRoom> listBookRoom = new ArrayList<>(); // เก็บห้องสำหรับการจอง
        Booking booking = new Booking();
        boolean isComplete = true;
        int roomType;

        while (isComplete) {
            displayAvailableRooms(standards, family, honeymoon);

            boolean limitRoom = true;
            while (limitRoom) {

                roomType = selectRoomType(scanner, standards, family, honeymoon); // get input roomtype
                List<TransectionRoom> selectedRoomType = getSelectedRooms(roomType, standards, family, honeymoon);
                System.out.print("Enter number of room(s): ");
                int numOfRoom = scanner.nextInt();
                scanner.nextLine();

                if (numOfRoom <= selectedRoomType.size()) {
                    limitRoom = false;
                    // เพิ่มทำรายการจอง
                    for (int i = 0; i < numOfRoom; i++) {

                        TransectionRoom assignedRoom = reserveRoom.assignRoom(selectedRoomType);

                        if (assignedRoom != null) {
                            System.out.println("> " + assignedRoom.getRoom().getType() + " Room #" + (i + 1));
                            System.out.print("I am booking for someone else?(y/n): ");
                            String isBookingforSomeOne = scanner.nextLine();
                            if (isBookingforSomeOne.equalsIgnoreCase("y")) {
                                guests.add(getInputGuest());
                            } else if (isBookingforSomeOne.equalsIgnoreCase("n")) {
                                guests.add(agent);
                            } else {
                                System.out.println("Invalid input");
                            }

                            listBookRoom.add(assignedRoom);
                            booking = new Booking(agent, guests, listBookRoom, checkInDate, checkOutDate);

                            selectedRoomType.remove(assignedRoom);

                        } else {
                            System.out.println("No available room to assign");
                        }

                    }

                } else {
                    System.out.println("You chose more than available!!");
                }

            } // end limitRoom loop
            System.out.print("Do you want to book another room? (y/n): ");
            String complete = scanner.nextLine();
            if (complete.equals("y")) {
                break;
            }
        } // end complete loop

        booking.bookingInfo(); 

        System.out.print("Comfirm booking(y/n): ");
        String comfirm = scanner.nextLine();
        if (comfirm.equalsIgnoreCase("y")) {
            booking.confirmBooking();
            reserveRoom.UpdateJsonBookingDates(listBookRoom, checkInDate, checkOutDate); // ส่งไปอัพเดตสถานะ
        }

    }// end reservationOnline

    private static TransectionRoom assignRoom(List<TransectionRoom> selectedRoomType) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'assignRoom'");
    }

    // Booking(Customer agent, List<Customer> guests, List<TransectionRoom> rooms,
    // int amountRoom, int roomType,LocalDate checkInDate, LocalDate checkOutDate)
    public static boolean signIn() {
        printHeader("Sign In");
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String pwd = scanner.nextLine();

        // เช็คใน json
        if (username.equals("emp")) {
            return true;
        } else {
            return false;
        }
    }

    public static Customer signUp() {
        Scanner scanner = new Scanner(System.in);
        printHeader("Sign Up");
        System.out.print("Enter First name: ");
        String firstName = scanner.nextLine();
        System.out.print("Enter Last name: ");
        String lastName = scanner.nextLine();
        System.out.print("Enter Phone number: ");
        String phoneNum = scanner.nextLine();
        System.out.print("Enter Email: ");
        String email = scanner.nextLine();

        return new Customer(firstName, lastName, phoneNum, email);
    }

    public static Customer getInputGuest() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter guest name: ");
        String name = scanner.nextLine();
        System.out.print("Enter guest surname: ");
        String surname = scanner.nextLine();

        return new Customer(name, surname);
    }

    public static ArrayList<MasterRoom> SETROOM() {
        // ArrayList<Room> rooms = Room.loadRoomsFromJson("rooms.json");
        ArrayList<MasterRoom> rooms = new ArrayList<>();
        // สร้างห้อง
        if (rooms.isEmpty()) {
            rooms.add(new MasterRoom(101, "Standard", 1000));
            rooms.add(new MasterRoom(102, "Standard", 1000));
            rooms.add(new MasterRoom(201, "Family", 2000));
            rooms.add(new MasterRoom(301, "Honeymoon", 3000));
            // Room.saveRoomsToJson(rooms, "rooms.json");
        }

        return rooms;
    }// end setroom

    private static void displayAvailableRooms(List<TransectionRoom> standards, List<TransectionRoom> family,
            List<TransectionRoom> honeymoon) {
        // printHeader("Available Rooms");
        System.out.println("[ 1 ] Standard Rooms: " + "[" + standards.size() + "]");
        System.out.println("[ 2 ] Family Rooms: " + "[" + family.size() + "]");
        System.out.println("[ 3 ] Honeymoon Rooms: " + "[" + honeymoon.size() + "]");
    }

    private static int selectRoomType(Scanner scanner, List<TransectionRoom> standards, List<TransectionRoom> family,
            List<TransectionRoom> honeymoon) {
        HashMap<Integer, String> roomTypeMap = new HashMap<>();
        int index = 0;

        if (!standards.isEmpty()) {
            roomTypeMap.put(++index, "Standard");
        }
        if (!family.isEmpty()) {
            roomTypeMap.put(++index, "Family");
        }
        if (!honeymoon.isEmpty()) {
            roomTypeMap.put(++index, "Honeymoon");
        }

        System.out.print("\nEnter your Type Room: ");
        int selectRoomType = scanner.nextInt();
        scanner.nextLine();
        return selectRoomType;
    }

    private static List<TransectionRoom> getSelectedRooms(int roomTypeSelection, List<TransectionRoom> standards,
            List<TransectionRoom> family, List<TransectionRoom> honeymoon) {
        switch (roomTypeSelection) {
            case 1:
                return standards;
            case 2:
                return family;
            case 3:
                return honeymoon;
            default:
                return new ArrayList<>(); // Empty list if selection is invalid
        }
    }

    // private static Customer collectBookingAgentDetails(Scanner scanner,
    // ArrayList<Customer> guests) {
    // Customer agent = null;
    // System.out.print("> Contact Details\nI am booking for someone else? (Y/N):
    // ");
    // String isBooker = scanner.nextLine();

    // if (isBooker.equalsIgnoreCase("Y")) {
    // System.out.print("Enter First name: ");
    // String firstName = scanner.nextLine();
    // System.out.print("Enter Last name: ");
    // String lastName = scanner.nextLine();
    // System.out.print("Enter Phone number: ");
    // String phoneNum = scanner.nextLine();
    // System.out.print("Enter Email: ");
    // String email = scanner.nextLine();
    // agent = new Customer(firstName, lastName, phoneNum, email, true);
    // } else {
    // System.out.print("Enter Phone number: ");
    // String phoneNum = scanner.nextLine();
    // System.out.print("Enter Email: ");
    // String email = scanner.nextLine();

    // agent = guests.get(0);
    // agent.setPhoneNumber(phoneNum);
    // agent.setEmail(email);
    // agent.setAgent(true);
    // }
    // return agent;
    // }

    // private static void RoleManager(Manager manager) {
    // Scanner scanner = new Scanner(System.in);

    // while (true) {
    // System.out.println("\n=== Hotel Management System ===");
    // System.out.println("1. Manage Rooms");
    // System.out.println("2. View Customer Stay History");
    // System.out.println("3. View Hotel Income");
    // System.out.println("4. Exit");
    // System.out.print(">>> ");

    // int choice = scanner.nextInt();

    // switch (choice) {
    // case 1:
    // ManageRooms(manager); // ฟังก์ชันสำหรับจัดการห้อง
    // break;
    // case 2:
    // ViewCustomerStayHistory(manager); // ฟังก์ชันสำหรับดูประวัติการเข้าพัก
    // break;
    // case 3:
    // ViewHotelIncome(manager); // ฟังก์ชันสำหรับดูรายได้โรงแรม
    // break;
    // case 4:
    // System.out.println("Exiting the system...");
    // scanner.close();
    // return; // ออกจากโปรแกรม
    // default:
    // System.out.println("Invalid choice. Please try again.");
    // break;
    // }
    // }
    // }

    // public static void ManageRooms(Manager manager) {
    // Scanner scanner = new Scanner(System.in);

    // while (true) {
    // System.out.println("1. Add Room");
    // System.out.println("2. Remove Room");
    // System.out.println("3. Edit Room [Log]");
    // System.out.println("4. View All Rooms");
    // System.out.println("5. Exit");
    // System.out.println(">>> ");

    // int choice = scanner.nextInt();

    // switch (choice) {
    // case 1:
    // System.out.print("Enter room number: ");
    // int roomNumber = scanner.nextInt();
    // System.out.print("Enter room type (Standard/Family/Honeymoon): ");
    // String roomType = scanner.next();
    // System.out.print("Enter room price: ");
    // double price = scanner.nextDouble();
    // manager.AddRoom(roomNumber, roomType, price);
    // break;
    // case 2:
    // System.out.print("Enter room number to remove: ");
    // roomNumber = scanner.nextInt();
    // manager.RemoveRoom(roomNumber);
    // break;
    // case 3:
    // System.out.print("Enter room number to edit: ");
    // int editRoomNumber = scanner.nextInt();
    // scanner.nextLine(); // รับค่า new line

    // System.out.print("Enter new room type: ");
    // String newRoomType = scanner.nextLine();

    // System.out.print("Enter new room price: ");
    // double newPrice = scanner.nextDouble();

    // manager.EditRoom(editRoomNumber, newRoomType, newPrice);
    // break;
    // case 4:
    // manager.DisplayAllRooms();
    // break;
    // case 5:
    // return; // ออกจากเมนูจัดการ
    // default:
    // System.out.println("Invalid choice.");
    // }
    // }
    // }

    // private static void ViewCustomerStayHistory(Manager manager) {
    // manager.DisplayReserveRoom();

    // }

    // private static void ViewHotelIncome(Manager manager) {
    // manager.HotelIncome();
    // }

    private static void printHeader(String message) {
        System.out.println("\n" + "=".repeat(50));
        System.out.printf("%-48s%s%n", message, "=");
        System.out.println("=".repeat(50));
    }

}
