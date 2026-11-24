
package model;

public class Operator extends User {

    private String shift;

    // Default Constructor
    public Operator() {
        super();
        this.shift = "Morning";
    }

    // Parameterized Constructor
    public Operator(String userId, String name, String email,
                    String password, String phoneNumber) {

        super(userId, name, email, password, phoneNumber);

        this.shift = "Morning";
    }

    // Parameterized Constructor with Shift
    public Operator(String userId, String name, String email,
                    String password, String phoneNumber,
                    String shift) {

        super(userId, name, email, password, phoneNumber);

        this.shift = shift;
    }

    // Getter
    public String getShift() {
        return shift;
    }

    // Setter
    public void setShift(String shift) {
        this.shift = shift;
    }

    // Display Operator Details
    @Override
    public void displayUserDetails() {

        System.out.println("\n------ OPERATOR DETAILS ------");

        System.out.println("Operator ID: " + getUserId());
        System.out.println("Name: " + getName());
        System.out.println("Email: " + getEmail());
        System.out.println("Phone Number: " + getPhoneNumber());
        System.out.println("Working Shift: " + shift);
    }
}
