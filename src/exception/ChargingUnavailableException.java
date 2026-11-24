
package exception;

public class ChargingUnavailableException extends Exception {

    public ChargingUnavailableException() {
        super("Charging station is currently unavailable.");
    }

    public ChargingUnavailableException(String message) {
        super(message);
    }
}
