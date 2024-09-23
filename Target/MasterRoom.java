import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class MasterRoom {
    private int roomNumber;
    private String type;
    private double price;

    // Constructor
    public MasterRoom(int roomNumber, String type, double price) {
        this.roomNumber = roomNumber;
        this.type = type;
        this.price = price;
    }

    // Getters and Setters for attributes
    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
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