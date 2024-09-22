    import java.time.LocalDate;
    import java.util.ArrayList;
    import java.util.HashMap;
    import java.util.List;
    import java.util.Map;

    public class ReserveRoom  {
        // private ArrayList<ReserveRoom> rooms;
        private Room room;
        private ArrayList<ReserveRoom> reserveRooms = new ArrayList<>();
        private Map<LocalDate, Boolean> availability = new HashMap<>();// เตรียมแผนที่เพื่อจัดการสถานะห้อง

        public ReserveRoom (){

        }
        public ReserveRoom(Room room) {
            //clone room
            Room tempRoom = new Room(room.getRoomNumber(), room.getType(), room.getPrice());
            this.room = tempRoom;
            this.reserveRooms.add(room);
            this.availability = new HashMap<>();
        }
        public String getRoomNumber(){
            return room.getRoomNumber();
        }
        // เช็คสถานะห้อง
        public boolean isAvailable(LocalDate date) {
            return availability.getOrDefault(date, true); // true = available, false = unavailable
        }

        // เปลี่ยนสถานะห้อง
        public void setRoomStatus(LocalDate date, boolean status) {
            availability.put(date, status); // Set ห้องว่างหรือไม่ว่างตามวันที่
        }

        // แสดงข้อมูลห้อง
        public void printInfo() {
            System.out.println("Room " + room.getRoomNumber() + " (" + room.getType() + ")");
        }

        public List<ReserveRoom> getAvailableRooms(LocalDate startDate, LocalDate endDate) {
            List<Room> availableRooms = new ArrayList<>();
            for (Room room : room) {
                boolean isAvailable = true;
                for (LocalDate date = startDate; date.isBefore(endDate) || date.equals(endDate); date = date.plusDays(1)) {
                    if (!room.isAvailable(date)) {
                        isAvailable = false;
                        break;
                    }
                }
                if (isAvailable) {
                    availableRooms.add(room);
                }
            }
            return availableRooms;
        }

    
        // public ArrayList<Room> getRooms() {
        //     return rooms;
        // }


        // public void setRooms(Room room) {
        //     this.rooms.add(room);
        // }

        // public boolean isAvailable(LocalDate date) {
        //     return availability.getOrDefault(date, true);  // true = available
        // }


        public void book(LocalDate startDate, LocalDate endDate) {
            for (LocalDate date = startDate; date.isBefore(endDate) || date.equals(endDate); date = date.plusDays(1)) {
                availability.put(date, false);
            }
        }
        
        

        //แสดงปฎิทิน
        public void displayCalendar(boolean[] availability) {
            String[][] calendar = new String[5][7]; // Simplified for a 30-day month
            int day = 1;
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 7; j++) {
                    if (day <= 30) {
                        if (availability[day - 1]) {
                            calendar[i][j] = String.format("%02d", day); // Available day
                        } else {
                            calendar[i][j] = "XX"; // Booked day
                        }
                        day++;
                    } else {
                        calendar[i][j] = "  ";
                    }
                }
            }

            // Print calendar
            System.out.println("Mon Tue Wed Thu Fri Sat Sun");
            for (String[] week : calendar) {
                for (String dayStr : week) {
                    System.out.print(dayStr + "  ");
                }
                System.out.println();
            }
        }
    }
