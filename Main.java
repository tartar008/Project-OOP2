public class Main {
    public static void main(String[] args) {
        Hotel hotel = new Hotel();
        Customer customer1 = new Customer("John Doe", "john@example.com", "1234567890");

        // แสดงห้องพักทั้งหมด
        System.out.println("Available Rooms:");
        hotel.displayRooms();

        // จองห้องพัก
        hotel.bookRoom(101, customer1);

        // แสดงห้องพักหลังการจอง
        System.out.println("\nAvailable Rooms After Booking:");
        hotel.displayRooms();

        // ยกเลิกการจอง
        hotel.cancelRoom(101);
        System.out.println("sdfgfdfghgdfghgfdfg");
        // แสดงห้องพักหลังการยกเลิก
        System.out.println("\nAvailable Rooms After Cancellation:");
        hotel.displayRooms();

        System.out.println("Hello OOP");
    }
}
