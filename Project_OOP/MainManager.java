import java.util.Scanner;

public class MainManager {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Manager manager = new Manager();

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

    public static void ManageRooms(Manager manager) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1. Add Room");
            System.out.println("2. Remove Room");
            System.out.println("3. Edit Room [Log]");
            System.out.println("4. View All Rooms");
            System.out.println("5. Exit");
            System.out.println(">>> ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter room number: ");
                    int roomNumber = scanner.nextInt();
                    System.out.print("Enter room type (Standard/Family/Honeymoon): ");
                    String roomType = scanner.next();
                    System.out.print("Enter room price: ");
                    double price = scanner.nextDouble();
                    manager.AddRoom(roomNumber, roomType, price);
                    break;
                case 2:
                    System.out.print("Enter room number to remove: ");
                    roomNumber = scanner.nextInt();
                    manager.RemoveRoom(roomNumber);
                    break;
                case 3:
                    System.out.print("Enter room number to edit: ");
                    int editRoomNumber = scanner.nextInt();
                    scanner.nextLine(); // รับค่า new line

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
        }
    }

    public static void ViewCustomerStayHistory(Manager manager) {
        manager.DisplayReserveRoom();

    }

    public static void ViewHotelIncome(Manager manager) {
        manager.HotelIncome();
    }
}
