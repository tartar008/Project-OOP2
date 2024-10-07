import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReserveRoom {
    private ArrayList<TransectionRoom> transectionRooms;
    private Map<LocalDate, Boolean> availability; 

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

    public boolean isAvailable(LocalDate date) {
        return availability.getOrDefault(date, true); 
    }

    
    public void setRoomStatus(LocalDate date, boolean status) {
        availability.put(date, status);
    }

    
    public void book(LocalDate startDate, LocalDate endDate) {
        for (LocalDate date = startDate; date.isBefore(endDate) || date.equals(endDate); date = date.plusDays(1)) {
            availability.put(date, false); 
        }
        
    }
    //คืนค่าห้องพักทั้งหมดที่ว่างทุกประเภทห้อง
    public List<TransectionRoom> getAvailableRooms(LocalDate startDate, LocalDate endDate) {
        List<TransectionRoom> availableRooms = new ArrayList<>();
        System.out.println("startDate : " + startDate);
        System.out.println("endDate : " + endDate);
        for (TransectionRoom TSroom : transectionRooms) {
            boolean isRoomAvailable = true;
            for (LocalDate date = startDate; date.isBefore(endDate) || date.equals(endDate); date = date.plusDays(1)) {
                
                if (!TSroom.isAvailable(date)) {
                    System.out.println("TSroomCheckAvailable: " + TSroom.isAvailable(date));
                    isRoomAvailable = false;
                    break;
                }
            }
            if (isRoomAvailable) {
                availableRooms.add(TSroom);
            }
        }
        return availableRooms;
    }
    //คืนค่าห้องพักทั้งหมดที่ว่างตามประเภทห้องที่เลือก
    public List<TransectionRoom> getAvailableRoomsByType(String roomType, LocalDate checkInDate, LocalDate checkOutDate) {
        List<TransectionRoom> availableRooms = new ArrayList<>();
        List<TransectionRoom> allRooms = transectionRooms; // ใช้ transectionRooms แทน reserveRooms
    
        for (TransectionRoom room : allRooms) {
            // ตรวจสอบว่าประเภทห้องตรงตามที่ต้องการ
            if (room.getRoom().getType().equals(roomType)) {
                boolean isRoomAvailable = true;
                // ตรวจสอบความว่างในช่วงวันที่เช็คอินและเช็คเอาท์
                for (LocalDate date = checkInDate; date.isBefore(checkOutDate) || date.equals(checkOutDate); date = date.plusDays(1)) {
                    if (!room.isAvailable(date)) {
                        isRoomAvailable = false;
                        break;
                    }
                }
                // ถ้าห้องว่างในทุกวันที่ต้องการ ก็เพิ่มห้องลงในรายการ
                if (isRoomAvailable) {
                    availableRooms.add(room);
                }
            }
        }
        return availableRooms;
    }
    
    public void reduceRoomAvailable(TransectionRoom room,LocalDate startDate, LocalDate endDate){
        for (LocalDate date = startDate; date.isBefore(endDate) || date.equals(endDate); date = date.plusDays(1)) {
            room.setAvailability(date, false); // เปลี่ยนสถานะห้องในวันที่ถูกจองเป็นไม่ว่าง
        }
    }
    public void displayCalendar() {
        String[][] calendar = new String[5][7]; 
        int day = 1;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 7; j++) {
                if (day <= 30) {
                    LocalDate currentDate = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), day);
                    if (isAvailable(currentDate)) {
                        calendar[i][j] = String.format("%02d", day); 
                    } else {
                        calendar[i][j] = "XX"; 
                    }
                    day++;
                } else {
                    calendar[i][j] = "  "; 
                }
            }
        }

        
        System.out.println("Mon Tue Wed Thu Fri Sat Sun");
        for (String[] week : calendar) {
            for (String dayStr : week) {
                System.out.print(dayStr + "  ");
            }
            System.out.println();
        }
    }


    
}
