import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("......");
        System.out.println("......Hello.......");
        System.out.println("......welcome.......");
        System.out.println("\nWelcome to the Hotel Booking System!");
        System.out.println("Choose your role:");
        System.out.println("1. User");
        System.out.println("2. Employee [log]");
        System.out.println("2. receptionist  [log]");
        System.out.println("3. Manager\t [log]");
        System.out.println("4. Exit \t [log]");
        int ChooseRole = scanner.nextInt();

        if (ChooseRole == 1) {
            User();

        }else if (ChooseRole == 2) {
            Employee();

        }else {
            main(args);
        }
    }

    public static void User(){
        Scanner scanner = new Scanner(System.in);

        System.out.println("Choose your booking method:");
        System.out.println("1. Walk-in");
        System.out.println("2. Online");
        System.out.println("3. Exit");
        int choice = scanner.nextInt();
        
        if (choice == 1) {

        } else if (choice == 2) {

        } else if (choice == 3) {

        } else {
            
        }
    }


    public static void Employee(){
        Scanner scanner = new Scanner(System.in);

        System.out.println("Choose your role:");
        System.out.println("1. receptionist  [log]");
        System.out.println("2. Manager\t [log]");
        System.out.println("3. Exit \t [log]");
        int ChooseRole = scanner.nextInt();

        if (ChooseRole == 1) {

        }
        else if (ChooseRole == 2) {

        } else {
            main(args);
        }
    }



}



