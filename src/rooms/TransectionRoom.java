package src.rooms;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class TransectionRoom {
    private MasterRoom room;
    private Map<LocalDate, Boolean> availability;
   
    
    public TransectionRoom (){
        this.room = new MasterRoom();
        this.availability = new HashMap<>();
    }
    
    public TransectionRoom(MasterRoom room) {
        
        this.room = new MasterRoom(room.getRoomNumber(), room.getType(), room.getPrice());
        this.availability = new HashMap<>();

        for (int i = 0; i < 30; i++) {
            LocalDate date = LocalDate.now().plusDays(i);
            availability.put(date, true); 
        }
    }
  
    public boolean isAvailable(LocalDate date) {
        return availability.getOrDefault(date, true);
    }

    public Map<LocalDate, Boolean> getAvailable(){
        return availability;
    }

    public boolean bookRoom(LocalDate checkInDate, LocalDate checkOutDate) {
        for (LocalDate date = checkInDate; !date.isAfter(checkOutDate); date = date.plusDays(1)) {
            if (!isAvailable(date)) {
                System.out.println("Room is not available on " + date);
                return false; 
            }
        }
        for (LocalDate date = checkInDate; !date.isAfter(checkOutDate); date = date.plusDays(1)) {
            availability.put(date, false);
        }
        System.out.println("Room " + room.getRoomNumber() + " has been booked from " + checkInDate + " to " + checkOutDate);
        return true;
    }
 
    public void cancelBooking(LocalDate checkInDate, LocalDate checkOutDate) {
        for (LocalDate date = checkInDate; !date.isAfter(checkOutDate); date = date.plusDays(1)) {
            availability.put(date, true); 
        }
        System.out.println("Booking for room " + room.getRoomNumber() + " has been cancelled from " + checkInDate + " to " + checkOutDate);
    }

    public void displayAvailability() {
        System.out.println("Availability for Room " + room.getRoomNumber() + ":");
        for (Map.Entry<LocalDate, Boolean> entry : availability.entrySet()) {
            String status = entry.getValue() ? "Available" : "Booked";
            System.out.println("Date: " + entry.getKey() + " - " + status);
        }
    }

    public MasterRoom getRoom() {
        return room;
    }

    public void setRoom(MasterRoom room) {
        this.room = room;
    }

    public void setAvailabilityMap(Map<LocalDate, Boolean> availability) {
        this.availability = availability;
    }
    
    public void setAvailability(LocalDate date,  boolean status) {
        availability.put(date, status);
    }

    public void displayRoomInfo() {
        System.out.println("Transection Room: " + room.getRoomNumber() + ", Type: " + room.getType());
    }

    public void displayRoomStatus(boolean status) {
        String statusText = status ? "Available" : "Booked";
        System.out.println("Rooms with status: " + statusText);
        
        for (Map.Entry<LocalDate, Boolean> entry : availability.entrySet()) {
            if (entry.getValue() == status) {
                System.out.println("Date: " + entry.getKey() + " - Room " + room.getRoomNumber() + " is " + statusText);
            }
        }
    }


}
