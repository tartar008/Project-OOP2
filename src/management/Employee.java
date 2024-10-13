package src.management;


import src.customers.Person;

public class Employee extends Person{
    private String empId;
    private String position;

    public Employee(String firstName, String lastName, String phoneNumber, String email, String staffId, String position){
        super(firstName, lastName, phoneNumber, email);
        this.empId = empId;
        this.position = position;
    }
} 
