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
import java.util.InputMismatchException;
import javax.sound.midi.Receiver;

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

        System.out.println("=== Welcome to the Hotel Management System ===");

        while (true) {
            try {
                System.out.println("[ 1 ] Sign in");
                System.out.println("[ 2 ] Exit");
                System.out.print("Enter your choice: ");
                
                int choice = scanner.nextInt();
                scanner.nextLine();
        
                if (choice == 2) {
                    System.out.println("Exiting the system...");
                    break; // ออกจากโปรแกรม
                } else if (choice == 1) {
                    String role = signIn(); 
                    if (role.equals("customer")) {
                        Customer agent = signUp();
                        System.err.println();
                        System.out.println(cyan + " Welcome to the Hotel Booking! " + agent.getFirstName() + reset);
                        while (true) {
                            System.err.println();
                            try {
                                System.out.println("[ 1 ] Booking a room");
                                System.out.println("[ 2 ] Log out");
                                System.out.print("Enter choice: ");
                                int choiceMain = scanner.nextInt();
                                scanner.nextLine();
        
                                if (choiceMain == 2) {
                                    System.out.println("Logging out...");
                                    break; // กลับไปที่ Main Menu
                                } else if (choiceMain == 1) {
                                    reservationOnline(reserveRoom, agent);
                                } else {
                                    System.out.println("Invalid choice. Please select again.");
                                }
                            } catch (InputMismatchException e) {
                                System.out.println("Invalid input. Please enter a valid integer.");
                                scanner.nextLine();
                            }
                        }
                    } else if (role.equals("manager")) {
                        RoleManager(manager);
                    } else if (role.equals("receptionist")) {
                        RoleReceptionist();
                    }
                } else {
                    System.out.println("Invalid choice. Please select 1 or 2.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine();
            }
        }
        
        

    }// end main

    public static void reservationOnline(ReserveRoom reserveRoom, Customer agent) {
        Scanner scanner = new Scanner(System.in);

        displayCalendar();
        LocalDate checkInDate, checkOutDate;

        while (true) {
            try {
                System.out.print("Enter check-in date (day): ");
                int startDay = scanner.nextInt();

                if (startDay < 1 || startDay > 30) {
                    System.out.println("Invalid check-in day. Please enter a day between 1 and 30.");
                    continue;
                }
                
                checkInDate = LocalDate.of(2024, 10, startDay);
                
                System.out.print("Enter check-out date (day): ");
                int endDay = scanner.nextInt();
                
                if (endDay < 1 || endDay > 30) {
                    System.out.println("Invalid check-out day. Please enter a day between 1 and 30.");
                    continue;
                }
        
                checkOutDate = LocalDate.of(2024, 10, endDay);
        
                if (checkInDate.isAfter(checkOutDate)) {
                    System.out.println("Check-in date must be before check-out date. Please try again.");
                    continue;
                }
                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid integer for the date.");
                scanner.nextLine();
            }
        }

        List<src.rooms.TransectionRoom> availableRooms = reserveRoom.getAvailableRooms(checkInDate, checkOutDate);

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
        boolean isComplete = false;
        int roomType;

        while (!isComplete) {
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
                    for (int i = 0; i < numOfRoom; i++) {
                        TransectionRoom assignedRoom = reserveRoom.assignRoom(selectedRoomType);
                    
                        if (assignedRoom != null) {
                            System.out.println("\n> " + assignedRoom.getRoom().getType() + " Room #" + (i + 1) + " <");
                            
                            // ตรวจสอบว่าผู้ใช้ป้อนข้อมูลเกี่ยวกับการจองได้ถูกต้อง
                            String isBookingforSomeOne;
                            while (true) {
                                System.out.print("I am booking for someone else?(y/n): ");
                                isBookingforSomeOne = scanner.nextLine();
                    
                                if (isBookingforSomeOne.equalsIgnoreCase("y")) {
                                    guests.add(getInputGuest());
                                    break; // ออกจากลูปเมื่อป้อนข้อมูลถูกต้อง
                                } else if (isBookingforSomeOne.equalsIgnoreCase("n")) {
                                    guests.add(agent);
                                    break; // ออกจากลูปเมื่อป้อนข้อมูลถูกต้อง
                                } else {
                                    System.out.println("Invalid input, please enter 'y' or 'n'");
                                }
                            }
                    
                            listBookRoom.add(assignedRoom);
                            booking = new Booking(agent, guests, listBookRoom, checkInDate, checkOutDate);
                    
                            // ลบห้อง
                            selectedRoomType.remove(assignedRoom);
                        } else {
                            System.out.println("No available room to assign");
                            break; // ออกจากลูปถ้ามีห้องไม่เพียงพอ
                        }
                    }

                } else {
                    System.out.println("You choose more than available!!");
                }

            } // end limitRoom loop

            String complete = "";
            boolean isCompleteInput = false;
            
            while (!isCompleteInput) {
                System.out.print("Do you want to book another room? (y/n): ");
                complete = scanner.nextLine().trim(); // ใช้ trim() เพื่อเอาช่องว่างที่ขอบออก
        
                if (complete.equalsIgnoreCase("n")) {
                    isComplete = true; // ออกจากลูปหลัก
                    isCompleteInput = true; // ออกจากลูปการตรวจสอบการกรอกข้อมูล
                }
                else if (complete.equalsIgnoreCase("y")) {
                    System.out.println("Please complete the process.");
                    isCompleteInput = true; // ออกจากลูปการตรวจสอบการกรอกข้อมูล แต่ยังอยู่ในลูปหลัก
                }
                else {
                    System.out.println("Invalid input. Please enter 'y' or 'n'");
                }
            }
        } // end complete loop

        booking.bookingInfo(); 

        String comfirm;
        while (true) {
            System.out.print("Comfirm booking (y/n): ");
            comfirm = scanner.nextLine().trim();

            if (comfirm.equalsIgnoreCase("y") || comfirm.equalsIgnoreCase("n")) {
                break;
            } else {
                System.out.println("Invalid input! Please enter 'y' or 'n'");
            }
        }

        if (comfirm.equalsIgnoreCase("y")) {
            booking.confirmBooking();
            reserveRoom.UpdateJsonBookingDates(listBookRoom, checkInDate, checkOutDate); // ส่งไปอัพเดตสถานะ
            payment(booking);
        }
    }// end reservationOnline


    public static String signIn() {
        printHeader("Sign In");
        Scanner scanner = new Scanner(System.in);
        
        //Username
        String username = "";
        boolean validUsername = false;
        while (!validUsername) {
            System.out.print("Enter username: ");
            username = scanner.nextLine();
    
            if (username == null || username.trim().isEmpty()) {
                System.out.println("Username cannot be null or empty! Please enter again.");
            }
            else if (!username.matches("[a-zA-Z]+")) { 
                System.out.println("Username must contain only letters! Please enter again.");
            }
            else {
                validUsername = true;
            }
        }

        // Password
        String pwd = "";
        boolean validPassword = false;
        while (!validPassword) {
            System.out.print("Enter password: ");
            pwd = scanner.nextLine();
    
            if (pwd == null || pwd.trim().isEmpty()) {
                System.out.println("Password cannot be null or empty! Please enter again.");
            } 
            else if (pwd.length() > 10) { 
                System.out.println("Password not exceed 10 characters! Please enter again.");
            }
            else if (!pwd.matches("[A-Za-z0-9]+")) {  
                System.out.println("Password must contain only letters or numbers! Please enter again.");
            }
            else {
                validPassword = true;
            }
        }

        // เช็คใน json
        if (username.equals("Admin") && pwd.equals("System123")) { // รหัสผ่านเป็นตัวอย่าง
            return "manager";
        } else if(username.equals("Receptionist") && pwd.equals("System321")) {
            return "receptionist";
        } else {
            return "customer";
        }
    }

    public static Customer signUp() {
        Scanner scanner = new Scanner(System.in);
        printHeader("Sign Up");
        
        // Firstname
        String firstName = "";
        boolean validFirstName = false;
        while (!validFirstName) {
            System.out.print("Enter First name: ");
            firstName = scanner.nextLine();

            if (firstName == null || firstName.trim().isEmpty()) {
                System.out.println("First name cannot be null or empty. Please enter again.");
            }
            else if (!firstName.matches("[a-zA-Z]+")) {
                System.out.println("First name must contain only letters. Please enter again.");
            }
            else {
                validFirstName = true;
            }
        }

        // Lastname
        String lastName = "";
        boolean validLastName = false;
        while (!validLastName) {
            System.out.print("Enter Last name: ");
            lastName = scanner.nextLine();

            if (lastName == null || lastName.trim().isEmpty()) {
                System.out.println("Last name cannot be null or empty. Please enter again.");
            }
            else if (!lastName.matches("[a-zA-Z]+")) {
                System.out.println("Last name must contain only letters. Please enter again.");
            }
            else {
                validLastName = true;
            }
        }
        
        //Phone Number
        String phoneNum = "";
        boolean validPhoneNum = false;
        while (!validPhoneNum) {
            System.out.print("Enter Phone number (10 digit): ");
            phoneNum = scanner.nextLine();

            if (phoneNum == null || phoneNum.trim().isEmpty()) {
                System.out.println("Phone number cannot be null or empty. Please enter again.");
            } else if (phoneNum.length() != 10) { 
                System.out.println(red + "Phone number must be exactly 10 digits. Please enter again." + reset);
            } else if (!phoneNum.matches("[0-9]+")) { // ตรวจสอบว่าเป็นตัวเลขล้วน
                System.out.println(red + "Phone number must contain only numbers. Please enter again." + reset);
            } else {
                validPhoneNum = true;
            }
        }

        // Email
        String email = "";
        boolean validEmail = false;
        while (!validEmail) {
            System.out.print("Enter Email (xxxx@gamail.com): ");
            email = scanner.nextLine();

            if (email == null || email.trim().isEmpty()) {
                System.out.println("Email cannot be null or empty. Please enter again.");
            } else if (!email.matches("^[\\w-\\.]+@[\\w-]+\\.[a-zA-Z]{2,}$")) { // ตรวจสอบรูปแบบ email
                System.out.println("Invalid email format. Please enter a valid email.");
            } else {
                validEmail = true;
            }
        }

        return new Customer(firstName, lastName, phoneNum, email);
    }

    public static Customer getInputGuest() {
        Scanner scanner = new Scanner(System.in);

        // Name guest
        String name = "";
        boolean validName = false;
        while (!validName) {
            System.out.print("Enter guest name: ");
            name = scanner.nextLine();

            if (name == null || name.trim().isEmpty()) {
                System.out.println("Name cannot be null or empty. Please enter again.");
            }
            else if (!name.matches("[a-zA-Z]+")) {
                System.out.println("Name must contain only letters. Please enter again.");
            }
            else {
                validName = true;
            }
        }

        // Surname guest
        String surname = "";
        boolean validSurname = false;
        while (!validSurname) {
            System.out.print("Enter guest surname: ");
            surname = scanner.nextLine();

            if (surname == null || surname.trim().isEmpty()) {
                System.out.println("surname cannot be null or empty. Please enter again.");
            }
            else if (!surname.matches("[a-zA-Z]+")) {
                System.out.println("surname must contain only letters. Please enter again.");
            }
            else {
                validSurname = true;
            }
        }

        return new Customer(name, surname);
    }



    private static void displayAvailableRooms(List<TransectionRoom> standards, List<TransectionRoom> family,
            List<TransectionRoom> honeymoon) {
        System.out.println("[ 1 ] Standard Rooms: " + "[" + standards.size() + "]");
        System.out.println("[ 2 ] Family Rooms: " + "[" + family.size() + "]");
        System.out.println("[ 3 ] Honeymoon Rooms: " + "[" + honeymoon.size() + "]");
    }

    private static int selectRoomType(Scanner scanner, List<TransectionRoom> standards, List<TransectionRoom> family,
            List<TransectionRoom> honeymoon) {
        HashMap<Integer, String> roomTypeMap = new HashMap<>();
        int index = 0;
        if (standards.isEmpty() && family.isEmpty() && honeymoon.isEmpty()) {
            System.out.println("Sorry, no rooms are available at the moment.");
            return -1; // คืนค่าเป็น -1 หรือค่าที่แสดงว่าไม่มีห้องว่าง
        }

        if (!standards.isEmpty()) {
            roomTypeMap.put(++index, "Standard");
        }
        if (!family.isEmpty()) {
            roomTypeMap.put(++index, "Family");
        }
        if (!honeymoon.isEmpty()) {
            roomTypeMap.put(++index, "Honeymoon");
        }

        int selectRoomType;
        while (true) {

            System.out.print("\nEnter your Type Room: ");
            selectRoomType = scanner.nextInt();
            scanner.nextLine();

            if (roomTypeMap.containsKey(selectRoomType)) {
                break;
            } else {
                System.out.println("Invalid selection. Please choose a valid room type.");
            }
        }
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


    private static void RoleManager(Manager manager) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== Hotel Management System ===");
            System.out.println("[ 1 ] Manage Rooms");
            System.out.println("[ 2 ] View Customer Stay History");
            System.out.println("[ 3 ] View Hotel Income");
            System.out.println("[ 4 ] Exit");
            System.out.print("Enter choice>>> ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    ManageRooms(manager); // ฟังก์ชันสำหรับจัดการห้อง
                    break;
                case 2:
                    ViewCustomerStayHistory(manager); // ฟังก์ชันสำหรับดูประวัติการเข้าพัก
                    break;
                case 3:
                    ViewHotelIncome(manager); // ฟังก์ชันสำหรับดูรายได้โรงแรม
                    break;
                case 4:
                    System.out.println("Exiting the system...");
                    scanner.close();
                    return; // ออกจากโปรแกรม
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }

    public static void ManageRooms(Manager manager) {
        Scanner scanner = new Scanner(System.in);

        boolean isRunning = true;

        while (isRunning) {
            System.out.println("[ 1 ] Add Room");
            System.out.println("[ 2 ] Remove Room");
            System.out.println("[ 3 ] Edit Room [Log]");
            System.out.println("[ 4 ] View All Rooms");
            System.out.println("[ 5 ] Exit");
            System.out.print("Enter choice>>> ");

            try {
                int choice = scanner.nextInt();
                
                if (choice < 1 || choice > 5) {
                    System.out.println("Invalid choice. Please enter a number between 1 and 5.");
                    scanner.nextLine();
                    continue;
                }
                int roomNumber;
                String roomType;
                double price;
                switch (choice) {
                    case 1:
                        try {
                            try {
                                System.out.print("Enter room number: ");
                                roomNumber = scanner.nextInt();
                                scanner.nextLine();
                                
                                if (roomNumber <= 0) {
                                    System.out.println("Invalid room number. Room number must be greater than zero.");
                                    continue;
                                }
                            } catch (InputMismatchException e) {
                                System.out.println("Invalid input. Please enter a valid number for the room.");
                                scanner.nextLine();
                                continue;
                            }
                
                            System.out.print("Enter room type (Standard/Family/Honeymoon): ");
                            roomType = scanner.nextLine().trim();
                            if (!roomType.equalsIgnoreCase("Standard") && 
                                !roomType.equalsIgnoreCase("Family") && 
                                !roomType.equalsIgnoreCase("Honeymoon")) {
                                System.out.println("Invalid room type. Please enter 'Standard', 'Family', or 'Honeymoon'.");
                                continue;
                            }
                
                            try {
                                System.out.print("Enter room price: ");
                                price = scanner.nextDouble();
                                scanner.nextLine();
                            
                                if (price < 0) {
                                    System.out.println("Invalid room price. Price cannot be negative.");
                                    continue;
                                }
                            } catch (InputMismatchException e) {
                                System.out.println("Invalid input. Please enter a valid number for the price.");
                                scanner.nextLine(); 
                                continue;
                            }
                
                            manager.AddRoom(roomNumber, roomType, price);
                        } catch (InputMismatchException e) {
                            System.out.println("Invalid input. Please enter the correct data types.");
                            scanner.nextLine();
                        }
                        break;
                    case 2:
                        System.out.print("Enter room number to remove: ");
                        roomNumber = scanner.nextInt();
                        manager.RemoveRoom(roomNumber);
                        break;
                    case 3:
                        System.out.print("Enter room number to edit: ");
                        int editRoomNumber = scanner.nextInt();
                        scanner.nextLine(); 

                        System.out.print("Enter new room type: ");
                        String newRoomType = scanner.nextLine();

                        System.out.print("Enter new room price: ");
                        double newPrice = scanner.nextDouble();

                        manager.EditRoom(editRoomNumber, newRoomType, newPrice);
                        break;
                    case 4:
                        manager.DisplayAllRooms();
                        break;
                    case 5:
                        return; // ออกจากเมนูจัดการ
                    default:
                        System.out.println("Invalid choice.");
                }
            } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a valid number.");
            scanner.nextLine();
            }
        }
    }

    private static void ViewCustomerStayHistory(Manager manager) {
        manager.DisplayReserveRoom();

    }

    private static void ViewHotelIncome(Manager manager) {
        manager.HotelIncome();
    }

    private static void printHeader(String message) {
        int totalWidth = 50; // ความกว้างทั้งหมด
        int paddingSize = (totalWidth - message.length()) / 2; 
        String padding = " ".repeat(paddingSize); // สร้างช่องว่าง
    
        System.out.println("\n" + "=".repeat(totalWidth));
        System.out.printf("%s%s%s%n", padding, message, padding); // แทรกช่องว่างด้านซ้ายขวา
        System.out.println("=".repeat(totalWidth));
    }
    

    public static void RoleReceptionist(){
        Scanner scanner = new Scanner(System.in);
        Receptionist somchai = new Receptionist("Somchai", "Jingjai", "0999999999", "somza@gmail.com", "rp-001");
        String bookingInput;

        while (true) {  
            try {
                System.out.println("[ 1 ] Check-In\n[ 2 ] Check-Out\n[ 3 ] Exit");
                System.out.print("Enter your choice: ");   
                int choiceMain = scanner.nextInt();
                scanner.nextLine();  
        
                if (choiceMain < 1 || choiceMain > 3) {
                    System.out.println("Invalid choice. Please enter a number between 1 and 3.");
                    continue; 
                }
        
                if (choiceMain == 3) {
                    System.out.println("Exiting...");
                    break;  
                }
        
                boolean exit = true; 
                while (exit) {
                    System.out.print("Enter booking id: ");
                    bookingInput = scanner.nextLine();
                    boolean isFound = somchai.findBookingById(bookingInput);
        
                    if (!isFound) {
                        System.out.println("Sorry, booking ID " + bookingInput + " not found.");
                        System.out.print("Please enter your booking number again (y/n): ");
                        bookingInput = scanner.nextLine();
                        if (bookingInput.equalsIgnoreCase("n")) {
                            exit = false;
                        }
                    } else {
                        switch (choiceMain) {
                            case 1:
                                checkIn(somchai, bookingInput); 
                                break;
                            case 2:
                                checkOut(somchai, bookingInput);
                                break;
                        }
                        exit = false;
                    }
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine(); 
            }
        }
        
    }//end RoleReceptionist
    

    public static void checkIn(Receptionist receptionist, String bookingIdCustomer){
        Scanner scanner = new Scanner(System.in);
        String confirm;
        receptionist.displayCustomerRoom(bookingIdCustomer);
        System.out.print("Confirm check-in(pressing 'y'): ");
        confirm = scanner.nextLine();
        if(confirm.equalsIgnoreCase("y")){
            receptionist.updateRoomStatus(bookingIdCustomer, true);
        }

    }//end checkIn

    public static void checkOut(Receptionist receptionist, String bookingIdCustomer){
        Scanner scanner = new Scanner(System.in);
        String confirm;
        
        receptionist.displayCustomerRoom(bookingIdCustomer);
        System.out.print("Confirm check-out(pressing 'y'): ");
        confirm = scanner.nextLine();

        if(confirm.equalsIgnoreCase("y")){
            receptionist.checkOut(bookingIdCustomer);
            System.out.println();
        
            receptionist.updateRoomStatus(bookingIdCustomer, false);
        }
    }//end checkOut


    public static void payment(Booking booking) {
        PaymentAndReceipt payment = new PaymentAndReceipt();
        double amount1 = 4000.0;  // ตัวอย่างจำนวนเงินที่ต้องชำระ
        String paymentMethod = "Credit Card";  // วิธีการชำระเงิน
        
        payment.processPayment(booking, amount1, paymentMethod);
    }
    public static void displayCalendar() {
        String[][] calendar = new String[5][7]; // ปฏิทิน 30 วัน
        int day = 1;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 7; j++) {
                if (day <= 30) {
                    // แสดงวันที่ปกติ
                    calendar[i][j] = String.format("%02d", day);
                    day++;
                } else {
                    calendar[i][j] = "  "; // วันที่นอกช่วง 30 วัน
                }
            }
        }

        // พิมพ์ปฏิทิน
        System.out.println(yellow +"Mon Tue Wed Thu Fri Sat Sun" + reset);
        for (String[] week : calendar) {
            for (String dayStr : week) {
                System.out.print(dayStr + "  ");
            }
            System.out.println();
        }
    }
//=========================================== color =========================================================
    private static final String reset = "\033[0m";    // รีเซ็ตสี
    private static final String green = "\033[32m";   // สีเขียว
    private static final String cyan = "\033[36m";    // สีฟ้าอ่อน
    private static final String red = "\033[31m";    // สีฟ้าอ่อน
    private static final String yellow = "\033[93m";    // สีฟ้าอ่อน
}