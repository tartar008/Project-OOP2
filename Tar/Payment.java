

public class Payment {
    private double amount;
    private String paymentType;
    private String creditCardNo;
    private String 


    // เมธอดคำนวณยอดรวม
    public double calculateTotalAmount(double taxRate) {
        return amount + (amount * taxRate); // คำนวณรวมภาษีมูลค่าเพิ่ม
    }

    // เมธอดสำหรับประมวลผลการชำระเงิน
    public void processPayment() {
        if (paymentType.equalsIgnoreCase("PromptPay")) {
            System.out.println("Processing PromptPay payment of " + calculateTotalAmount() + " THB.");
        } else if (paymentType.equalsIgnoreCase("Credit")) {
            System.out.println("Processing Credit Card payment of " + calculateTotalAmount() + " THB.");
        } else {
            System.out.println("Unknown payment method.");
        }
    }

    // เมธอดสำหรับคำนวณยอดรวมที่ต้องชำระ
    public void calculateTotalAmount() {

    }

}
