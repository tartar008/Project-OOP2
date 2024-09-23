import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReserveRoom {
    private ArrayList<TransectionRoom> transectionRooms;
    private Map<LocalDate, Boolean> availability; // สถานะห้องว่าง/ไม่ว่างตามวันที่

    public ReserveRoom() {
        this.transectionRooms = new ArrayList<>();
        this.availability = new HashMap<>();
    }


    public void AddTransectionRoom(TransectionRoom transectionRoom){
        transectionRooms.add(transectionRoom);
    }

    
    
    public ArrayList<TransectionRoom> getTransectionRoom(){
        return transectionRooms;
    }

    // public String getRoomNumber(){
    //     return transectionRoom.getRoom().getRoomNumber();
    // }


    // ตรวจสอบว่าวันที่ที่กำหนดห้องว่างหรือไม่
    public boolean isAvailable(LocalDate date) {
        return availability.getOrDefault(date, true); // ถ้าไม่มีข้อมูลในวันนั้นให้ถือว่าว่าง
    }

    // ตั้งค่าสถานะห้องในวันที่ที่กำหนด (ว่างหรือไม่ว่าง)
    public void setRoomStatus(LocalDate date, boolean status) {
        availability.put(date, status);
    }

    // จองห้องในช่วงวันที่กำหนด (startDate ถึง endDate)
    public void book(LocalDate startDate, LocalDate endDate) {
        for (LocalDate date = startDate; date.isBefore(endDate) || date.equals(endDate); date = date.plusDays(1)) {
            availability.put(date, false); // เปลี่ยนสถานะห้องเป็นไม่ว่างในช่วงวันที่ที่จอง
        }
    }

    // ดึงรายการห้องที่ว่างในช่วงวันที่กำหนด
    public List<TransectionRoom> getAvailableRooms(LocalDate startDate, LocalDate endDate) {
        List<TransectionRoom> availableRooms = new ArrayList<>();
        System.out.println("startDate : " + startDate);
        System.out.println("endDate : " + endDate);
        for (TransectionRoom TSroom : transectionRooms) {
            boolean isRoomAvailable = true;
            for (LocalDate date = startDate; date.isBefore(endDate) || date.equals(endDate); date = date.plusDays(1)) {
                // System.out.println("TSroom : " + TSroom.getAvailability().entrySet().);
                if (!TSroom.isAvailable(date)) {
                    System.out.println("TSroomCheckAvailable: " + TSroom.isAvailable(date));
                    isRoomAvailable = false;
                    break;
                }
            }
            if (isRoomAvailable) {
                availableRooms.add(TSroom); // เพิ่มห้องที่ว่างในรายการ
            }
        }
        return availableRooms;
    }

    // แสดงปฏิทินสถานะการจองห้อง
    public void displayCalendar() {
        String[][] calendar = new String[5][7]; // ปฏิทิน 30 วัน
        int day = 1;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 7; j++) {
                if (day <= 30) {
                    LocalDate currentDate = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), day);
                    if (isAvailable(currentDate)) {
                        calendar[i][j] = String.format("%02d", day); // วันที่ว่าง
                    } else {
                        calendar[i][j] = "XX"; // วันที่ไม่ว่าง
                    }
                    day++;
                } else {
                    calendar[i][j] = "  "; // วันที่นอกช่วง 30 วัน
                }
            }
        }

        // พิมพ์ปฏิทิน
        System.out.println("Mon Tue Wed Thu Fri Sat Sun");
        for (String[] week : calendar) {
            for (String dayStr : week) {
                System.out.print(dayStr + "  ");
            }
            System.out.println();
        }
    }
}
