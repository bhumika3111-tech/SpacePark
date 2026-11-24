
package exception;

public class MaintenanceException extends Exception {

    public MaintenanceException() {
        super("A maintenance operation could not be completed.");
    }

    public MaintenanceException(String message) {
        super(message);
    }
}
