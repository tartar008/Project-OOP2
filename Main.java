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
import src.management.Receptionist;
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
            Receptionist receptionist = new Receptionist("Somchai", "Mukem", "0999999999", "somchai@email.hotel.ac.th", "RP-001");
            boolean choiceMain = true;

            while(choiceMain){
                System.out.println("[ 1 ] Check-In\n[ 2 ] Check-Out\n[ 3 ] Exit");
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt(); scanner.nextLine();
                switch (choice) {
                    case 1:
                        checkIn(receptionist);
                        break;
                    
                    case 2:
                        checkOut(receptionist);
                        break;
                    
                    case 3:
                        choiceMain = false;
                    
                    default:
                        break;
                }

            }
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
            if (complete.equals("n")) {
                break;
            }
        } // end complete loop

        booking.bookingInfo(); 

        System.out.print("Comfirm booking(y/n): ");
        String comfirm = scanner.nextLine();
        if (comfirm.equalsIgnoreCase("y")) {
            booking.confirmBooking();
            reserveRoom.UpdateJsonBookingDates(listBookRoom, checkInDate, checkOutDate); // ส่งไปอัพเดตวันที่ห้องถูกจอง

            payment(booking);
        }

    }// end reservationOnline

    public static void checkIn(Receptionist receptionist){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter booking id: ");
        String bookingIdCustomer = scanner.nextLine();

        boolean findBooking = receptionist.findBookingById(bookingIdCustomer); 

        if (findBooking) {
            receptionist.updateRoomStatus(bookingIdCustomer, true);
        }
        else {
            System.out.println("Booking with ID: " + bookingIdCustomer + " not found.");
        }

    }
    public static void checkOut(Receptionist receptionist){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter booking id: ");
        String bookingIdCustomer = scanner.nextLine();

        boolean findBooking = receptionist.findBookingById(bookingIdCustomer); 

        if (findBooking) {
            receptionist.updateRoomStatus(bookingIdCustomer, false);
        }
        else {
            System.out.println("Booking with ID: " + bookingIdCustomer + " not found.");
        }

    }

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

    public static void payment(Booking booking) {
        PaymentAndReceipt payment = new PaymentAndReceipt();

        double amount1 = 4000.0;  // ตัวอย่างจำนวนเงินที่ต้องชำระ
        String paymentMethod = "Credit Card";  // วิธีการชำระเงิน
        
        payment.processPayment(booking, amount1, paymentMethod);
    }


    private static void printHeader(String message) {
        System.out.println("\n" + "=".repeat(50));
        System.out.printf("%-48s%s%n", message, "=");
        System.out.println("=".repeat(50));
    }

}
