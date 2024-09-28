public class Main {
    public static void main(String[] args) {
        // สร้างพนักงาน Manager
        Manager manager = new Manager("M001", "John Doe", 50000, "Day", "john.doe@example.com");

        // สร้างพนักงาน Receptionist
        Receptionist receptionist = new Receptionist("R001", "Jane Smith", 25000, "Night", "jane.smith@example.com");

        // Manager อัปเดตข้อมูลการติดต่อได้
        System.out.println("Before update: " + manager.getContactInfo());
        manager.updateInfo("john.newemail@example.com");
        System.out.println("After update: " + manager.getContactInfo());

        // Receptionist ไม่สามารถอัปเดตข้อมูลการติดต่อได้
        // System.out.println("Receptionist contact info: " +
        // receptionist.getContactInfo());

        System.out.println("Before update: " + receptionist.getContactInfo());
        receptionist.updateInfo("Jane.newemail@example.com");
        receptionist.updateContactInfo
        System.out.println("After update: " + receptionist.getContactInfo());
    }
}
