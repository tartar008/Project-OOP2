package src.rooms;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class TransectionRoom {
    private MasterRoom room;
    private Map<LocalDate, Boolean> availability;
    private boolean isOccupied;

    public TransectionRoom (){
        this.room = new MasterRoom();
        this.availability = new HashMap<>();
        this.isOccupied = false;

    }
    
    public TransectionRoom(MasterRoom room) {
        
        this.room = new MasterRoom(room.getRoomNumber(), room.getType(), room.getPrice());
        this.availability = new HashMap<>();
        this.isOccupied = false;
        for (int day = 1; day <= 31; day++) {
            LocalDate date = LocalDate.of(2024, 10, day);
            availability.put(date, true);
        }
    }


  
    public boolean getIsOccupied() {
        return isOccupied;
    }

    public void setIsOccupied(boolean isOccupied) {
        this.isOccupied = isOccupied;
    }

    public boolean isAvailable(LocalDate date) {
        return availability.getOrDefault(date, true);
    }

    public Map<LocalDate, Boolean> getAvailable(){
        return availability;
    }

    public boolean isRoomAvailableForPeriod(LocalDate checkInDate, LocalDate checkOutDate) {
        for (LocalDate date = checkInDate; !date.isAfter(checkOutDate); date = date.plusDays(1)) {
            if (!isAvailable(date)) {
                return false;
            }
        }
        return true;
    }

    public boolean bookRoom(LocalDate checkInDate, LocalDate checkOutDate) { //check-in
        if (!isRoomAvailableForPeriod(checkInDate, checkOutDate)) {
        System.out.println("Room is not available in the selected period.");
        return false;
        }
        for (LocalDate date = checkInDate; !date.isAfter(checkOutDate); date = date.plusDays(1)) {
            availability.put(date, false);
        }
        System.out.println("Room " + room.getRoomNumber() + " has been booked from " + checkInDate + " to " + checkOutDate);
        return true;
    }
 
    public void cancelBooking(LocalDate checkInDate, LocalDate checkOutDate) { //
        for (LocalDate date = checkInDate; !date.isAfter(checkOutDate); date = date.plusDays(1)) {
            availability.put(date, true); 
        }
        System.out.println("Booking for room " + room.getRoomNumber() + " has been cancelled from " + checkInDate + " to " + checkOutDate);
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