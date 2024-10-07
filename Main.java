import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // สร้างผู้จัดการ และ สร้างห้องขึ้นมาผ่านผู้จัดการ
        Manager manager = new Manager();
        ArrayList<MasterRoom> rooms = manager.getRooms();

        // สร้าง TransectionRoom สำหรับเก็บ MasterRoom
        ReserveRoom reserveRoom = new ReserveRoom();
        for (MasterRoom runRoom : rooms) {
            TransectionRoom transectionRoom = new TransectionRoom(runRoom);
            reserveRoom.AddTransectionRoom(transectionRoom);
        }

        // นำ TransectionRoom เก็บเข้า JSON -----[ยังไม่ทำ]

        // วนลูปหลักเพื่อให้ผู้ใช้เลือกทำงานต่อ
        while (true) {
            printHeader("Welcome to the Hotel Booking System!");
            System.out.println("Choose your role:");
            System.out.println("1. Customer");
            System.out.println("2. Employee\t");
            System.out.println("3. Exit \t");
            System.out.print(">>> ");
            int chooseRole = scanner.nextInt();

            if (chooseRole == 1) {
                User(reserveRoom);
            } else if (chooseRole == 2) {
                RoleManager(manager);
            }

        }
    }

    public static void User(ReserveRoom reserveRoom) {
        Scanner scanner = new Scanner(System.in);
        printHeader("Choose your booking method:");
        System.out.println("1. Walk-in");
        System.out.println("2. Online");
        System.out.println("3. Exit");
        System.out.print("Enter your method: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                System.out.println(">> WALK IN <<");
                break;
            case 2:
                handleOnlineBooking(reserveRoom);
                break;
            case 3:
                System.out.println("Exiting...");
                break;
            default:
                System.out.println("Invalid choice, please try again.");
                break;
        }
    }

    private static void handleOnlineBooking(ReserveRoom reserveRooms) {
        Scanner scanner = new Scanner(System.in);
        printHeader("You chose Online booking.");

        reserveRooms.displayCalendar();
        LocalDate checkInDate, checkOutDate;

        while (true) {
            System.out.print("Enter check-in date (day): ");
            int startDay = scanner.nextInt();
            System.out.print("Enter check-out date (day): ");
            int endDay = scanner.nextInt();

            checkInDate = LocalDate.of(2024, 9, startDay);
            checkOutDate = LocalDate.of(2024, 9, endDay);

            if (checkInDate.isAfter(checkOutDate)) {
                System.out.println("Check-in date must be before check-out date. Please try again.");
                continue;
            }
            break;
        }

        boolean confirmBooking = true;
        List<TransectionRoom> confirmRooms = new ArrayList<>();
        ArrayList<Customer> guests = new ArrayList<>();

        Customer agent = null;

        while (confirmBooking) {
            List<TransectionRoom> availableRooms = reserveRooms.getAvailableRooms(checkInDate, checkOutDate);
            if (availableRooms.isEmpty()) {
                System.out.println("No rooms available for the selected dates. Please choose another date.");
                return;
            }

            System.out.println("Available rooms: " + availableRooms.size());
            List<TransectionRoom> standards = reserveRooms.getAvailableRoomsByType("Standard", checkInDate,
                    checkOutDate);
            List<TransectionRoom> family = reserveRooms.getAvailableRoomsByType("Family", checkInDate, checkOutDate);
            List<TransectionRoom> honeymoon = reserveRooms.getAvailableRoomsByType("Honeymoon", checkInDate,
                    checkOutDate);

            // แสดงผลห้องที่ว่าง
            displayAvailableRooms(standards, family, honeymoon);

            boolean limitRoom = true;

            int roomType = 0;
            int numberOfRooms = 0;
            while (limitRoom) {
                roomType = selectRoomType(scanner, standards, family, honeymoon);
                List<TransectionRoom> selectedRooms = getSelectedRooms(roomType, standards, family, honeymoon);

                System.out.print("Enter number of room(s): ");
                numberOfRooms = scanner.nextInt();
                scanner.nextLine();

                if (numberOfRooms <= selectedRooms.size()) {
                    limitRoom = false;

                    for (int i = 0; i < numberOfRooms; i++) {
                        confirmRooms.add(new TransectionRoom(selectedRooms.get(i).getRoom()));
                    }
                } else {
                    System.out.println("You chose more than available!!");
                }
            }

            // Collect guest details
            collectGuestDetails(scanner, confirmRooms.size(), guests);

            // Collect booking agent details
            agent = collectBookingAgentDetails(scanner, guests);

            Booking booking = new Booking(agent, guests, confirmRooms, numberOfRooms, roomType, checkInDate,
                    checkOutDate);

            System.out.print("Complete Booking (Y/N): ");
            String completeBooking = scanner.nextLine();

            if (completeBooking.equalsIgnoreCase("Y")) {
                for (TransectionRoom room : confirmRooms) {
                    reserveRooms.reduceRoomAvailable(room, checkInDate, checkOutDate);
                }
                booking.confirmBooking();
                List<TransectionRoom> availableRoomsAfterBooking = reserveRooms.getAvailableRooms(checkInDate,
                        checkOutDate);
                System.out.println("Available rooms after booking: " + availableRoomsAfterBooking.size());
            } else {
                booking.cancelBooking();
            }

            booking.bookingList();

            if (completeBooking.equalsIgnoreCase("Y")) {
                break;
            }
        }
    }

    private static void displayAvailableRooms(List<TransectionRoom> standards, List<TransectionRoom> family,
            List<TransectionRoom> honeymoon) {
        printHeader("Available Rooms");
        System.out.println("Available Standard Rooms: " + standards.size());
        System.out.println("Available Family Rooms: " + family.size());
        System.out.println("Available Honeymoon Rooms: " + honeymoon.size());
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
        return scanner.nextInt();
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

    private static void collectGuestDetails(Scanner scanner, int numberOfRooms, ArrayList<Customer> guests) {
        System.out.println("> Guest Details");
        for (int i = 0; i < numberOfRooms; i++) {
            System.out.println("Guest " + (i + 1));
            System.out.print("Enter First name: ");
            String firstName = scanner.nextLine();
            System.out.print("Enter Last name: ");
            String lastName = scanner.nextLine();
            guests.add(new Customer(firstName, lastName, false));
        }
    }

    private static Customer collectBookingAgentDetails(Scanner scanner, ArrayList<Customer> guests) {
        Customer agent = null;
        System.out.print("> Contact Details\nI am booking for someone else? (Y/N): ");
        String isBooker = scanner.nextLine();

        if (isBooker.equalsIgnoreCase("Y")) {
            System.out.print("Enter First name: ");
            String firstName = scanner.nextLine();
            System.out.print("Enter Last name: ");
            String lastName = scanner.nextLine();
            System.out.print("Enter Phone number: ");
            String phoneNum = scanner.nextLine();
            System.out.print("Enter Email: ");
            String email = scanner.nextLine();
            agent = new Customer(firstName, lastName, phoneNum, email, true);
        } else {
            System.out.print("Enter Phone number: ");
            String phoneNum = scanner.nextLine();
            System.out.print("Enter Email: ");
            String email = scanner.nextLine();

            agent = guests.get(0);
            agent.setPhoneNumber(phoneNum);
            agent.setEmail(email);
            agent.setAgent(true);
        }
        return agent;
    }

    private static void RoleManager(Manager manager) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== Hotel Management System ===");
            System.out.println("1. Manage Rooms");
            System.out.println("2. View Customer Stay History");
            System.out.println("3. View Hotel Income");
            System.out.println("4. Exit");
            System.out.print(">>> ");

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

    private static void ManageRooms(Manager manager) {
    }

    private static void ViewCustomerStayHistory(Manager manager) {
        manager.DisplayReserveRoom();

    }

    private static void ViewHotelIncome(Manager manager) {
        manager.HotelIncome();
    }

    private static void printHeader(String message) {
        System.out.println("\n" + "=".repeat(50));
        System.out.printf("%-48s%s%n", message, "=");
        System.out.println("=".repeat(50));
    }

}
