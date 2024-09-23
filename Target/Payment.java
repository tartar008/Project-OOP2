public class Payment {
    private double amount;
    private String paymentType;
    private String creditCardNo;
    private String promptPayNo;
    private String transactionId; // เพื่อระบุการทำธุรกรรมแต่ละครั้ง เผื่อเรียกใช้ทีหลัง

    public Payment(double amount, String creditCardNo, String paymentType, String promptPayNo, String transactionId) {
        this.amount = amount;
        this.paymentType = paymentType;
        this.creditCardNo = creditCardNo;
        this.promptPayNo = promptPayNo;
        this.transactionId = transactionId;
    }

    
    // เมธอดคำนวณยอดรวม
    // public double calculateTotalAmount(double taxRate) {
    //     return amount + (amount * taxRate); // คำนวณรวมภาษีมูลค่าเพิ่ม
    // }

    public double calculateTotalAmount() {
        return amount; // ไม่ได้คิดภาษี
    }


    // เมธอดสำหรับประมวลผลการชำระเงิน
    public void processPayment() {
        if (paymentType.equalsIgnoreCase("PromptPay")) {
            System.out.println("Processing PromptPay payment of " + calculateTotalAmount() + " THB.");
        } else if (paymentType.equalsIgnoreCase("Credit")) {
            System.out.println("Processing Credit Card payment of " + calculateTotalAmount() + " THB.");
        } else {
            System.out.println("Unknown payment method."); //ประเภทการชำระเงินไม่ถูกต้อง
        }
    }

    // เมธอดสำหรับคำนวณยอดรวมที่ต้องชำระ
    // public void calculateTotalAmount() {

    // }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getCreditCardNo() {
        return creditCardNo;
    }

    public void setCreditCardNo(String creditCardNo) {
        this.creditCardNo = creditCardNo;
    }

    public String getPromptPayNo() {
        return promptPayNo;
    }

    public void setPromptPayNo(String promptPayNo) {
        this.promptPayNo = promptPayNo;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

}
