public class Employee extends Person {
    protected String position;

    public Employee(String name, String id, String contactInfo, String position) {
        super(name, id, contactInfo);
        this.position = position;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return super.toString() + ", Position: " + position;
    }
}
