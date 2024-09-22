import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class TransectionRoom {
    private MasterRoom room;
    private Map<LocalDate, Boolean> availability; // สถานะห้องว่างตามวันที่

    public TransectionRoom (){}
    // Constructor เพื่อสร้าง TransectionRoom
    public TransectionRoom(MasterRoom room) {
        // สำเนา MasterRoom โดยใช้คอนสตรัคเตอร์ที่ถูกต้อง
        this.room = new MasterRoom(room.getRoomNumber(), room.getType(), room.getPrice());
        this.availability = new HashMap<>();

        // กำหนดสถานะความว่างของห้องเป็น true (ว่าง) สำหรับ 30 วันข้างหน้า
        for (int i = 0; i < 30; i++) {
            LocalDate date = LocalDate.now().plusDays(i);
            availability.put(date, true); // ว่าง
        }
    }


    
    // Method เพื่อเช็คห้องว่างในวันที่กำหนด
    public boolean isAvailable(LocalDate date) {
        return availability.getOrDefault(date, false); // คืนค่า true หากว่าง, false หากไม่ว่าง
    }

    // Method เพื่อทำการจองห้องในช่วงวันที่กำหนด
    public boolean bookRoom(LocalDate checkInDate, LocalDate checkOutDate) {
        for (LocalDate date = checkInDate; !date.isAfter(checkOutDate); date = date.plusDays(1)) {
            if (!isAvailable(date)) {
                System.out.println("Room is not available on " + date);
                return false; // ถ้าวันใดวันหนึ่งไม่ว่างให้หยุด
            }
        }

        // ถ้าว่างในทุกวัน ทำการจองห้อง
        for (LocalDate date = checkInDate; !date.isAfter(checkOutDate); date = date.plusDays(1)) {
            availability.put(date, false); // ตั้งค่าห้องเป็นไม่ว่าง
        }
        System.out.println("Room " + room.getRoomNumber() + " has been booked from " + checkInDate + " to " + checkOutDate);
        return true;
    }

    // Method เพื่อยกเลิกการจองห้องในช่วงวันที่กำหนด
    public void cancelBooking(LocalDate checkInDate, LocalDate checkOutDate) {
        for (LocalDate date = checkInDate; !date.isAfter(checkOutDate); date = date.plusDays(1)) {
            availability.put(date, true); // ตั้งค่าห้องเป็นว่าง
        }
        System.out.println("Booking for room " + room.getRoomNumber() + " has been cancelled from " + checkInDate + " to " + checkOutDate);
    }

    // Method เพื่อแสดงสถานะความว่างของห้อง
    public void displayAvailability() {
        System.out.println("Availability for Room " + room.getRoomNumber() + ":");
        for (Map.Entry<LocalDate, Boolean> entry : availability.entrySet()) {
            String status = entry.getValue() ? "Available" : "Booked";
            System.out.println("Date: " + entry.getKey() + " - " + status);
        }
    }

    // Getter สำหรับห้อง
    public MasterRoom getRoom() {
        return room;
    }

    // Setter สำหรับห้อง
    public void setRoom(MasterRoom room) {
        this.room = room;
    }

    // Getter สำหรับ availability
    public Map<LocalDate, Boolean> getAvailability() {
        return availability;
    }

    // Setter สำหรับ availability
    public void setAvailability(Map<LocalDate, Boolean> availability) {
        this.availability = availability;
    }

    public void displayRoomInfo() {
        System.out.println("Transection Room: " + room.getRoomNumber() + ", Type: " + room.getType());
    }
}
