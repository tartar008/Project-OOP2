import java.util.Date;

public class Booking {
    private Customer customer;
    private MasterRoom room;
    private Date checkInDate;
    private Date checkOutDate;

    public Booking(Customer customer, MasterRoom room, Date checkInDate, Date checkOutDate) {
        this.customer = customer;
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        room.setAvailable(false); // ห้องจะไม่ว่างเมื่อจอง
    }

    public Customer getCustomer() {
        return customer;
    }

    public MasterRoom getRoom() {
        return room;
    }

    public Date getCheckInDate() {
        return checkInDate;
    }

    public Date getCheckOutDate() {
        return checkOutDate;
    }

    public void cancelBooking() {
        room.setAvailable(true); // ยกเลิกการจอง ห้องจะกลับมาว่าง
    }

    @Override
    public String toString() {
        return "Booking for: " + customer.getName() + ", Room: " + room.getRoomNumber() + ", Check-in: " + checkInDate + ", Check-out: " + checkOutDate;
    }
}
