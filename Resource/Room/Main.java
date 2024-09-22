
public class Main {
    public static void main(String[] args) {
        MasterRoom room1 = new MasterRoom("D1", "1111");
        // Room room2 = new Room("D2", "2222");

        TransectionRoom TSRoom = new TransectionRoom(room1, "pass");

        System.out.println("Room1 : " + room1.getName() + " " + room1);

        TSRoom.getRoom().setName("S1");

        System.out.println("Room1 : " + TSRoom.getRoom().getName() + " " + TSRoom.getRoom());

        System.out.println("Room1 : " + room1.getName() + " " + room1);
    }
}
