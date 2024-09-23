import java.util.ArrayList;
import java.util.Scanner;

public class MainOnline {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // สร้างห้อง Master
        ArrayList<MasterRoom> masterRooms = setMasterRooms();

        // สร้าง Datebase สำหรับเก็บ Room Transaction


        // ทำการ นำ Room Transaction

        System.out.println("\nWelcome to the Hotel Online Booking System!");

        // ส่งผ่าน masterRooms ไปยังฟังก์ชัน handleOnlineBooking
        handleOnlineBooking(masterRooms);
    }

    // Handle online booking process
    private static void handleOnlineBooking(ArrayList<MasterRoom> masterRooms) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("You chose Online booking.");

        // วนสร้างตาราง
        System.out.println("Mon Tue Wed Thu Fri Sat Sun");
        System.out.println("01  02  03  04  05  06  07");
        System.out.println("08  09  10  11  12  13  14");
        System.out.println("15  16  17  18  19  20  21");
        System.out.println("22  23  24  25  26  27  28");
        System.out.println("29  30");

        System.out.print("Select the CheckIn (1-30) \n[0] go back\n>>> ");
        int StartDay = scanner.nextInt();

        int daydown = 30 - (30 - StartDay);

        System.out.print("Select the CheckOut (" + daydown + "-30) \n[0] go back\n>>> ");
        int EndDay = scanner.nextInt();
        System.out.print("Enter number of rooms >>> ");
        int NumDays = scanner.nextInt();

        for (int days = 1; days <= NumDays; days++) {
            System.out.println("\tAvailable type rooms");
            for (MasterRoom masterRoom : masterRooms) { 
                System.out.println(masterRoom);
            }
            System.out.print("Enter type room >>> ");
            int TypeRoom = scanner.nextInt();
            System.out.print("Enter first name guest >>> ");
            String firstName = scanner.next();
            System.out.print("Enter last name guest >>> ");
            String lastName = scanner.next();
        }
    }

    // Initialize master rooms
    public static ArrayList<MasterRoom> setMasterRooms() {
        ArrayList<MasterRoom> masterRooms = new ArrayList<>();
        masterRooms.add(new MasterRoom(101, "Standard", 1000));
        masterRooms.add(new MasterRoom(102, "Deluxe", 2000));
        masterRooms.add(new MasterRoom(103, "Suite", 3000));
        return masterRooms;
    }
}
