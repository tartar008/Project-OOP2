import java.time.LocalDate;
import src.bookings.ReserveRoom;
import src.rooms.TransectionRoom;

public class TestReserveMain {
    public static void main(String[] args) {
        ReserveRoom reserveRoom = new ReserveRoom();

        // สร้าง TransectionRoom ตัวอย่าง
        TransectionRoom room1 = new TransectionRoom();
        room1.getRoom().setRoomNumber(101);
        room1.getRoom().setType("Deluxe");
        room1.getRoom().setPrice(1200.00);

        TransectionRoom room2 = new TransectionRoom();
        room2.getRoom().setRoomNumber(102);
        room2.getRoom().setType("Standard");
        room2.getRoom().setPrice(800.00);

        // เพิ่มห้องเข้าใน reserveRoom
        reserveRoom.AddTransectionRoom(room1);
        reserveRoom.AddTransectionRoom(room2);

        // ทดสอบการจองห้อง
        LocalDate startDate = LocalDate.of(2024, 10, 2);
        LocalDate endDate = LocalDate.of(2024, 10, 5);
        for (TransectionRoom room : reserveRoom.getAvailableRooms(startDate, endDate)) {
            room.bookRoom(startDate, endDate);
        }

        // แสดงห้องพักที่ว่าง
        System.out.println("Available Rooms:");
        for (TransectionRoom availableRoom : reserveRoom.getAvailableRooms(startDate, endDate)) {
            System.out.println("Room Number: " + availableRoom.getRoom().getRoomNumber());
        }

        // ทดสอบการเขียน JSON
        String jsonFilePath = "roomBooking.json"; // เปลี่ยนเป็น path ที่ต้องการ
        reserveRoom.WriteJsonBooking(jsonFilePath);
        System.out.println("JSON Booking Written to " + jsonFilePath);

        // ทดสอบการอ่าน JSON
        ReserveRoom newReserveRoom = new ReserveRoom();
        try {
            newReserveRoom.readJsonBooking(jsonFilePath);
            System.out.println("JSON Booking Read Successfully.");
            System.out.println("Available Rooms after reading JSON:");
            for (TransectionRoom availableRoom : newReserveRoom.getAvailableRooms(startDate, endDate)) {
                System.out.println("Room Number: " + availableRoom.getRoom().getRoomNumber());
            }
        } catch (org.json.simple.parser.ParseException e) {
            System.out.println("Error reading JSON: " + e.getMessage());
        }
    }
}
