
package model;

public class Admin extends User {

    private String adminRole;

    // Default Constructor
    public Admin() {
        super();
        this.adminRole = "Administrator";
    }

    // Parameterized Constructor
    public Admin(String userId, String name, String email,
                 String password, String phoneNumber) {

        super(userId, name, email, password, phoneNumber);

        this.adminRole = "Administrator";
    }

    // Parameterized Constructor with Admin Role
    public Admin(String userId, String name, String email,
                 String password, String phoneNumber,
                 String adminRole) {

        super(userId, name, email, password, phoneNumber);

        this.adminRole = adminRole;
    }

    // Getter
    public String getAdminRole() {
        return adminRole;
    }

    // Setter
    public void setAdminRole(String adminRole) {
        this.adminRole = adminRole;
    }

    // Display Admin Details
    @Override
    public void displayUserDetails() {

        System.out.println("\n------ ADMIN DETAILS ------");

        System.out.println("Admin ID: " + getUserId());
        System.out.println("Name: " + getName());
        System.out.println("Email: " + getEmail());
        System.out.println("Phone Number: " + getPhoneNumber());
        System.out.println("Admin Role: " + adminRole);
    }
}
