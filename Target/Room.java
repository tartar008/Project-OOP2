import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class MasterRoom {
    private String roomNumber;
    private String type;
    private double price;

    // Constructor
    public MasterRoom(String roomNumber, String type, double price) {
        this.roomNumber = roomNumber;
        this.type = type;
        this.price = price;
    }

    // Getters and Setters for attributes
    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    // Method to print room info
    public void printInfo() {
        System.out.println("Room Number: " + roomNumber);
        System.out.println("Room Type: " + type);
        System.out.println("Price per Night: $" + price);
    }
   
}