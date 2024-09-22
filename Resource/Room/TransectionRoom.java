
public class TransectionRoom {
    private Room room;
    private String statusRoom;


    public Room getRoom() {
        return room;
    }

    public void setRoom(String name){
        this.room.setName(name);
    }

    

    public TransectionRoom(Room room , String statusRoom){
        Room roomTest = new Room(room.getName(), room.getId());
        this.room = roomTest;
        this.statusRoom = statusRoom;
    }

}
