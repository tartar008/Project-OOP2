public class Person {
    protected String firstName;
    protected String lastName;
    protected String phoneNumber;
    protected String email;

    public Person (String firstName, String lastName, String phoneNumber, String email){
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }
    public String getFirstName(){
        return firstName;
    }
    public String getLastName(){
        return lastName;
    }
    public String getPhoneNumber(){
        return phoneNumber;
    }
    public String getEmail(){
        return email;
    }

    public void printInfo(){
        System.out.println("Fullname: " + getFirstName() + " " + getLastName());
    }

}
