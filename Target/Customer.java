import java.util.HashMap;

public class Customer extends Person{
    HashMap<Integer,String> guest = new HashMap<>();

    public Customer(String firstName, String lastName, String phoneNumber, String email){
        super(firstName, lastName, phoneNumber, email);
    }

    public void setGuest(int roomNumber , String guestName){
        guest.put(roomNumber, guestName); //roomNumber เป็น key, guestName เป็น value
    }

    // Method สำหรับดึงข้อมูล guest ทั้งหมด
    public HashMap<Integer, String> getGuests() {
        return guest;
    }
    // Method สำหรับดึงข้อมูล guest โดยใช้หมายเลขห้องพัก
    public String getGuestByRoomNumber(int roomNumber) {
        return guest.get(roomNumber);
    }

    public void customerInfo(){
        System.out.println("Fullname: " + getFirstName()+" "+getLastName());
    }

}
