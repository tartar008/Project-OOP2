public class Guest extends Customer{
    private String roomNumber;
    public Guest(String firstName, String lastName, String phoneNumber, String email, String roomNumber){
        super(firstName, lastName, phoneNumber, email);
        this.roomNumber = roomNumber;
    }
    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }
}
