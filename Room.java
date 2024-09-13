public class Room {
    private int roomNumber;
    private String type;
    private double price;
    private boolean isBooked;

    public Room(int roomNumber, String type, double price) {
        this.roomNumber = roomNumber;
        this.type = type;
        this.price = price;
        this.isBooked = false;
    }

    public int getRoomNumber() {  // เพิ่ม getter สำหรับ roomNumber
        return roomNumber;
    }

    public boolean isBooked() {
        return isBooked;
    }

    public void bookRoom() {
        this.isBooked = true;
    }

    public void cancelBooking() {
        this.isBooked = false;
    }

    public String getRoomInfo() {
        return "Room Number: " + roomNumber + ", Type: " + type + ", Price: " + price + ", Booked: " + isBooked;
    }
}