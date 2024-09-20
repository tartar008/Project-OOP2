public class ReserveRoom  {
    private ArrayList<Room> rooms;
    private Map<LocalDate, Boolean> availability = new HashMap<>();

    public ReserveRoom(String type, int roomNumber) {
        this.type = type;
        this.roomNumber = roomNumber;
    }


    public int getRoomNumber() {
        return roomNumber;
    }

    public boolean isAvailable(LocalDate date) {
        return availability.getOrDefault(date, true);  // true = available
    }

    public void book(LocalDate startDate, LocalDate endDate) {
        for (LocalDate date = startDate; date.isBefore(endDate) || date.equals(endDate); date = date.plusDays(1)) {
            availability.put(date, false);
        }
    }
}
