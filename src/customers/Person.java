package src.customers;

public class Person {
    protected String firstName;
    protected String lastName;
    protected String phoneNumber;
    protected String email;

    public Person (){
    }

    public Person (String firstName, String lastName, String phoneNumber, String email){
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }
    
    public Person (String firstName, String lastName){
        this.firstName = firstName;
        this.lastName = lastName;
        
    }

    public String getFirstName(){
        return firstName;
    }
    public String getLastName(){
        return lastName;
    }
    public void setFirstName(String firstname){
         this.firstName = firstname;
    }
    public void getLastName(String lastName){
        this.lastName = lastName;
    }
    public void setPhoneNumber(String phoneNumber){
        this.phoneNumber = phoneNumber;
    }
    public String getPhoneNumber(){
        return phoneNumber;
    }
    public void setEmail(String email){
        this.email = email;
    }
    public String getEmail(){
        return email;
    }

    public void printInfo(){
        System.out.println("Fullname: " + getFirstName() + " " + getLastName());
    }

}
