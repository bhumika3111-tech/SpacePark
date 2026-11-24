
package exception;

public class DuplicateVehicleException extends Exception {

    public DuplicateVehicleException() {
        super("Vehicle is already registered!");
    }

    public DuplicateVehicleException(String message) {
        super(message);
    }
}
