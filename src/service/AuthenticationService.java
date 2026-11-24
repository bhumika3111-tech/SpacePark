
package service;

import java.util.ArrayList;
import java.util.Scanner;

import model.User;
import model.Customer;
import model.Admin;
import model.Operator;
import exception.InvalidUserException;

public class AuthenticationService {

    private ArrayList<User> users;

    public AuthenticationService() {

        users = new ArrayList<User>();

        // Default Admin Account
        users.add(new Admin("A001", "Admin",
                "admin@spacepark.com",
                "admin123", "9876543210"));

        // Default Operator Account
        users.add(new Operator("O001", "Operator",
                "operator@spacepark.com",
                "operator123", "9876543211"));
    }

    // User Registration
    public void registerUser(Scanner sc) {

        System.out.println("\n----- USER REGISTRATION -----");

        System.out.print("Enter User ID: ");
        String userId = sc.nextLine().trim();

        System.out.print("Enter Name: ");
        String name = sc.nextLine().trim();

        System.out.print("Enter Email: ");
        String email = sc.nextLine().trim();

        System.out.print("Enter Password: ");
        String password = sc.nextLine();

        System.out.print("Enter Phone Number: ");
        String phone = sc.nextLine().trim();

        // Validate Empty Fields
        if (userId.isEmpty() || name.isEmpty()
                || email.isEmpty() || password.trim().isEmpty()
                || phone.isEmpty()) {

            System.out.println("All fields are required!");
            return;
        }

        // Basic Email Validation
        if (!email.contains("@") || !email.contains(".")) {
            System.out.println("Please enter a valid email address!");
            return;
        }

        // Check Duplicate User ID and Email
        for (User user : users) {

            if (user.getUserId().equalsIgnoreCase(userId)
                    || user.getEmail().equalsIgnoreCase(email)) {

                System.out.println("User ID or Email already exists!");
                return;
            }
        }

        // Create Customer
        Customer customer = new Customer(
                userId, name, email, password, phone);

        users.add(customer);

        System.out.println("Registration Successful!");
    }

    // User Login
    public User loginUser(Scanner sc) throws InvalidUserException {

        System.out.println("\n----- USER LOGIN -----");

        System.out.print("Enter Email: ");
        String email = sc.nextLine().trim();

        System.out.print("Enter Password: ");
        String password = sc.nextLine();

        for (User user : users) {

            if (user.getEmail().equalsIgnoreCase(email)
                    && user.getPassword().equals(password)) {

                System.out.println("\nLogin Successful!");
                System.out.println("Welcome, " + user.getName());

                return user;
            }
        }

        throw new InvalidUserException("Invalid Email or Password!");
    }

    // Get All Users
    public ArrayList<User> getAllUsers() {
        return new ArrayList<User>(users);
    }
}
