import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Hotel hotel = new Hotel();

        while (true) {
            System.out.println("\nWelcome to the Hotel Booking System!");
            System.out.println("Choose your booking method:");
            System.out.println("1. Walk-in");
            System.out.println("2. Online");
            System.out.println("3. Exit");

            int choice = scanner.nextInt();
            if (choice == 1) {
                handleWalkInBooking(hotel, scanner);
            } else if (choice == 2) {
                handleOnlineBooking(hotel, scanner);
            } else if (choice == 3) {
                System.out.println("Thank you for using the Hotel Booking System. Goodbye!");
                break;
            } else {
                System.out.println("Invalid choice! Please choose again.");
            }
        }

        scanner.close();
    }

    private static void handleWalkInBooking(Hotel hotel, Scanner scanner) {
        System.out.println("You chose Walk-in booking.");
    
        while (true) {
            System.out.println("Enter your arrival day (1-30): ");
            int arrivalDay = scanner.nextInt();
            scanner.nextLine();
    
            System.out.println("Arrival day entered: " + arrivalDay);
            
            System.out.println("Calculating...");
            // Show available check-out dates based on room availability
            LocalDate checkInDate = LocalDate.of(2024, 9, arrivalDay);
            LocalDate maxStayDate = hotel.getMaxStayDate(checkInDate); // Calculate max possible stay
    
            System.out.println("You can stay until " + maxStayDate + " based on current room availability.");
    
            System.out.println("How many nights would you like to stay (1-" + (maxStayDate.getDayOfMonth() - arrivalDay + 1) + ")? Enter '0' to go back:");
            int nights = scanner.nextInt();
    
            if (nights == 0) {
                break; // User goes back to arrival day selection
            }
    
            LocalDate checkOutDate = checkInDate.plusDays(nights);
    
            List<Room> availableRooms = hotel.getAvailableRooms(checkInDate, checkOutDate);
    
            if (availableRooms.isEmpty()) {
                System.out.println("Sorry, no rooms are available for the selected dates.");
            } else {
                System.out.println("Available rooms:");
                for (Room room : availableRooms) {
                    System.out.println("Room " + room.getRoomNumber() + " (" + room.getType() + ")");
                }
    
                System.out.println("Select a room number to book, or enter '0' to go back:");
                int roomNumber = scanner.nextInt();
    
                if (roomNumber == 0) {
                    continue; // Go back to room selection
                }
    
                for (Room room : availableRooms) {
                    if (room.getRoomNumber() == roomNumber) {
                        room.book(checkInDate, checkOutDate);
                        System.out.println("Room " + roomNumber + " has been booked successfully from " + checkInDate + " to " + checkOutDate + ".");
                    }
                }
                break; // Exit after successful booking
            }
        }
    }
    

    private static void handleOnlineBooking(Hotel hotel, Scanner scanner) {
        while (true) {
            System.out.println("You chose Online booking.");
            
            // Display calendar (simplified as boolean array)
            boolean[] roomAvailability = new boolean[30];
            for (int i = 0; i < 30; i++) {
                roomAvailability[i] = true; // Assume all rooms are available
            }

            hotel.displayCalendar(roomAvailability);

            System.out.println("Select the start day (1-30) or enter '0' to go back to the main menu: ");
            int startDay = scanner.nextInt();

            if (startDay == 0) {
                // User chooses to go back to main menu
                break;
            }

            System.out.println("How many nights would you like to book? (Enter '0' to go back):");
            int nights = scanner.nextInt();

            if (nights == 0) {
                // User chooses to go back to the start day selection
                continue;
            }

            LocalDate checkInDate = LocalDate.of(2024, 9, startDay);
            LocalDate checkOutDate = checkInDate.plusDays(nights);

            List<Room> availableRooms = hotel.getAvailableRooms(checkInDate, checkOutDate);

            if (availableRooms.isEmpty()) {
                System.out.println("Sorry, no rooms are available for the selected dates.");
            } else {
                System.out.println("Available rooms:");
                for (Room room : availableRooms) {
                    System.out.println("Room " + room.getRoomNumber() + " (" + room.getType() + ")");
                }

                System.out.println("Select a room number to book, or enter '0' to go back:");
                int roomNumber = scanner.nextInt();

                if (roomNumber == 0) {
                    // User chooses to go back to the previous step
                    continue;
                }

                for (Room room : availableRooms) {
                    if (room.getRoomNumber() == roomNumber) {
                        room.book(checkInDate, checkOutDate);
                        System.out.println("Room " + roomNumber + " has been booked successfully from " + checkInDate + " to " + checkOutDate + ".");
                    }
                }
            }
        }
    }
}
