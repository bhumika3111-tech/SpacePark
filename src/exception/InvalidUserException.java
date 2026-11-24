
package exception;

public class InvalidUserException extends Exception {

    public InvalidUserException() {
        super("Invalid user details!");
    }

    public InvalidUserException(String message) {
        super(message);
    }
}
