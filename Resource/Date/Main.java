import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;


public class Main {
    public static void main(String[] args) {
        // สร้างห้อง
        Room room101 = new Room("Standard", 101);

        // ตรวจสอบความพร้อมของห้องในวันที่ 2024-09-15
        LocalDate dateToCheck = LocalDate.of(2024, 9, 15);
        System.out.println("Room 101 available on " + dateToCheck + ": " + room101.isAvailable(dateToCheck)); // คาดว่า true

        // จองห้องตั้งแต่วันที่ 2024-09-15 ถึง 2024-09-20
        room101.book(LocalDate.of(2024, 9, 15), LocalDate.of(2024, 9, 20));

        // ตรวจสอบความพร้อมของห้องอีกครั้งในวันที่ 2024-09-15
        System.out.println("Room 101 available on " + dateToCheck + ": " + room101.isAvailable(dateToCheck)); // คาดว่า false
    }
}
