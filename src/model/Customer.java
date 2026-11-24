
package model;

public class Customer extends User {

    private String membershipType;

    // Default Constructor
    public Customer() {
        super();
        this.membershipType = "Regular";
    }

    // Parameterized Constructor
    public Customer(String userId, String name, String email,
                    String password, String phoneNumber) {

        super(userId, name, email, password, phoneNumber);

        this.membershipType = "Regular";
    }

    // Parameterized Constructor with Membership Type
    public Customer(String userId, String name, String email,
                    String password, String phoneNumber,
                    String membershipType) {

        super(userId, name, email, password, phoneNumber);

        this.membershipType = membershipType;
    }

    // Getter
    public String getMembershipType() {
        return membershipType;
    }

    // Setter
    public void setMembershipType(String membershipType) {
        this.membershipType = membershipType;
    }

    // Display Customer Details
    @Override
    public void displayUserDetails() {

        System.out.println("\n------ CUSTOMER DETAILS ------");

        System.out.println("Customer ID: " + getUserId());
        System.out.println("Name: " + getName());
        System.out.println("Email: " + getEmail());
        System.out.println("Phone Number: " + getPhoneNumber());
        System.out.println("Membership Type: " + membershipType);
    }
}
